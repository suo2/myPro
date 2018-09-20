package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.view.View;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseCommonActivity;
import com.pinnet.chargerapp.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/6/13
 * 搜素电站页面
 */

public class ChargerStationSearchActivity extends BaseCommonActivity {
    @BindView(R.id.cet_search)
    ClearEditText cetSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.charger_station_search_activity;
    }

    @OnClick({R.id.iv_back, R.id.tv_right_search})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.tv_right_search:
                Intent intent = new Intent();
                intent.putExtra(Constants.CHARGER_STATION_SEARCH_RESULT, cetSearch.getText().toString());
                setResult(Constants.CHARGER_STATION_SEARCH_RESULT_CODE, intent);
                finishAct();
                break;
            default:
                break;
        }
    }
}
