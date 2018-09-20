package com.pinnet.chargerapp.ui.charger.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinnet.chargerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/5/10
 * 评论列表 Adapter
 */

public class ChargerCommentAdapter extends RecyclerView.Adapter<ChargerCommentAdapter.ViewHolder> {
    private Context mContext;

    public ChargerCommentAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_comment_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.rlvReply.setLayoutManager(layoutManager);
        ChargerCommentReplyAdapter replyAdapter = new ChargerCommentReplyAdapter();
        holder.rlvReply.setAdapter(replyAdapter);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.iv_user_header)
        ImageView ivUserHeader;
        @BindView(R.id.rlv_comment_reply)
        RecyclerView rlvReply;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
