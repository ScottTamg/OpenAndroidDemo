package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.DeliveryModelEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/27.
 */
public class DeliverDetailAdapter extends RecyclerView.Adapter<DeliverDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<DeliveryModelEntity.DataBean.RecordListBean> mList;

    public DeliverDetailAdapter(Context context, List<DeliveryModelEntity.DataBean.RecordListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_deliver_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvMessage.setText(mList.get(position).getEssage());
        holder.mTvTime.setText(mList.get(position).getCTime());
        if (position == 0) {
            holder.mIvIcon.setImageResource(R.drawable.record_top_icon);
        } else {
            holder.mIvIcon.setImageResource(R.drawable.record_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView mIvIcon;
        @Bind(R.id.tv_message)
        TextView mTvMessage;
        @Bind(R.id.tv_time)
        TextView mTvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
