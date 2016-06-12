package com.anxin.changbaishan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.AddressEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/25.
 */
public class AddressSelectItemAdapter extends RecyclerView.Adapter<AddressSelectItemAdapter.ViewHolder> {
    private Context mContext;
    private List<AddressEntity.DataBean.ListBean> mList;
    private OnAddressListInteractionListener mListener;
    private int mSelectOrderId;

    public AddressSelectItemAdapter(Context context, List<AddressEntity.DataBean.ListBean> list,
                                    int selectOrderId, OnAddressListInteractionListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
        this.mSelectOrderId = selectOrderId;
    }

    public void setList(List<AddressEntity.DataBean.ListBean> list) {
        mList = list;
    }

    public int getSelectOrderId() {
        return mSelectOrderId;
    }

    public void setSelectOrderId(int selectOrderId) {
        mSelectOrderId = selectOrderId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_select_address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mList.get(position);
        holder.mTvName.setText(mList.get(position).getUserName());
        holder.mTvPhone.setText(mList.get(position).getMobile());
        StringBuffer sb = new StringBuffer();
        sb.append(mList.get(position).getCityName());
        sb.append(mList.get(position).getDistrictName());
        sb.append(mList.get(position).getAddress());
        holder.mTvAddress.setText(sb.toString());
        if (mSelectOrderId == mList.get(position).getID()) {
            holder.mRbDefault.setChecked(true);
        } else {
            holder.mRbDefault.setChecked(false);
        }

        holder.mRbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rb_default)
        RadioButton mRbDefault;
        @Bind(R.id.tv_edit)
        ImageView mTvEdit;
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_phone)
        TextView mTvPhone;
        @Bind(R.id.tv_address)
        TextView mTvAddress;

        public AddressEntity.DataBean.ListBean mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnAddressListInteractionListener {
        void onEditItemInteraction(AddressEntity.DataBean.ListBean itme);

        void onDefaultItemInteraction(AddressEntity.DataBean.ListBean itme);
    }
}
