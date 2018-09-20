package com.pinnet.chargerapp.dagger.module;

import android.text.TextUtils;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.dagger.qualifier.ChargerUrl;
import com.pinnet.chargerapp.dagger.qualifier.LoginUrl;
import com.pinnet.chargerapp.dagger.qualifier.MineUrl;
import com.pinnet.chargerapp.mvp.model.http.api.ChargerApi;
import com.pinnet.chargerapp.mvp.model.http.api.LoginApis;
import com.pinnet.chargerapp.mvp.model.http.api.MineApis;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.widget.HttpLoggingInterceptor;
import com.pinnet.chargerapp.utils.SystemUtil;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author P00558
 * @date 2018/4/12
 */
@Module
public class HttpModul {
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    @LoginUrl
    Retrofit provideLoginRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.HTTP_HTTP);
    }

    @Provides
    @Singleton
    @MineUrl
    Retrofit provideMineRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.HTTP_HTTP);
    }

    @Provides
    @Singleton
    @ChargerUrl
    Retrofit provideChargerRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.HTTP_HTTP);
    }

    @Provides
    @Singleton
    LoginApis provideLoginService(@LoginUrl Retrofit retrofit) {
        return retrofit.create(LoginApis.class);
    }

    @Provides
    @Singleton
    MineApis provideMineService(@MineUrl Retrofit retrofit) {
        return retrofit.create(MineApis.class);
    }

    @Provides
    @Singleton
    ChargerApi provideChargerService(@ChargerUrl Retrofit retrofit) {
        return retrofit.create(ChargerApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    request = request.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .header(Constants.HEADER_CONTENT_TYPE, Constants.AUTH_CONTENT_TYPE)
                            .header(Constants.HEADER_ACCEPT, Constants.AUTH_ACCEPT)
                            .header("appDeviceType", "CANDROID")
                            .header(Constants.HEADER_USERID, SharePrefUtils.getInstance().getLoginedUserId())
                            .header(Constants.HEADER_TOKENID, SharePrefUtils.getInstance().getLoginedTokenId())
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    request = request.newBuilder()
                            .header(Constants.HEADER_CONTENT_TYPE, Constants.AUTH_CONTENT_TYPE)
                            .header(Constants.HEADER_ACCEPT, Constants.AUTH_ACCEPT)
                            .build();
                }
                return chain.proceed(request);
            }
        };
        Interceptor getCookiesInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                List<String> cookie = originalResponse.headers("Set-Cookie");
                if (!cookie.isEmpty()) {
                    for (String s : cookie) {
                        //tokenId格式：tokenId=zEvJJIEKUHWO5Jz6RMPVIBlwT3C4LtFQ;Path=/
                        if (!TextUtils.isEmpty(s) && s.contains(Constants.HEADER_TOKENID)) {
                            if (s.contains("tokenId=")) {
                                SharePrefUtils.getInstance().setLoginedTokenId(s.replace("tokenId=", "").split(";")[0]);
                                break;
                            }
                        }
                    }
                }
                return originalResponse;
            }
        };
        builder.addInterceptor(cacheInterceptor);
        builder.addInterceptor(getCookiesInterceptor);
        //设置超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(false);
        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 通过okhttpClient来设置证书
     *
     * @param clientBuilder OKhttpClient.builder
     * @param certificates  读取证书的InputStream
     */
    public void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                        .generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
