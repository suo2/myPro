package com.pinnet.chargerapp.mvp.model.beans.charger;

import java.io.Serializable;

/**
 * @author P00558
 * @date 2018/5/9
 * 充电中
 */

public class ChargingBean implements Serializable {
    /**
     * "serialNumber": "zzfeizzfei",
     "gunNumber": "1",
     "chargeDirectVoltage": 500.0,
     "chargeDirectCurrent": 6.0,
     "chargedPower": null,
     "chargeMoney": 0.0,
     "progress": 0.0,
     "chargedTime": null,
     "estimateTimeLeft": null
     */
    /**
     * 桩编号
     */
    private String serialNumber;

    /**
     * 枪口编号
     */
    private String gunNumber;

    /**
     * 充电直流电压
     */
    private double chargeDirectVoltage;

    /**
     * 充电直流电流
     */
    private double chargeDirectCurrent;

    /**
     * 已充电量
     */
    private double chargedPower;

    /**
     * 充电金额
     */
    private double chargeMoney;

    /**
     * 进度
     */
    private double progress;

    /**
     * 已充时长
     */
    private long chargedTime;
    private String formatChargedTime;

    /**
     * 预估剩余充电时间
     */
    private long estimateTimeLeft;
    /**
     * 是否结束充电
     */
    private boolean stopCharger;
    /**
     * 是否是充电桩与平台断链
     */
    private boolean isDisconnection;
    /**
     * 是否是网络异常
     */
    private boolean isSocketException;

    public boolean isStopCharger() {
        return stopCharger;
    }

    public void setStopCharger(boolean stopCharger) {
        this.stopCharger = stopCharger;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getGunNumber() {
        return gunNumber;
    }

    public void setGunNumber(String gunNumber) {
        this.gunNumber = gunNumber;
    }

    public double getChargeDirectVoltage() {
        return chargeDirectVoltage;
    }

    public void setChargeDirectVoltage(double chargeDirectVoltage) {
        this.chargeDirectVoltage = chargeDirectVoltage;
    }

    public double getChargeDirectCurrent() {
        return chargeDirectCurrent;
    }

    public void setChargeDirectCurrent(double chargeDirectCurrent) {
        this.chargeDirectCurrent = chargeDirectCurrent;
    }

    public double getChargedPower() {
        return chargedPower;
    }

    public void setChargedPower(double chargedPower) {
        this.chargedPower = chargedPower;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public long getChargedTime() {
        return chargedTime;
    }

    public void setChargedTime(long chargedTime) {
        this.chargedTime = chargedTime;
    }

    public long getEstimateTimeLeft() {
        return estimateTimeLeft;
    }

    public void setEstimateTimeLeft(long estimateTimeLeft) {
        this.estimateTimeLeft = estimateTimeLeft;
    }

    public String getFormatChargedTime() {
        return formatChargedTime;
    }

    public void setFormatChargedTime(String formatChargedTime) {
        this.formatChargedTime = formatChargedTime;
    }

    public boolean isDisconnection() {
        return isDisconnection;
    }

    public void setDisconnection(boolean disconnection) {
        isDisconnection = disconnection;
    }

    public boolean isSocketException() {
        return isSocketException;
    }

    public void setSocketException(boolean socketException) {
        isSocketException = socketException;
    }
}
