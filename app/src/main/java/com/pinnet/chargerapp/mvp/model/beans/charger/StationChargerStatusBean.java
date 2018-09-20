package com.pinnet.chargerapp.mvp.model.beans.charger;

/**
 * @author P00558
 * @date 2018/4/23
 * 电桩状态
 */

public class StationChargerStatusBean {
    /**
     * "type": 1,
     * "expire": 0,
     * "fault": 0,
     * "count": 0
     */
    /**
     * 类型 1：快充，2：慢充
     */
    private int type;
    /**
     * 空闲
     */
    private int expire;
    /**
     * 维修
     */
    private int fault;
    /**
     * 总数
     */
    private int count;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
