package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyFriendOrderDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/11.
 */
public class FriendOrderItemAdapter extends RecyclerView.Adapter<FriendOrderItemAdapter.ViewHolder> {
    private List<MyFriendOrderDetail.DataBean.ProductListBean> mList;
    private Context mContext;

    public FriendOrderItemAdapter(Context context, List<MyFriendOrderDetail.DataBean.ProductListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvTitle.setText(mList.get(position).getName());
        holder.mTvStandard.setText(String.valueOf(mList.get(position).getProductID()));
        holder.mTvMoney.setText("￥：" + mList.get(position).getCount());
        holder.mTvCount.setText("X" + mList.get(position).getCount());
//        ImageLoadUtil.loadImage(mContext, mList.get(position).getPhoto(), holder.mImgIcon);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
