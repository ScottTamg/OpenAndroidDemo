package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.AddressEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/16.
 */
public class AddressItemAdapter extends RecyclerView.Adapter<AddressItemAdapter.ViewHolder> {
    private Context mContext;
    private List<AddressEntity.DataBean.ListBean> mList;
    private OnAddressListInteractionListener mListener;

    public AddressItemAdapter(Context context, List<AddressEntity.DataBean.ListBean> list,
                              OnAddressListInteractionListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public void setList(List<AddressEntity.DataBean.ListBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mList.get(position);
        holder.mTvName.setText(mList.get(position).getUserName());
        holder.mTvPhone.setText(mList.get(position).getMobile());
        StringBuilder sb = new StringBuilder();
        sb.append(mList.get(position).getCityName());
        sb.append(mList.get(position).getDistrictName());
        sb.append(mList.get(position).getAddress());
        holder.mTvAddress.setText(sb.toString());
        if (1 == mList.get(position).getState()) {
            holder.mCbDefault.setChecked(true);
        } else {
            holder.mCbDefault.setChecked(false);
        }

        holder.mCbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mListener.onDefaultItemInteraction(holder.mItem);
                }
            }
        });

        holder.mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditItemInteraction(holder.mItem);
            }
        });

        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteItemInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_phone)
        TextView mTvPhone;
        @Bind(R.id.tv_address)
        TextView mTvAddress;
        @Bind(R.id.rb_default)
        RadioButton mCbDefault;
        @Bind(R.id.tv_delete)
        TextView mTvDelete;
        @Bind(R.id.tv_edit)
        TextView mTvEdit;

        public AddressEntity.DataBean.ListBean mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAddressListInteractionListener {
        void onEditItemInteraction(AddressEntity.DataBean.ListBean itme);

        void onDeleteItemInteraction(AddressEntity.DataBean.ListBean itme);

        void onDefaultItemInteraction(AddressEntity.DataBean.ListBean itme);
    }

}
