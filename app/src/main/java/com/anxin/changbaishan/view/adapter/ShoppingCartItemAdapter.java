package com.anxin.changbaishan.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.ShoppingCartFragment.OnListFragmentInteractionListener;
import com.anxin.changbaishan.view.dummy.DummyContent.DummyItem;
import com.anxin.changbaishan.widget.CustomNumberLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShoppingCartItemAdapter extends RecyclerView.Adapter<ShoppingCartItemAdapter.ViewHolder> {
    private OnListFragmentInteractionListener mListener;
    private List<DummyItem> mDummyItems;

    public ShoppingCartItemAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mDummyItems = items;
        mListener = listener;
    }

    public List<DummyItem> getDummyItems() {
        return mDummyItems;
    }

    public void setDummyItems(List<DummyItem> dummyItems) {
        mDummyItems = dummyItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shoppingcart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mDummyItems.get(position);
        holder.mTvTitle.setText(mDummyItems.get(position).id);
        holder.mTvContent.setText(mDummyItems.get(position).content);
        holder.mLtNum.setCurrentNum(Integer.valueOf(mDummyItems.get(position).number));
        holder.mLtNum.setTextChangedListener(new CustomNumberLayout.TextChangedListener() {
            @Override
            public void onTextChangedInteraction(int value) {
                holder.mTvMoney.setText(String.valueOf(value));
                holder.mItem.number = value;
                mListener.onNumberChangedInteraction(holder.mItem);
            }
        });
        holder.mCheckBox.setChecked(mDummyItems.get(position).isChecked);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mDummyItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount() - position);
                    mListener.onDeleteItemInteraction(holder.mItem);
                }
            }
        });
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    boolean flag = ((CheckBox)v).isChecked();
                    holder.mItem.isChecked = flag;
                    mListener.onCheckedItemInteraction(holder.mItem);
                    mDummyItems.get(position).isChecked = flag;
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDummyItems.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.img_icon)
        ImageView mImgIcon;
        @Bind(R.id.img_out_of_stock)
        ImageView mImgOutOfStock;
        @Bind(R.id.img_delete)
        ImageView mImgDelete;
        @Bind(R.id.checkBox)
        CheckBox mCheckBox;
        @Bind(R.id.tv_money)
        TextView mTvMoney;
        @Bind(R.id.lt_num)
        CustomNumberLayout mLtNum;

        public final View mView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLtNum.getCurrentNum() + "'";
        }
    }
}
