package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.dummy.DummyContent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/4/12.
 */
public class ProductItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<DummyContent.DummyItem> mList;

    public ProductItemAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<DummyContent.DummyItem> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void addData(List<DummyContent.DummyItem> data) {
        if (mList == null) {
            mList = data;
        } else {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_product_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvTitle.setText(mList.get(position).id);
        holder.mTvContent.setText(mList.get(position).content);
        holder.mTvMoney.setText(mList.get(position).content);
        final int i = position;
        holder.mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "添加商品：" + i, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.img_icon)
        ImageView mImgIcon;
        @Bind(R.id.img_out_of_stock)
        ImageView mImgOutOfStock;
        @Bind(R.id.tv_count)
        TextView mTvContent;
        @Bind(R.id.tv_money)
        TextView mTvMoney;
        @Bind(R.id.img_add)
        ImageView mImgAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
