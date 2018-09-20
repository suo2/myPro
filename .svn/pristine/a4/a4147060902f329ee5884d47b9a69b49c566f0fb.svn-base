package com.pinnet.chargerapp.ui.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.base.BaseCommonFragment;
import com.pinnet.chargerapp.mvp.contract.mine.MyMessageContract;
import com.pinnet.chargerapp.mvp.presenter.mine.MyMessagePresenter;
import com.pinnet.chargerapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author P00558
 * @date 2018/4/9
 * 我的消息
 */

public class MyMessageActivity extends BaseActivity<MyMessagePresenter> implements MyMessageContract.View {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tab_switch)
    TabLayout tabSwitch;
    @BindView(R.id.view_main)
    ViewPager viewPager;
    private List<String> mTabName = new ArrayList<>();
    private BaseCommonFragment mCommentMessageFragment;
    private BaseCommonFragment mReplyMessageFragment;
    private BaseCommonFragment mSystemMessageFragmet;
    private List<BaseCommonFragment> fragmentLists;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_my_message_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        tvHeaderTitle.setText(R.string.mine_my_message);
        initFragment();
        viewPager.setAdapter(new MessageAdapter(getSupportFragmentManager()));
        initTablayout();
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

    private void initTablayout() {
        LinearLayout linearLayout = (LinearLayout) tabSwitch.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.mine_message_tab_divider));
        tabSwitch.addTab(tabSwitch.newTab().setText(mTabName.get(0)));
        tabSwitch.addTab(tabSwitch.newTab().setText(mTabName.get(1)));
        tabSwitch.addTab(tabSwitch.newTab().setText(mTabName.get(2)));
        tabSwitch.setupWithViewPager(viewPager);
    }

    private void initFragment() {
        mCommentMessageFragment = new CommentMessageFragment();
        mReplyMessageFragment = new ReplyMessageFragment();
        mSystemMessageFragmet = new SystemMessageFragment();
        if (fragmentLists == null) {
            fragmentLists = new ArrayList<>();
        }
        fragmentLists.add(mCommentMessageFragment);
        fragmentLists.add(mReplyMessageFragment);
        fragmentLists.add(mSystemMessageFragmet);
        mTabName.add("我评论的");
        mTabName.add("回复我的");
        mTabName.add("系统消息");
    }

    class MessageAdapter extends FragmentPagerAdapter {

        public MessageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentLists.get(position);
        }

        @Override
        public int getCount() {
            return fragmentLists.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName.get(position);
        }
    }
}
