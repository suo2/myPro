package com.pinnet.chargerapp.ui.charger.amap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.ui.charger.RoutePlanAMapActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class DriveRouteDetailActivity extends BaseCommonActivity {
    private DrivePath mDrivePath;
    private DriveRouteResult mDriveRouteResult;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.firstline)
    TextView mTitleDriveRoute;
    @BindView(R.id.secondline)
    TextView mDesDriveRoute;
    @BindView(R.id.bus_segment_list)
    ListView mDriveSegmentList;
    private DriveSegmentListAdapter mDriveSegmentListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_route_detail;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        getIntentData();
        init();
    }

    @OnClick({R.id.iv_back})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            default:
                break;
        }
    }

    private void init() {
        tvHeaderTitle.setText("驾车路线详情");
        String dur = AMapUtil.getFriendlyTime((int) mDrivePath.getDuration());
        String dis = AMapUtil.getFriendlyLength((int) mDrivePath
                .getDistance());
        mTitleDriveRoute.setText(dur + "(" + dis + ")");
        int taxiCost = (int) mDriveRouteResult.getTaxiCost();
        mDesDriveRoute.setText("打车约" + taxiCost + "元");
        mDesDriveRoute.setVisibility(View.VISIBLE);
        configureListView();
    }

    private void configureListView() {
        mDriveSegmentList = (ListView) findViewById(R.id.bus_segment_list);
        mDriveSegmentListAdapter = new DriveSegmentListAdapter(
                this.getApplicationContext(), mDrivePath.getSteps());
        mDriveSegmentList.setAdapter(mDriveSegmentListAdapter);
    }

    private void getIntentData() {
//        Intent intent = getIntent();
//        if (intent == null) {
//            return;
//        }
        mDrivePath = RoutePlanAMapActivity.drivePath;
        mDriveRouteResult =RoutePlanAMapActivity.mDriveRouteResult;
    }
}
