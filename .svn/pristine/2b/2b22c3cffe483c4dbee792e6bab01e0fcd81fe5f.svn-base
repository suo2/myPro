package com.pinnet.chargerapp.mvp.model.beans.charger;

import java.io.Serializable;

/**
 * @author P00558
 * @date 2018/5/10
 */

public class StartChargerBean implements Serializable {
    /**
     * "topic": "zzfeizzfei:1:1",
     * "username": "admin",
     * "realTimeData": {
     * "serialNumber": "zzfeizzfei",
     * "gunNumber": "1",
     * "chargeDirectVoltage": 500.0,
     * "chargeDirectCurrent": 6.0,
     * "chargedPower": null,
     * "chargeMoney": null,
     * "progress": 0.0,
     * "chargedTime": null,
     * "estimateTimeLeft": null
     * },
     * "password": "password"
     */
    private String topic;
    private String username;
    private String password;
    private String address;
    /**
     * 启动失败原因：
     * 0：启动未失败
     * 1：充电机检测错误
     * 2：需求错误
     * 3：车子未准备好
     * 4：可用余额不足
     */
    private int cause;
    /**
     * 充电模式
     */
    private int type;
    private ChargingBean realTimeData;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChargingBean getRealTimeData() {
        return realTimeData;
    }

    public void setRealTimeData(ChargingBean realTimeData) {
        this.realTimeData = realTimeData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCause() {
        return cause;
    }

    public void setCause(int cause) {
        this.cause = cause;
    }
}
