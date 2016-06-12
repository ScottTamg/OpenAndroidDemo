package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.ProductEntity;
import com.anxin.changbaishan.utils.ImageLoadUtil;
import com.anxin.changbaishan.view.home.HomeFragment;
import com.anxin.changbaishan.view.home.HomeFragment.OnListHomeFragmentInteractionListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/4/11.
 */
public class RCYProductItemAdapter extends RecyclerView.Adapter<RCYProductItemAdapter.ViewHolder> {

    private final List<ProductEntity.DataBean.ListBean> mValues;
    private final HomeFragment.OnListHomeFragmentInteractionListener mListener;
    private Context mContext;

    public RCYProductItemAdapter(Context context, List<ProductEntity.DataBean.ListBean> items, OnListHomeFragmentInteractionListener listener) {
        this.mContext = context;
        this.mValues = items;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(Html.fromHtml(mValues.get(position).getName()
                + "<font color='#999999'>" + mValues.get(position).getSummary()
                + "</font>"));
//        holder.tvContent.setText(mValues.get(position).getSummary());
        holder.tvStandard.setText("规格：" + mValues.get(position).getStandard());
        holder.tvMoney.setText("￥：" + mValues.get(position).getSellPrice());
        ImageLoadUtil.loadImage(mContext, mValues.get(position).getPhoto(), holder.imgIcon);

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    int[] location = new int[2];
                    holder.imgIcon.getLocationInWindow(location);
                    Drawable drawable = holder.imgIcon.getDrawable();
                    mListener.onListHomeFragmentInteraction(holder.mItem, drawable, location);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.img_icon)
        ImageView imgIcon;
        @Bind(R.id.img_out_of_stock)
        ImageView imgOutOfStock;
        @Bind(R.id.tv_count)
        TextView tvContent;
        @Bind(R.id.tv_standard)
        TextView tvStandard;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.img_add)
        Button imgAdd;

        public ProductEntity.DataBean.ListBean mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
