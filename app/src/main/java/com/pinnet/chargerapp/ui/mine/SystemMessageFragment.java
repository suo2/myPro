package com.pinnet.chargerapp.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.widget.GrayItemDecoration;
import com.pinnet.chargerapp.R;

import butterknife.BindView;

/**
 * @author P00558
 * @date 2018/4/11
 * 系统消息
 */

public class SystemMessageFragment extends BaseCommonFragment {
    @BindView(R.id.view_main)
    RecyclerView mRecycler;
    SystemMessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_my_message_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new SystemMessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new GrayItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                mContext.getResources().getDrawable(R.drawable.mine_charge_recycle_divider)));
        mRecycler.setAdapter(mAdapter);
    }
}
