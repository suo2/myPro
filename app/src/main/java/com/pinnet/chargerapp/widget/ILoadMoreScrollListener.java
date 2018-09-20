package com.pinnet.chargerapp.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author P00558
 * @date 2018/5/29
 */

public abstract class ILoadMoreScrollListener extends RecyclerView.OnScrollListener {

    // 底部还剩下几个的时候开始触发加载更多的回调接口
    private static final int VISIBLE_THRESHOLD = 2;

    private final LinearLayoutManager layoutManager;
    private boolean isRefreshing;

    public ILoadMoreScrollListener(@NonNull LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public ILoadMoreScrollListener(@NonNull LinearLayoutManager layoutManager, boolean isRefreshing) {
        this.layoutManager = layoutManager;
        this.isRefreshing = isRefreshing;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || isRefreshing) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();
        if (totalItemCount < 20) {
            return;
        }
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        if (lastVisibleItem  >= totalItemCount - VISIBLE_THRESHOLD) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();

}