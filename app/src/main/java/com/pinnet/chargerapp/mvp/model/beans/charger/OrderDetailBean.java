package com.pinnet.chargerapp.mvp.model.beans.charger;

import java.io.Serializable;

/**
 * @author P00558
 * @date 2018/4/26
 * 订单详情
 */

public class OrderDetailBean implements Serializable {
    /**
     {
     "success": true,
     "data": {
     "name": "杭研联调",
     "stationOwner": "品联",
     "price": 0.1,
     "chargeTime": 1528946855000,
     "useTime": "0时0分18秒",
     "electric": 0.0,
     "serviceFee": 1.0,
     "totalAmount": 0.01
     },
     "failCode": 0,
     "params": null,
     "message": "getOrderInfoByUserId success!",
     "buildCode": "2"
     }
     */
    /**
     * 电站名称
     */
    String name;
    /**
     * 电站所属
     */
    String stationOwner;
    /**
     * 电价
     */
    double price;
    /**
     * 充电开始时间
     */
    long chargeTime;
    /**
     * 充电用时
     */
    String useTime;
    /**
     * 充电电量
     */
    double electric;
    /**
     * 充电总价
     */
    double totalAmount;
    /**
     * 充电服务费
     */
    private String serviceFee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStationOwner() {
        return stationOwner;
    }

    public void setStationOwner(String stationOwner) {
        this.stationOwner = stationOwner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public double getElectric() {
        return electric;
    }

    public void setElectric(double electric) {
        this.electric = electric;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
}
