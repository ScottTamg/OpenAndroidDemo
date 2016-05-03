package com.anxin.changbaishan.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.adapter.ShoppingCartItemAdapter;
import com.anxin.changbaishan.view.dummy.DummyContent;
import com.anxin.changbaishan.view.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingCartFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @Bind(R.id.ll_left)
    LinearLayout mLlLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @Bind(R.id.tv_total)
    TextView mTvTotal;
    @Bind(R.id.btn_settlement)
    Button mBtnSettlement;
    @Bind(R.id.rl_total)
    RelativeLayout mRlTotal;

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MainTabActivity mActivity;
    private ShoppingCartItemAdapter mCartItemAdapter;
    private List<DummyItem> mCheckedItems;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingCartFragment() {
    }

    public static ShoppingCartFragment newInstance(int columnCount) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCartData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainTabActivity) context;  //解决在“内存重启”之后调用GetActivity()方法报空指针异常
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_left, R.id.btn_right, R.id.btn_settlement, R.id.checkbox_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                break;
            case R.id.btn_right:
                mActivity.showShortToast("客服订水");
                break;
            case R.id.btn_settlement:
                // TODO: 2016/4/28 模拟下单成功
                mActivity.removeListShoppingCartData(mCheckedItems);
                for (DummyItem item : mCheckedItems) {
                    mCartItemAdapter.getDummyItems().remove(item);
                }
                mCartItemAdapter.notifyDataSetChanged();
                checkedOptions();
                Intent intent = new Intent(mActivity, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.checkbox_all:
                boolean flag = mCheckboxAll.isChecked();
//                mCheckboxAll.setChecked(flag);
                Log.e("checkbox_all", "checkbox_all" + flag);
                mActivity.checkedAllShoppingCartData(flag);

                for (DummyContent.DummyItem item : mCartItemAdapter.getDummyItems()) {
                    item.isChecked = flag;
                }
                mCartItemAdapter.notifyDataSetChanged();
                checkedOptions();
        }
    }

    private void init() {
        //Set the adapter
        if (mColumnCount <= 1) {
            mList.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            mList.setLayoutManager(new GridLayoutManager(mActivity, mColumnCount));
        }

//        mCartItemAdapter = new ShoppingCartItemAdapter(mActivity.getShoppingCartData(), mListener);
//        mList.setAdapter(mCartItemAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL, 2, Color.parseColor("#cccccc")));

        mLlLeft.setVisibility(View.GONE);
        mTvTitle.setText("购物车");

    }

    public void checkedOptions() {
        mCheckedItems = new ArrayList<>();
        boolean checkedAll = true;
        int count = 0;
        int total = 0;
        for (DummyItem item : mCartItemAdapter.getDummyItems()) {
            if (item.isChecked) {
                mCheckedItems.add(item);
                count++;
                total += item.number;
            } else {
                checkedAll = false;
            }
        }
        mBtnSettlement.setText("去结算 (" + count + ")");
        mTvTotal.setText(total + "元");
        mCheckboxAll.setChecked(checkedAll);
    }

    public void refreshCartData() {
        List<DummyItem> items = new ArrayList<>();
        Iterator<DummyItem> iterator = mActivity.getShoppingCartData().values().iterator();
        while (iterator.hasNext()) {
            items.add(iterator.next());
        }

        mCartItemAdapter = new ShoppingCartItemAdapter(items, mListener);
        mList.setAdapter(mCartItemAdapter);
        mCartItemAdapter.notifyDataSetChanged();
        checkedOptions();
    }

    public void syncCartData() {
        Map<String, DummyItem> map = new ArrayMap<>();
        for (DummyItem item : mCartItemAdapter.getDummyItems()) {
            if (!map.containsKey(item.id)) {
                map.put(item.id, item);
            } else {
                map.get(item.id).number += item.number;
            }
        }
        mActivity.setShoppingCartData(map);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);

        void onDeleteItemInteraction(DummyItem item);

        void onCheckedItemInteraction(DummyItem item);

        void onNumberChangedInteraction(DummyItem item);
    }

}
