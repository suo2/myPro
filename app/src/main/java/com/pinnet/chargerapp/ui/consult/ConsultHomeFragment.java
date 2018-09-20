package com.pinnet.chargerapp.ui.consult;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseFragment;
import com.pinnet.chargerapp.mvp.contract.consult.ConsultHomeContract;
import com.pinnet.chargerapp.mvp.presenter.consult.ConsultHomePresenter;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.widget.GrayItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/4
 */

public class ConsultHomeFragment extends BaseFragment<ConsultHomePresenter> implements ConsultHomeContract.View {
    public static ConsultHomeFragment newInstance() {
        ConsultHomeFragment fragment = new ConsultHomeFragment();
        return fragment;
    }

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.view_main)
    RecyclerView rlvConsult;
    @BindView(R.id.fab_ask_question)
    FloatingActionButton fabAskQuestion;
    ConsultHomeAdapter consultAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.consult_home_fragment;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.main_tab_consult);
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.consult_wait_answer_question);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rlvConsult.setLayoutManager(layoutManager);
        rlvConsult.addItemDecoration(new GrayItemDecoration(mContext, LinearLayoutManager.VERTICAL, ContextCompat.getDrawable(mContext,
                R.drawable.mine_charge_recycle_divider)));
        consultAdapter = new ConsultHomeAdapter();
        rlvConsult.setAdapter(consultAdapter);
        consultAdapter.setOnItemClickListener(new ConsultHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                startAct(new Intent(mContext, ConsultDetailActivity.class));
            }

            @Override
            public void onAnswer() {
                startAct(new Intent(mContext, ConsultAnswerQuestionActivity.class));
            }
        });
    }

    @OnClick({R.id.fab_ask_question})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_ask_question:
                startAct(new Intent(mContext, ConsultAskQuestionActivity.class));
                break;
            default:
                break;
        }
    }
}
