package com.pinnet.chargerapp.utils;

import android.text.TextUtils;

import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StartChargerBean;
import com.pinnet.chargerapp.mvp.model.http.exception.ApiException;
import com.pinnet.chargerapp.mvp.model.http.exception.EmptyException;
import com.pinnet.chargerapp.mvp.model.http.exception.MostLoginException;
import com.pinnet.chargerapp.mvp.model.http.exception.NoParentException;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<T>, T> handleResult() {   //compose判断结果
        return new FlowableTransformer<BaseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @return
     */
    public static FlowableTransformer<BaseBean, BaseBean> handleResultBase() {   //compose判断结果
        return new FlowableTransformer<BaseBean, BaseBean>() {
            @Override
            public Flowable<BaseBean> apply(Flowable<BaseBean> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean, Flowable<BaseBean>>() {
                    @Override
                    public Flowable<BaseBean> apply(BaseBean baseBean) {
                        if (!baseBean.getError()) {
                            return createDataBase(baseBean);
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }


    /**
     * 统一返回结果处理
     * 针对返回data 为空的情况
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<T>, T> handleResultNoParent() {   //compose判断结果
        return new FlowableTransformer<BaseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            if (baseBean.getData() == null) {
                                return Flowable.error(new NoParentException());
                            }
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<T>, T> handleListResult() {   //compose判断结果
        return new FlowableTransformer<BaseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            if (baseBean.getData() == null || ((List) baseBean.getData()).size() == 0) {
                                return Flowable.error(new EmptyException("数据为空"));
                            }
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 处理错误返回结果
     *
     * @param baseBean
     * @return
     */
    private static Flowable handleErrorResult(BaseBean baseBean) {
        if ("307".equals(baseBean.getFailCode())) {
            return Flowable.error(new MostLoginException("多端登录", 307));
        } else if ("306".equals(baseBean.getFailCode())) {
            return Flowable.error(new MostLoginException("登录过期", 306));
        }
        String errorMessage = baseBean.getMessage();
//        switch (baseBean.getFailCode()) {
//            case "1004":
//                errorMessage = "手机号未注册";
//                break;
//            case "1007":
//                errorMessage = "请输入正确的手机号码";
//                break;
//            case "1005":
//                errorMessage = "密码错误";
//                break;
//            case "1003":
//                errorMessage = "手机号已经注册过了";
//                break;
//            case "1001":
//                errorMessage = "验证码已过期";
//                break;
//            default:
//                break;
//        }
        return Flowable.error(new ApiException(errorMessage));
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {

                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable
     *
     * @return
     */
    public static Flowable<BaseBean> createDataBase(final BaseBean t) {
        return Flowable.create(new FlowableOnSubscribe<BaseBean>() {
            @Override
            public void subscribe(FlowableEmitter<BaseBean> emitter) throws Exception {
                try {

                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
