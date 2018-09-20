package com.pinnet.chargerapp.ui.charger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.charger.ChargerCommentContract;
import com.pinnet.chargerapp.mvp.presenter.charger.ChargerCommentPresenter;
import com.pinnet.chargerapp.ui.charger.adapter.ChargerCommentAdapter;
import com.pinnet.chargerapp.widget.GrayItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/5/10
 */

public class ChargerCommentFragment extends BaseFragment<ChargerCommentPresenter> implements ChargerCommentContract.View {
    @BindView(R.id.view_main)
    RecyclerView rlvComment;
    ChargerCommentAdapter chargerCommentAdapter;

    public static ChargerCommentFragment newInstance(Bundle args) {
        ChargerCommentFragment fragment = new ChargerCommentFragment();
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.charger_comment_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chargerCommentAdapter = new ChargerCommentAdapter(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvComment.setLayoutManager(layoutManager);
        rlvComment.setAdapter(chargerCommentAdapter);
        rlvComment.addItemDecoration(new GrayItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                getResources().getDrawable(R.drawable.mine_charge_recycle_divider)));
    }

    @OnClick({R.id.tv_submit_comment})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit_comment:
                startAct(new Intent(mContext, ChargerCommentSubmitActivity.class));
                break;
            default:
                break;
        }
    }
}
