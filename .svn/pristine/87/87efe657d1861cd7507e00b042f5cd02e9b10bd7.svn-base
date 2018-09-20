package com.pinnet.chargerapp.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.pinnet.chargerapp.app.ChargerApp;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargingBean;
import com.pinnet.chargerapp.utils.GsonUtils;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;
import com.pinnet.chargerapp.utils.ToastUtils;
import com.pinnet.chargerapp.utils.rxbus.RxBus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PushService extends Service {
    public static final String TAG = "PushService";

    private String host = "tcp://10.10.10.91:61613";
    private String userName = "admin";
    private String passWord = "password";
    private MqttClient client;
    private static String myTopic = "test/topic";
    private MqttConnectOptions options;

    private String android_id;
    private ConnectivityManager mConnectivityManager; // To check for connectivity changes
    private ScheduledExecutorService scheduler;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        host = intent.getStringExtra("address");
        myTopic = intent.getStringExtra("topic");
        userName = intent.getStringExtra("userName");
        passWord = intent.getStringExtra("password");
        init();
        startReconnect();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        try {

            android_id = GetAndroidId.getMyUUID(this);
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, android_id,
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //断连 发送消息
            MqttTopic topic = client.getTopic(myTopic);
            options.setWill(topic, "close".getBytes(), 2, false);
            //设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    LogUtils.getInstance().e("mqtt connectionLost", cause);
                    if (cause.toString().contains("SocketException")) {
                        SharePrefUtils.getInstance().setChargingStatus(2);
                    }
                    //连接丢失后，一般在这里面进行重连
                    if (isNetworkAvailable()) {
                        reconnectIfNecessary();
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    LogUtils.getInstance().e("deliveryComplete---------"
                            + token.isComplete());
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面

                    LogUtils.getInstance().i("pushService messageArrived----------" + message.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = message.toString();
                    handler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            LogUtils.getInstance().i("set client params err");
            e.printStackTrace();
        }
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void connect() {
        if (client == null) {
            LogUtils.getInstance().i("client is null");
            return;
        }
        if (client.isConnected()) {
            LogUtils.getInstance().i("client not connect");
            return;
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void disConnected() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client != null && client.isConnected()) {
                        client.publish(myTopic, "close".getBytes(), 2, false);
                        client.disconnect();
                        client.close();
                    }
                } catch (MqttException e) {
                    LogUtils.getInstance().e("mqtt disconnect failed!", e);
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disConnected();
        unregisterReceiver(mConnectivityReceiver);
    }
    /**
     *  调用init() 方法之后，调用此方法。
     */
    public void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (!client.isConnected() && isNetworkAvailable()) {
                    connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }
    /**
     * Query's the NetworkInfo via ConnectivityManager
     * to return the current connected state
     * 通过ConnectivityManager查询网络连接状态
     *
     * @return boolean true if we are connected false otherwise
     * 如果网络状态正常则返回true反之flase
     */
    private boolean isNetworkAvailable() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

        return (info == null) ? false : info.isConnected();
    }

    /**
     * Checkes the current connectivity
     * and reconnects if it is required.
     * 重新连接如果他是必须的
     */
    public synchronized void reconnectIfNecessary() {
        if (client == null || !client.isConnected()) {
            connect();
        }
    }

    /**
     * Receiver that listens for connectivity chanes
     * via ConnectivityManager
     * 网络状态发生变化接收器
     */
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("BroadcastReceiver", "Connectivity Changed...");
            if (!isNetworkAvailable()) {
                SharePrefUtils.getInstance().setChargingStatus(2);
                scheduler.shutdownNow();
            } else {
                startReconnect();
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                try {
                    String jsonStr = String.valueOf(msg.obj);
                    if (TextUtils.isEmpty(jsonStr)) {
                        return;
                    }
                    ChargingBean chargingBean;
                    if (jsonStr.contains("stop")) {//电桩停止充电异常
                        chargingBean = new ChargingBean();
                        chargingBean.setStopCharger(true);
                        RxBus.getInstance().post(chargingBean);
                        SharePrefUtils.getInstance().setChargingStatus(1);
                    } else if (jsonStr.contains("disconnection")) {//电桩断网异常场景
                        chargingBean = new ChargingBean();
                        chargingBean.setDisconnection(true);
                    } else {
                        chargingBean = GsonUtils.getInstance().mGson.fromJson(jsonStr, ChargingBean.class);
                        RxBus.getInstance().post(chargingBean);
                    }
                    Intent intent = new Intent(Constants.PUSH_RECIVER_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.CHARGER_CHARGING_BEAN, chargingBean);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                } catch (Exception e) {
                    LogUtils.getInstance().e("PushService" + e.getMessage(), e);
                }

            } else if (msg.what == 2) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client.subscribe(myTopic, 1);
                        } catch (Exception e) {
                            LogUtils.getInstance().e("msg what = 2");
                            e.printStackTrace();
                            connect();
                        }
                    }
                }).start();

            } else if (msg.what == 3) {
                if (2==SharePrefUtils.getInstance().getChargingStatus()){
                    Intent intent = new Intent(Constants.PUSH_RECIVER_ACTION);
                    Bundle bundle = new Bundle();
                    ChargingBean chargingBean=new ChargingBean();
                    chargingBean.setSocketException(true);
                    bundle.putSerializable(Constants.CHARGER_CHARGING_BEAN, chargingBean);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
                LogUtils.getInstance().e("mqtt client connect failed!");
            }
        }
    };
}
