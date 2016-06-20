package com.anxin.changbaishan.view.account.order;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.MyOrderItemEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.OrderStartItemAdapter;
import com.anxin.changbaishan.view.adapter.RecyclerViewStateUtils;
import com.anxin.changbaishan.view.shopping.SelectPaymentMethodActivity;
import com.anxin.changbaishan.widget.LoadingFooter;
import com.anxin.changbaishan.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment
        implements OrderStartItemAdapter.OnOrderStratListInteractionListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private int mParam;
    private OrderActivity mActivity;
    private MyOrderItemEntity.DataBean mDataBean;
    private MyOrderItemEntity.DataBean.ListBean mSelectedItem;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private OrderStartItemAdapter mAdapter;
    private static OrderFragment mFragment;
    private int pageIndex = 1;
    private int pageSize = 10;

    public OrderFragment() {
    }

    public static OrderFragment newInstance(int sectionNumber) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        mFragment = fragment;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (OrderActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    private void init() {
        getMyOrderList();

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getMyOrderList();
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setItemAnimator(new DefaultItemAnimator());
//        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
//                , 0, mActivity.getResources().getColor(R.color.bg_gray)));

        mList.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getMyOrderList() {
        VolleyRequest.getMyOrderList(pageSize, pageIndex, mParam, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            MyOrderItemEntity entity = mActivity.loadDataUtil.getJsonData(response,
                                    MyOrderItemEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mDataBean = entity.getData();
                                if (mAdapter == null) {
                                    mAdapter = new OrderStartItemAdapter(mActivity, mDataBean.getList(), mFragment);
                                    mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
                                    mList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
                                } else if (pageIndex == 1) {
                                    mAdapter.setList(mDataBean.getList());
                                } else {
                                    mAdapter.addData(mDataBean.getList());
                                    mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();

                                }

                            } else if (-99 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.TOKEN, "");
                                getMyOrderList();
                            } else if (-52 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.ATOKEN, "");
                                mActivity.startAnimActivity(RegisterActivity.class);
                            } else {
                                mActivity.showShortToast(entity.getMessage());
                            }
                        } else {
                            mActivity.showShortToast(error);
                        }
                        mSwipeRefresh.setRefreshing(false);
                        RecyclerViewStateUtils.setFooterViewState(mList, LoadingFooter.State.Normal);
                    }
                });
    }

    private void cancelOrder() {
        VolleyRequest.cancelOrder(mSelectedItem.getOrderId(),
                this.getClass().getSimpleName() + "cancel", new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity = mActivity.loadDataUtil.getJsonData(response,
                                    BaseEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mAdapter.getList().remove(mSelectedItem.getOrderId());
                                mAdapter.notifyDataSetChanged();
                            } else if (-99 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.TOKEN, "");
                                cancelOrder();
                            } else if (-52 == entity.getErrorNo()) {
                                mActivity.spUtil.put(SPUtil.ATOKEN, "");
                                mActivity.startAnimActivity(RegisterActivity.class);
                            } else {
                                mActivity.showShortToast(entity.getMessage());
                            }
                        } else {
                            mActivity.showShortToast(error);
                        }
                    }
                });
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mList);
            if (state == LoadingFooter.State.Loading) {
                return;
            }

            if (mDataBean.getTotalCount() > pageIndex * pageSize) {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, pageSize, LoadingFooter.State.Loading, null);
                pageIndex++;
                getMyOrderList();
            } else {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, pageSize, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    @Override
    public void onItemInteraction(MyOrderItemEntity.DataBean.ListBean item) {
//        mActivity.showShortToast("onItem :" + item.getOrderId());
        mSelectedItem = item;
        Bundle bundle = new Bundle();
        bundle.putInt("OrderId", item.getOrderId());
        bundle.putInt("section_number", item.getState());
        mActivity.startAnimActivity(OrderDetailsActivity.class, bundle);
    }

    @Override
    public void onPaymentInteraction(MyOrderItemEntity.DataBean.ListBean item) {
//        mActivity.showShortToast("onPayment :" + item.getOrderId());
        mSelectedItem = item;
        Bundle bundle = new Bundle();
        bundle.putInt("mOrderId", item.getOrderId());
        mActivity.startAnimActivity(SelectPaymentMethodActivity.class, bundle);
    }

    @Override
    public void onCancelInteraction(MyOrderItemEntity.DataBean.ListBean item) {
//        mActivity.showShortToast("onCancel :" + item.getOrderId());
        mSelectedItem = item;
        cancelOrder();
    }

    @Override
    public void onBuyAgainInteraction(MyOrderItemEntity.DataBean.ListBean item) {
//        mActivity.showShortToast("onBuyAgain :" + item.getOrderId());
        mSelectedItem = item;
    }
}
