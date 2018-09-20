package com.pinnet.chargerapp.ui.consult;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.minterface.IRecycleClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author P00558
 * @date 2018/5/11
 */

public class ConsultHomeAdapter extends RecyclerView.Adapter<ConsultHomeAdapter.ViewHolder> implements View.OnClickListener {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.consult_home_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.tvAnswer.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_answer:
                if (mClickListener != null) {
                    mClickListener.onAnswer();
                }
                break;
            case R.id.item_view:
                if (mClickListener != null) {
                    mClickListener.onItemClick();
                }
                break;
            default:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_answer)
        TextView tvAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mClickListener;

    public void setOnItemClickListener(OnItemClickListener recycleClickListener) {
        this.mClickListener = recycleClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick();

        void onAnswer();
    }
}
