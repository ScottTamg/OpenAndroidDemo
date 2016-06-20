package com.anxin.changbaishan.view.adapter;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BonusPointsEntity;
import com.anxin.changbaishan.utils.ImageLoadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/29.
 */
public class BonusPointsAdapter extends RecyclerView.Adapter<BonusPointsAdapter.ViewHolder> {
    private List<BonusPointsEntity.DataBean.ListBean> mList;
    private Fragment mFragment;

    public BonusPointsAdapter(Fragment fragment, List<BonusPointsEntity.DataBean.ListBean> list) {
        this.mList = list;
        this.mFragment = fragment;
    }

    public void addData(List<BonusPointsEntity.DataBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bonus_points_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvTitle.setText(mList.get(position).getName());
        ImageLoadUtil.loadImage(mFragment, mList.get(position).getPhoto(), holder.mImgIcon);
        holder.mTvCount.setText(String.valueOf(mList.get(position).getCount()));
        holder.mTvStandard.setText(mList.get(position).getStateName());
        holder.mTvMoney.setText(mList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.img_icon)
        ImageView mImgIcon;
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
