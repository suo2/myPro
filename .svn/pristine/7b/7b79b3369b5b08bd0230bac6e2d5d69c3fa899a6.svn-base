package com.pinnet.chargerapp.mvp.model.beans.charger;

import com.amap.api.maps.model.LatLng;
import com.pinnet.chargerapp.ui.charger.amap.ClusterItem;

/**
 * @author P00558
 * @date 2018/4/24
 */

public class ChargerStationBean implements ClusterItem {
    /**
     * {
     * "success": true,
     * "data": [{
     * "createDate": 0,
     * "createUser": "system",
     * "updateDate": 0,
     * "updateUser": null,
     * "stationCode": "277AFEFE9EFE493784E0C195A48015E5",
     * "id": 1,
     * "stationName": "zzfei",
     * "capacity": 100.0,
     * "angulation": null,
     * "assemblyLayout": null,
     * "area": null,
     * "meanAltitude": null,
     * "devoteDate": 1527120000000,
     * "expectRunningPeriod": null,
     * "inverterType": null,
     * "combineType": "8",
     * "stationType": null,
     * "longitude": 104.25,
     * "latitude": 30.121944,
     * "safeBeginDate": null,
     * "stationPic": null,
     * "stationAddr": "",
     * "aidType": 0,
     * "areaCode": null,
     * "domainId": 2,
     * "operationType": null,
     * "buildState": "3",
     * "timeZone": 8,
     * "kksCode": null,
     * "namePhonetic": "zzfei",
     * "stationBriefing": "",
     * "stationLinkman": "1",
     * "linkmanPho": "12564685236",
     * "dbShardingId": "01",
     * "secDomainId": 2,
     * "shareDevName": null,
     * "shareStatioName": null,
     * "useDefaultPrice": 1,
     * "dataFrom": 1,
     * "lastSyncTime": null,
     * "serviceLocation": null,
     * "businesStartHours": null,
     * "businesEndHours": null,
     * "powerRate": null,
     * "parkMoney": null,
     * "serveMoney": null,
     * "regionCode": null,
     * "stationOwner": null,
     * "isLogicDelete": false,
     * "domainName": null,
     * "distance":355322.48635266576,
     * "shareName": ""
     * distance
     * }],
     * "failCode": 0,
     * "params": null,
     * "message": "getChargeStationList success!",
     * "buildCode": "2"
     * }
     */

    private StationInfoBean stationInfo;
    private LatLng latLng;
    /**
     * 快充
     */
    private boolean fast;
    /**
     * 慢充
     */
    private boolean slow;
    /**
     * 电桩个数
     */
    private int totalCount;
    /**
     * 电桩空闲个数
     */
    private int freeCount;

    public StationInfoBean getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(StationInfoBean stationInfo) {
        this.stationInfo = stationInfo;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isFast() {
        return fast;
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }

    public boolean isSlow() {
        return slow;
    }

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    @Override
    public LatLng getPosition() {
        if (latLng == null) {
            latLng = new LatLng(stationInfo.getLatitude(), stationInfo.getLongitude());
        }
        return latLng;
    }

    @Override
    public String getStationName() {
        return stationInfo.getStationName();
    }

}
