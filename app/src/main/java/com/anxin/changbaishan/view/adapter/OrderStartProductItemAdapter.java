package com.anxin.changbaishan.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyOrderItemEntity;
import com.anxin.changbaishan.utils.ImageLoadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/19.
 */
public class OrderStartProductItemAdapter extends
        RecyclerView.Adapter<OrderStartProductItemAdapter.ViewHolder> {
    private Activity mActivity;
    private List<MyOrderItemEntity.DataBean.ListBean.ProductListBean> mProductList;

    public OrderStartProductItemAdapter(Activity activity,
                                        List<MyOrderItemEntity.DataBean.ListBean.ProductListBean>
                                                productList) {
        this.mActivity = activity;
        this.mProductList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_start_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoadUtil.loadImage(mActivity, mProductList.get(position).getPhoto(), holder.mImgIcon);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_icon)
        ImageView mImgIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
