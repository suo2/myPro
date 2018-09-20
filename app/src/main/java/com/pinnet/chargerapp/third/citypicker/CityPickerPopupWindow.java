package com.pinnet.chargerapp.third.citypicker;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pinnet.chargerapp.third.citypicker.adapter.CityListAdapter;
import com.pinnet.chargerapp.third.citypicker.adapter.InnerListener;
import com.pinnet.chargerapp.third.citypicker.adapter.OnPickListener;
import com.pinnet.chargerapp.third.citypicker.adapter.decoration.DividerItemDecoration;
import com.pinnet.chargerapp.third.citypicker.adapter.decoration.SectionItemDecoration;
import com.pinnet.chargerapp.third.citypicker.db.DBManager;
import com.pinnet.chargerapp.third.citypicker.model.City;
import com.pinnet.chargerapp.third.citypicker.model.HotCity;
import com.pinnet.chargerapp.third.citypicker.model.LocateState;
import com.pinnet.chargerapp.third.citypicker.model.LocatedCity;
import com.pinnet.chargerapp.third.citypicker.view.SideIndexBar;
import com.pinnet.chargerapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author P00558
 * @date 2018/4/9
 */

public class CityPickerPopupWindow extends PopupWindow implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;

    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;

    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;

    public CityPickerPopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.cp_dialog_city_picker, null);
        initHotCities();
        initLocatedCity();

        dbManager = new DBManager(context);
        mAllCities = dbManager.getAllCities();
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市", "未知", "0"));
        mResults = mAllCities;


        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.cp_city_recyclerview);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(context, mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context), 1);
        mAdapter = new CityListAdapter(context, mAllCities, mHotCities, locateState);
        mAdapter.setInnerListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView = mContentView.findViewById(R.id.cp_empty_view);
        mOverlayTextView = (TextView) mContentView.findViewById(R.id.cp_overlay);

        mIndexBar = (SideIndexBar) mContentView.findViewById(R.id.cp_side_index_bar);
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        this.setContentView(mContentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }

    private void initLocatedCity() {
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity("定位失败", "未知", "0");
            locateState = LocateState.FAILURE;
        } else {
            locateState = LocateState.SUCCESS;
        }
    }

    private void initHotCities() {
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "北京", "101010100"));
            mHotCities.add(new HotCity("上海", "上海", "101020100"));
            mHotCities.add(new HotCity("成都", "四川", "101270101"));
            mHotCities.add(new HotCity("广州", "广东", "101280101"));
            mHotCities.add(new HotCity("深圳", "广东", "101280601"));
            mHotCities.add(new HotCity("杭州", "浙江", "101210101"));
            mHotCities.add(new HotCity("武汉", "湖北", "101200101"));
            mHotCities.add(new HotCity("南京", "江苏", "101190101"));


        }
    }


    /**
     * 搜索框监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            mAdapter.updateData(mResults);
        } else {
            //开始数据库查找
            mResults = dbManager.searchCity(keyword);
            if (mResults == null || mResults.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        if (mResults == null || mResults.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(index)) {
            return;
        }
        int size = mResults.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(index.substring(0, 1), mResults.get(i).getSection().substring(0, 1))) {
                if (mLayoutManager != null) {
                    mLayoutManager.scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        }
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        dismiss();
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }
}
