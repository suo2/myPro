package com.pinnet.chargerapp.mvp.model.beans.charger;

/**
 * @author P00558
 * @date 2018/6/29
 * 电站信息
 */

public class StationInfoBean {
    /**
     * 电站编号,电站唯一标识
     */
    private String stationCode;
    /**
     * id,主键
     */
    private Long id;
    /**
     * 电站名称
     */
    private String stationName;
    /**
     * 装机容量[单位: MW]
     */
    private Double capacity;
    /**
     * 安装角度[单位: 度]
     */
    private Double angulation;
    /**
     * 组件布置方式
     */
    private String assemblyLayout;
    /**
     * 总占地面积[单位: 平方公里]
     */
    private Double area;
    /**
     * 平均海拔[单位: 米]
     */
    private Double meanAltitude;
    /**
     * 投产发电时间[毫秒数]
     */
    private Long devoteDate;
    /**
     * 计划运行时间[单位: 年]
     */
    private Double expectRunningPeriod;
    /**
     * 逆变器类型 1：组串式，2：集中式，3：混合式
     */
    private String inverterType;

    /**
     * 并网类型 1:地面式，2：分布式，3: 户用
     */
    private String combineType;
    /**
     * 电站类型
     */
    private String stationType;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 系统安全运行时间开始时间(毫秒数)
     */
    private Long safeBeginDate;
    /**
     * 电站图片 (图片路径uri)
     */
    private String stationPic;
    /**
     * 电站详细地址
     */
    private String stationAddr;
    /**
     * 扶贫电站标识 0:扶贫电站，1:非扶贫电站
     */
    private Integer aidType;
    /**
     * 行政区域
     */
    private String areaCode;
    /**
     * 域Id
     */
    private Long domainId;
    /**
     * 运营方式 0：运营商、1：第三方
     */
    private String operationType;
    /**
     * 电站状态(0:并网、1:在建、2:规划中)
     */
    private String buildState;
    /**
     * 时区
     */
    private Integer timeZone;
    /**
     * 电站KKS编码
     */
    private String kksCode;
    /**
     * 电站名称拼音
     */
    private String namePhonetic;

    /**
     * 电站简介
     */
    private String stationBriefing;

    /**
     * 电站联系人
     */
    private String stationLinkman;

    /**
     * 联系人电话
     */
    private String linkmanPho;
    /**
     * 数据库编号id
     */
    private String dbShardingId;
    /**
     * 二级域Id
     */
    private Long secDomainId;

    private String shareDevName;

    private String shareStatioName;
    /**
     * 是否使用默认电价 0:使用默认电价，1:不使用默认电价
     */
    private Integer useDefaultPrice;

    /**
     * 数据产生来源(IEMS扶贫托管WEB:1, IEMS扶贫托管APP:2, EMPS生成管理:3)
     */
    private Integer dataFrom;

    /**
     * 最后一次成功同步的时间
     */
    private Long lastSyncTime;

    /**
     * 下层网元地址
     */
    private String serviceLocation;

    /**
     * 营业开始时间
     */
    private Long businesStartHours;
    /**
     * 营业结束时间
     */
    private Long businesEndHours;
    /**
     * 电费费率
     */
    private Double powerRate;
    /**
     * 停车费
     */
    private Double parkMoney;
    /**
     * 服务费
     */
    private Double serveMoney;

    /**
     * 行政区域编码
     */
    private Long regionCode;

    /**
     * 站点所有方
     */
    private String stationOwner;

    /**
     * 标识是否被逻辑删除，删除时为true
     */
    private Boolean isLogicDelete = false;

    //电站所在域名称
    private String domainName;
    private double distance;

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Double getAngulation() {
        return angulation;
    }

    public void setAngulation(Double angulation) {
        this.angulation = angulation;
    }

    public String getAssemblyLayout() {
        return assemblyLayout;
    }

