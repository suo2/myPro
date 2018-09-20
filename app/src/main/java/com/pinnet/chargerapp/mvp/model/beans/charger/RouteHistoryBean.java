package com.pinnet.chargerapp.mvp.model.beans.charger;

/**
 * @author P00558
 * @date 2018/4/28
 */

public class RouteHistoryBean {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 起点名称
     */
    private String startPointName;
    /**
     * 起点经纬度
     */
    private double startPointLat;
    private double startPointLng;
    /**
     * 终点经纬度
     */
    private String endPointName;
    private double endPointLat;
    private double endPointLng;
    /**
     * 插入时间
     */
    private double insertTime;
    /**
     * 是否是家
     */
    private boolean isHome;
    /**
     * 是否是公司
     */
    private boolean isCompany;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartPointName() {
        return startPointName;
    }

    public void setStartPointName(String startPointName) {
        this.startPointName = startPointName;
    }

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getStartPointLng() {
        return startPointLng;
    }

    public void setStartPointLng(double startPointLng) {
        this.startPointLng = startPointLng;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

    public double getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getEndPointLng() {
        return endPointLng;
    }

    public void setEndPointLng(double endPointLng) {
        this.endPointLng = endPointLng;
    }

    public double getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(double insertTime) {
        this.insertTime = insertTime;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }
}
