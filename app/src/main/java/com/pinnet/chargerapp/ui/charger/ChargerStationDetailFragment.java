package com.pinnet.chargerapp.ui.charger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.charger.StationDetailContract;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.ChargerStationDetailBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationChargerStatusBean;
import com.pinnet.chargerapp.mvp.model.beans.charger.StationInfoBean;
import com.pinnet.chargerapp.mvp.model.http.requestbody.ChargerRequestBody;
import com.pinnet.chargerapp.mvp.presenter.charger.StationDetailPresenter;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.DataUtils;
import com.pinnet.chargerapp.utils.SharePrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/10
 * 电站详情页
 */

public class ChargerStationDetailFragment extends BaseFragment<StationDetailPresenter> implements StationDetailContract.View, ViewPager.OnPageChangeListener {

    public static ChargerStationDetailFragment newInstance(Bundle args) {
        ChargerStationDetailFragment fragment = new ChargerStationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tv_current_page)
    TextView tvCurrentPage;
    @BindView(R.id.tv_total_page)
    TextView tvTotalPage;
    @BindView(R.id.vp_banner)
    ViewPager vpBanner;
    @BindView(R.id.tv_fast_charge_count)
    TextView tvFastCount;
    @BindView(R.id.tv_fast_charge_free_count)
    TextView tvFastFreeCount;
    @BindView(R.id.tv_fast_charge_service_count)
    TextView tvFastServiceCount;
    @BindView(R.id.tv_slow_charge_count)
    TextView tvSlowCount;
    @BindView(R.id.tv_slow_charge_free_count)
    TextView tvSlowFreeCount;
    @BindView(R.id.tv_slow_charge_service_count)
    TextView tvSlowServiceCount;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_operators)
    TextView tvOperators;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    /**
     * 电站Id
     */
    private String sId;
    private LatLng mLocationLatLng;

    BannerPagerAdapter mPagerAdapter;
    private List<String> imgLists = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.charger_station_detail_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getArgumentsData();
        imgLists.add("default");
        mPagerAdapter = new BannerPagerAdapter();
        vpBanner.setAdapter(mPagerAdapter);
        vpBanner.addOnPageChangeListener(this);
        tvTotalPage.setText(String.valueOf(imgLists.size()));
    }

    private void getArgumentsData() {
        sId = getArguments().getString(Constants.CHAGER_SID);
        mLocationLatLng = getArguments().getParcelable(Constants.CHARGER_USER_LOCATION);
        mPresenter.onRequestStationDetail(sId);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @OnClick({R.id.tv_charger_status})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_charger_status:
                ((ChargerStationDetailActivity) mContext).switchTabLayout(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvCurrentPage.setText(String.valueOf(position + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRequestStationDetailResult(ChargerStationDetailBean stationDetailBean) {
        StationInfoBean stationBean = stationDetailBean.getStation();
        mPresenter.onDownLoadStationImage(sId);
        if (stationBean != null) {
            tvAddress.setText(stationBean.getStationAddr());
            String[] distance = DataUtils.getInstance().handleDistance(stationBean.getDistance());
            tvDistance.setText(distance[0] + distance[1]);
            if ("0" == stationBean.getOperationType()) {
                tvOperators.setText("运营商");
            } else if ("1" == stationBean.getOperationType()) {
                tvOperators.setText("第三方");
            } else {
                tvOperators.setText(R.string.main_invate_string);
            }
        }
        List<StationChargerStatusBean> statusBeanList = stationDetailBean.getList();
        if (statusBeanList != null) {
            for (StationChargerStatusBean statusBean : statusBeanList) {
                if (1 == statusBean.getType()) {
                    tvFastCount.setText(String.valueOf(statusBean.getCount()));
                    tvFastFreeCount.setText(String.valueOf(statusBean.getExpire()));
                    tvFastServiceCount.setText(String.valueOf(statusBean.getFault()));
                } else if (2 == statusBean.getType()) {
                    tvSlowCount.setText(String.valueOf(statusBean.getCount()));
                    tvSlowFreeCount.setText(String.valueOf(statusBean.getExpire()));
                    tvSlowServiceCount.setText(String.valueOf(statusBean.getFault()));
                }
            }
        }

    }

    @Override
    public void onDownLoadStationImageResult(String path) {
        imgLists.clear();
        imgLists.add(path);
        tvTotalPage.setText(String.valueOf(imgLists.size()));
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public ChargerRequestBody getRequestBody() {
        ChargerRequestBody requestBody = new ChargerRequestBody();
        requestBody.sid = sId;
        if (mLocationLatLng != null) {
            requestBody.latitude = String.valueOf(mLocationLatLng.latitude);
            requestBody.longitude = String.valueOf(mLocationLatLng.longitude);
        }
        return requestBody;
    }

    /**
     * 图片轮播
     */
    private class BannerPagerAdapter extends PagerAdapter {
        private SparseArray<View> cacheView;

        public BannerPagerAdapter() {
            super();
            cacheView = new SparseArray<>(imgLists.size());
        }

        @Override
        public int getCount() {
            return imgLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = cacheView.get(position);
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.charger_station_detail_item_vp_img, container, false);
                view.setTag(position);
                ImageView image = (ImageView) view.findViewById(R.id.image);
                Glide.with(ChargerStationDetailFragment.this)
                        .load(imgLists.get(position))
                        .asBitmap()
                        .placeholder(R.drawable.charger_icon_station_default)
                        .error(R.drawable.charger_icon_station_default)
                        .into(image);
                cacheView.put(position, view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.onRequestStationDetail(sId);
    }
}
