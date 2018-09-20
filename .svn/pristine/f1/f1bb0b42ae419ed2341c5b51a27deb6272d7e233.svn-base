package com.pinnet.chargerapp.mvp.model.beans.charger;

/**
 * @author P00558
 * @date 2018/4/24
 * 电桩-枪口
 */

public class ChargerMuzzleBean {
    /**
     * "createDate": 0,
     * "createUser": "system",
     * "updateDate": 0,
     * "updateUser": null,
     * "id": 2,
     * "serialNumber": "22",
     * "carParkPosition": "2",
     * "interfaceType": 2,
     * "gunNumber": "2",
     * "managerStatus": 2,
     * "chargeStatus": 2,
     * "upTime": null,
     * "isBooked": 2,
     * "lastConnectTime": 2
     */
    /**
     * 主键，Id
     */
    private long id;
    /**
     * 桩编号
     */
    private String serialNumber;
    /**
     * 对应车位号
     */
    private String carParkPosition;
    /**
     * 枪口类型 1 国标 2 欧标3 美标4 日标
     */
    private int interfaceType;
    /**
     * 枪口号
     */
    private String gunNumber;
    /**
     * 枪口管理状态1. 正常运营状态 2. 故障 3. 离网（用于充电桩标记自己当前状态，无法上传到运营平台） 4. 离网数据上传中 5. 维护',
     */
    private int managerStatus;
    /**
     * 枪口充电状态 1．空闲 2．充电枪已连接，未启动充电
     * 3．启动中（已发启动命令，等待充电枪连接 。或者启动充电的过程都定义为启动中）
     * 4．充电中 5．充电完成 6．已预约 7．等待充电中（预约已连接车，但未启动充电状态）',
     */
    private int chargeStatus;
    /**
     * 用于缓存
     */
    private long upTime;

    /**
     * 是否预约 1已经预约 0未预约
     */
    private int isBooked;

    /**
     * 枪口最后连接时间
     */
    private long lastConnectTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCarParkPosition() {
        return carParkPosition;
    }

    public void setCarParkPosition(String carParkPosition) {
        this.carParkPosition = carParkPosition;
    }

    public int getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(int interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getGunNumber() {
        return gunNumber;
    }

    public void setGunNumber(String gunNumber) {
        this.gunNumber = gunNumber;
    }

    public int getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(int managerStatus) {
        this.managerStatus = managerStatus;
    }

    public int getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(int chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public int getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(int isBooked) {
        this.isBooked = isBooked;
    }

    public long getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }
}
