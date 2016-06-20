package com.anxin.changbaishan.view.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyOrderItemEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Txw on 2016/5/19.
 */
public class OrderStartItemAdapter extends RecyclerView.Adapter<OrderStartItemAdapter.ViewHolder> {
    private List<MyOrderItemEntity.DataBean.ListBean> mList;
    private Activity mActivity;
    private OnOrderStratListInteractionListener mListener;

    public OrderStartItemAdapter(Activity activity, List<MyOrderItemEntity.DataBean.ListBean> list,
                                 OnOrderStratListInteractionListener listener) {
        this.mActivity = activity;
        this.mList = list;
        this.mListener = listener;
    }

    public void addData(List<MyOrderItemEntity.DataBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setList(List<MyOrderItemEntity.DataBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public List<MyOrderItemEntity.DataBean.ListBean> getList() {
        return mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_state_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mList.get(position);
        holder.mTvTime.setText(mList.get(position).getOrderTime());
        holder.mTvState.setText(mList.get(position).getStateName());
        holder.mTvTotal.setText(mList.get(position).getNeedMoney());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mListProduct.setLayoutManager(layoutManager);
        OrderStartProductItemAdapter adapter = new OrderStartProductItemAdapter(mActivity,
                mList.get(position).getProductList());
        holder.mListProduct.setAdapter(adapter);

        if (0 == mList.get(position).getState()) {
            holder.mBtnPayment.setVisibility(View.VISIBLE);
            holder.mBtnCancel.setVisibility(View.VISIBLE);
            holder.mBtnBuyAgain.setVisibility(View.GONE);
        } else {
            holder.mBtnPayment.setVisibility(View.GONE);
            holder.mBtnCancel.setVisibility(View.GONE);
            holder.mBtnBuyAgain.setVisibility(View.GONE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemInteraction(holder.mItem);
            }
        });
        holder.mBtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPaymentInteraction(holder.mItem);
            }
        });
        holder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelInteraction(holder.mItem);
            }
        });
        holder.mBtnBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBuyAgainInteraction(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.tv_state)
        TextView mTvState;
        @Bind(R.id.list_porduct)
        RecyclerView mListProduct;
        @Bind(R.id.tv_total)
        TextView mTvTotal;
        @Bind(R.id.btn_details)
        Button mBtnCancel;
        @Bind(R.id.btn_payment)
        Button mBtnPayment;
        @Bind(R.id.btn_buy_again)
        Button mBtnBuyAgain;

        public MyOrderItemEntity.DataBean.ListBean mItem;
        public final View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnOrderStratListInteractionListener {
        void onItemInteraction(MyOrderItemEntity.DataBean.ListBean item);

        void onPaymentInteraction(MyOrderItemEntity.DataBean.ListBean item);

        void onCancelInteraction(MyOrderItemEntity.DataBean.ListBean item);

        void onBuyAgainInteraction(MyOrderItemEntity.DataBean.ListBean item);
    }
}
