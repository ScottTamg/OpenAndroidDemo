package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.FriendOrderEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/31.
 */
public class SendFriendOrderAdapter extends RecyclerView.Adapter<SendFriendOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<FriendOrderEntity.DataBean.ListBean> mList;
    private OnSendFriendListInteractionListener mListener;

    public SendFriendOrderAdapter(Context context, List<FriendOrderEntity.DataBean.ListBean> list,
                                  OnSendFriendListInteractionListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public void setList(List<FriendOrderEntity.DataBean.ListBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_send_friend_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTvId.setText(String.valueOf(mList.get(position).getOrderId()));
        holder.mTvTime.setText(mList.get(position).getOrderTime());
        holder.mTvName.setText(mList.get(position).getFriendName());
        holder.mTvStart.setText("状态: " + mList.get(position).getStateName());
        if (mList.get(position).getProductList().size() >= 1) {
            holder.mTvCupCount.setText("送出桶数: "
                    + mList.get(position).getProductList().get(0).getCount() + "桶");
        }
        if (mList.get(position).getProductList().size() >= 2) {
            holder.mTvWaterCount.setText("送出箱数: "
                    + mList.get(position).getProductList().get(1).getCount() + "箱");
        }

        if (0 == mList.get(position).getState()) {
            holder.mBtnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.mBtnCancel.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDetailInteraction(mList.get(position));
            }
        });
        holder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelInteraction(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_id)
        TextView mTvId;
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_water_count)
        TextView mTvWaterCount;
        @Bind(R.id.tv_cup_count)
        TextView mTvCupCount;
        @Bind(R.id.tv_start)
        TextView mTvStart;
        @Bind(R.id.btn_cancel)
        Button mBtnCancel;

        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }

    public interface OnSendFriendListInteractionListener {
        void onDetailInteraction(FriendOrderEntity.DataBean.ListBean item);

        void onCancelInteraction(FriendOrderEntity.DataBean.ListBean item);
    }
}
