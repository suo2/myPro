package com.pinnet.chargerapp.ui.charger.amap;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiyi.qi on 16/10/10.
 * 聚合点
 */

public class Cluster {


    private LatLng mLatLng;
    private List<ChargerStationBean> mClusterItems;
    private Marker mMarker;


    Cluster(LatLng latLng) {

        mLatLng = latLng;
        mClusterItems = new ArrayList<ChargerStationBean>();
    }

    void addClusterItem(ChargerStationBean clusterItem) {
        mClusterItems.add(clusterItem);
    }

    int getClusterCount() {
        return mClusterItems.size();
    }


    LatLng getCenterLatLng() {
        return mLatLng;
    }

    void setMarker(Marker marker) {
        mMarker = marker;
    }

    Marker getMarker() {
        return mMarker;
    }

   public List<ChargerStationBean> getClusterItems() {
        return mClusterItems;
    }

    /**
     * 获取单个电站
     *
     * @return
     */
    ChargerStationBean getSingleClusterItem() {
        if (mClusterItems != null && mClusterItems.size() >= 1) {
            return mClusterItems.get(0);
        }
        return null;
    }
}
