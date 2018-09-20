package com.pinnet.chargerapp.mvp.model.beans.charger;

import java.util.List;

/**
 * @author P00558
 * @date 2018/4/24
 */

public class ChargerStatusBean {
    /**
     * {
     * "success": true,
     * "data": [{
     * "serialNumber": "CDZ20180050",
     * "count": 1,
     * "expire": 0,
     * "fault": 0,
     * "price": 0.5,
     * "service": 0.6,
     * "type": 2,
     * "list": [{
     * "createDate": 0,
     * "createUser": "system",
     * "updateDate": 0,
     * "updateUser": null,
     * "id": 2,
     * "serialNumber": "CDZ20180050",
     * "carParkPosition": "1",
     * "interfaceType": 1,
     * "gunNumber": "1",
     * "managerStatus": 3,
     * "chargeStatus": null,
     * "upTime": null,
     * "isBooked": null,
     * "lastConnectTime": 1528159780378
     * }]
     * }],
     * "failCode": 0,
     * "params": null,
     * "message": "getChargeListByStationId success!",
     * "buildCode": "2"
     * }
     */
    private String serialNumber;
    /**
     * 枪口数量
     */
    private int count;
    /**
     * 空闲
     */
    private int expire;
    /**
     * 维修
     */
    private int fault;
    private List<ChargerMuzzleBean> list;
    private boolean isChecked;
    /**
     * 费用
     */
    private double price;
    /**
     * 服务费
     */
    private double service;
    /**
     * 桩类型：1、快充；2、慢充
     */
    private int type;
    /**
     * 枪口管理状态1. 正常运营状态 2. 故障 3. 离网（用于充电桩标记自己当前状态，无法上传到运营平台） 4. 离网数据上传中 5. 维护',
     */
    private int managerStatus;
    /**
     * 各时段费率
     */
    private List<ChargerRateBean> rateList;

    public int getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(int managerStatus) {
        this.managerStatus = managerStatus;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getFault() {
        return fault;
    }

    public void setFault(int fault) {
        this.fault = fault;
    }

    public List<ChargerMuzzleBean> getList() {
        return list;
    }

    public void setList(List<ChargerMuzzleBean> list) {
        this.list = list;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ChargerRateBean> getRateList() {
        return rateList;
    }

    public void setRateList(List<ChargerRateBean> rateList) {
        this.rateList = rateList;
    }
}
