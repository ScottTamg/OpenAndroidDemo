package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.DeliveryListEntity;
import com.anxin.changbaishan.utils.ImageLoadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/25.
 */
public class DeliveryItemAdapter extends RecyclerView.Adapter<DeliveryItemAdapter.ViewHolder> {
    private Context mContext;
    private List<DeliveryListEntity.DataBean.ListBean> mList;
    private OnDeliveryListInteractionListener mListener;

    public DeliveryItemAdapter(Context context, List<DeliveryListEntity.DataBean.ListBean> list,
                               OnDeliveryListInteractionListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public List<DeliveryListEntity.DataBean.ListBean> getList() {
        return mList;
    }

    public void setList(List<DeliveryListEntity.DataBean.ListBean> list) {
        if (null != list && list.size() > 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public void addData(List<DeliveryListEntity.DataBean.ListBean> list) {
        if (null != list && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_delivery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mList.get(position);
        holder.mTvTime.setText(mList.get(position).getCTime());
        holder.mTvState.setText(mList.get(position).getStateName());
        holder.mTvTitle.setText(mList.get(position).getOrderDetail().getName());
        holder.mTvStandard.setText(mList.get(position).getOrderDetail().getStandard());
        holder.mTvMoney.setText("配送：X" + mList.get(position).getOrderDetail().getWaterCount());
        holder.mTvCount.setText("水桶：X" + mList.get(position).getOrderDetail().getCupCount());
        ImageLoadUtil.loadImage(mContext, mList.get(position).getOrderDetail().getPhoto(),
                holder.mImgIcon);

        switch (mList.get(position).getState()) {
            case 0:
                holder.mBtnCancel.setVisibility(View.GONE);
                holder.mBtnComment.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (1 == mList.get(position).getHasComment()) {
                    holder.mBtnComment.setVisibility(View.VISIBLE);
                } else {
                    holder.mBtnComment.setVisibility(View.VISIBLE);
                }
                break;
            default:
                holder.mBtnCancel.setVisibility(View.GONE);
                holder.mBtnComment.setVisibility(View.GONE);
                break;
        }

        holder.mBtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDetailItemInteraction(holder.mItem);
            }
        });
        holder.mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCommentItemInteraction(holder.mItem);
            }
        });
        holder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelItemInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.tv_state)
        TextView mTvState;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.img_icon)
        ImageView mImgIcon;
        @Bind(R.id.img_out_of_stock)
        ImageView mImgOutOfStock;
        @Bind(R.id.tv_standard)
        TextView mTvStandard;
        @Bind(R.id.tv_money)
        TextView mTvMoney;
        @Bind(R.id.list_porduct)
        RelativeLayout mListPorduct;
        @Bind(R.id.btn_details)
        Button mBtnDetails;
        @Bind(R.id.btn_cancel)
        Button mBtnCancel;
        @Bind(R.id.btn_comment)
        Button mBtnComment;

        public DeliveryListEntity.DataBean.ListBean mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDeliveryListInteractionListener {
        void onDetailItemInteraction(DeliveryListEntity.DataBean.ListBean item);

        void onCancelItemInteraction(DeliveryListEntity.DataBean.ListBean item);

        void onCommentItemInteraction(DeliveryListEntity.DataBean.ListBean item);
    }
}
