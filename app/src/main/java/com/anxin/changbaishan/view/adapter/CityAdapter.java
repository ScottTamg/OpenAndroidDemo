package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.CityEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/6.
 */
public class CityAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityEntity.DataBean.ListBean> mList;

    public CityAdapter(Context context, List<CityEntity.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_area_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvContent.setText(mList.get(position).getCityName());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_count)
        TextView mTvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
