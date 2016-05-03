package com.anxin.changbaishan.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.HomeFragment;
import com.anxin.changbaishan.view.HomeFragment.OnListHomeFragmentInteractionListener;
import com.anxin.changbaishan.view.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/4/11.
 */
public class RCYProductItemAdapter extends RecyclerView.Adapter<RCYProductItemAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final HomeFragment.OnListHomeFragmentInteractionListener mListener;

    public RCYProductItemAdapter(List<DummyItem> items, OnListHomeFragmentInteractionListener listener) {
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
        holder.tvTitle.setText(mValues.get(position).id);
        holder.tvContent.setText(mValues.get(position).content);
        holder.tvMoney.setText(mValues.get(position).content);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListHomeFragmentInteraction(holder.mItem);
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
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.img_add)
        ImageView imgAdd;

        public DummyItem mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
