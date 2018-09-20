package com.pinnet.chargerapp.mvp.model.beans.charger;

import java.util.List;

/**
 * @author P00558
 * @date 2018/7/3
 * 充电桩参数
 */

public class ChargerPileInfoBean {
    private PileParameter pileParameter;
    private List<ChargerRateBean> feeRate;

    public PileParameter getPileParameter() {
        return pileParameter;
    }

    public void setPileParameter(PileParameter pileParameter) {
        this.pileParameter = pileParameter;
    }

    public List<ChargerRateBean> getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(List<ChargerRateBean> feeRate) {
        this.feeRate = feeRate;
    }

    public static class PileParameter {
        /**
         * 出厂序列号
         */
        private String serialNumber;

        /**
         * 主键，id
         */
        private Long id;

        /**
         * 设备类型 1.直流快充 2.直流慢充 3.交流快充 4.交流慢充 5.交直流混合
         */
        private Integer deviceType;

        /**
         * 运营类型 1.私有,不对外开放充电系统2.私有,对外开放充电系统3.公有免费充电统4.公有收费充电系统
         */
        private String businessType;

        /**
         * 上线状态 1.上线 , 0.下线
         */
        private Integer onlineStatus;

        /**
         * 额定功率
         */
        private Double powerRating;

        /**
         * 额定电压
         */
        private Double ratedVoltage;

        /**
         * 枪口个数
         */
        private Integer gunsNum;

        /**
         * 经度
         */
        private Double longitude;

        /**
         * 纬度
         */
        private Double latitude;

        /**
         * 硬件版本号
         */
        private String hardwareVersion;

        /**
         * 软件版本号（桩上的嵌入式软件）
         */
        private String softwareVersion;

        /**
         * ip地址
         */
        private String ipAddress;

        /**
         * 所属站点
         */
        private String stationId;


        /**
         * 用于前台展示电站名称 和数据库无对应关系
         */
        private String stationName;

        /**
         * 经纬度 12位字符码标识
         */
        private String geoHash;

        private Double distance;

        /**
         * 采集服务器Ip
         */
        private String collectServerIp;

        /**
         * 管理状态 1.正常运营状态 2.故障 3.告警 4.离网
         */
        private Integer managerStatus;

        /**
         * 是否是空闲状态 >0 空闲   <=0 非空闲
         */
        private Integer isExpire;

        /**
         * 是否配置证书 0没配   1已配置
         */
        private Integer isConfCert;

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public Integer getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(Integer onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public Double getPowerRating() {
            return powerRating;
        }

        public void setPowerRating(Double powerRating) {
            this.powerRating = powerRating;
        }

        public Double getRatedVoltage() {
            return ratedVoltage;
        }

        public void setRatedVoltage(Double ratedVoltage) {
            this.ratedVoltage = ratedVoltage;
        }

        public Integer getGunsNum() {
            return gunsNum;
        }

        public void setGunsNum(Integer gunsNum) {
            this.gunsNum = gunsNum;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getHardwareVersion() {
            return hardwareVersion;
        }

        public void setHardwareVersion(String hardwareVersion) {
            this.hardwareVersion = hardwareVersion;
        }

        public String getSoftwareVersion() {
            return softwareVersion;
        }

        public void setSoftwareVersion(String softwareVersion) {
            this.softwareVersion = softwareVersion;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }


        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getGeoHash() {
            return geoHash;
        }

        public void setGeoHash(String geoHash) {
            this.geoHash = geoHash;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getCollectServerIp() {
            return collectServerIp;
        }

        public void setCollectServerIp(String collectServerIp) {
            this.collectServerIp = collectServerIp;
        }

        public Integer getManagerStatus() {
            return managerStatus;
        }

        public void setManagerStatus(Integer managerStatus) {
            this.managerStatus = managerStatus;
        }

        public Integer getIsExpire() {
            return isExpire;
        }

        public void setIsExpire(Integer isExpire) {
            this.isExpire = isExpire;
        }

        public Integer getIsConfCert() {
            return isConfCert;
        }

        public void setIsConfCert(Integer isConfCert) {
            this.isConfCert = isConfCert;
        }
    }
}