    public void setAssemblyLayout(String assemblyLayout) {
        this.assemblyLayout = assemblyLayout;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getMeanAltitude() {
        return meanAltitude;
    }

    public void setMeanAltitude(Double meanAltitude) {
        this.meanAltitude = meanAltitude;
    }

    public Long getDevoteDate() {
        return devoteDate;
    }

    public void setDevoteDate(Long devoteDate) {
        this.devoteDate = devoteDate;
    }

    public Double getExpectRunningPeriod() {
        return expectRunningPeriod;
    }

    public void setExpectRunningPeriod(Double expectRunningPeriod) {
        this.expectRunningPeriod = expectRunningPeriod;
    }

    public String getInverterType() {
        return inverterType;
    }

    public void setInverterType(String inverterType) {
        this.inverterType = inverterType;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
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

    public Long getSafeBeginDate() {
        return safeBeginDate;
    }

    public void setSafeBeginDate(Long safeBeginDate) {
        this.safeBeginDate = safeBeginDate;
    }

    public String getStationPic() {
        return stationPic;
    }

    public void setStationPic(String stationPic) {
        this.stationPic = stationPic;
    }

    public String getStationAddr() {
        return stationAddr;
    }

    public void setStationAddr(String stationAddr) {
        this.stationAddr = stationAddr;
    }

    public Integer getAidType() {
        return aidType;
    }

    public void setAidType(Integer aidType) {
        this.aidType = aidType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public String getKksCode() {
        return kksCode;
    }

    public void setKksCode(String kksCode) {
        this.kksCode = kksCode;
    }

    public String getNamePhonetic() {
        return namePhonetic;
    }

    public void setNamePhonetic(String namePhonetic) {
        this.namePhonetic = namePhonetic;
    }

    public String getStationBriefing() {
        return stationBriefing;
    }

    public void setStationBriefing(String stationBriefing) {
        this.stationBriefing = stationBriefing;
    }

    public String getStationLinkman() {
        return stationLinkman;
    }

    public void setStationLinkman(String stationLinkman) {
        this.stationLinkman = stationLinkman;
    }

    public String getLinkmanPho() {
        return linkmanPho;
    }

    public void setLinkmanPho(String linkmanPho) {
        this.linkmanPho = linkmanPho;
    }

    public String getDbShardingId() {
        return dbShardingId;
    }

    public void setDbShardingId(String dbShardingId) {
        this.dbShardingId = dbShardingId;
    }

    public Long getSecDomainId() {
        return secDomainId;
    }

    public void setSecDomainId(Long secDomainId) {
        this.secDomainId = secDomainId;
    }

    public String getShareDevName() {
        return shareDevName;
    }

    public void setShareDevName(String shareDevName) {
        this.shareDevName = shareDevName;
    }

    public String getShareStatioName() {
        return shareStatioName;
    }

    public void setShareStatioName(String shareStatioName) {
        this.shareStatioName = shareStatioName;
    }

    public Integer getUseDefaultPrice() {
        return useDefaultPrice;
    }

    public void setUseDefaultPrice(Integer useDefaultPrice) {
        this.useDefaultPrice = useDefaultPrice;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

    public Long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public Long getBusinesStartHours() {
        return businesStartHours;
    }

    public void setBusinesStartHours(Long businesStartHours) {
        this.businesStartHours = businesStartHours;
    }

    public Long getBusinesEndHours() {
        return businesEndHours;
    }

    public void setBusinesEndHours(Long businesEndHours) {
        this.businesEndHours = businesEndHours;
    }

    public Double getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(Double powerRate) {
        this.powerRate = powerRate;
    }

    public Double getParkMoney() {
        return parkMoney;
    }

    public void setParkMoney(Double parkMoney) {
        this.parkMoney = parkMoney;
    }

    public Double getServeMoney() {
        return serveMoney;
    }

    public void setServeMoney(Double serveMoney) {
        this.serveMoney = serveMoney;
    }

    public Long getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Long regionCode) {
        this.regionCode = regionCode;
    }

    public String getStationOwner() {
        return stationOwner;
    }

    public void setStationOwner(String stationOwner) {
        this.stationOwner = stationOwner;
    }

    public Boolean getLogicDelete() {
        return isLogicDelete;
    }

    public void setLogicDelete(Boolean logicDelete) {
        isLogicDelete = logicDelete;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }
}
