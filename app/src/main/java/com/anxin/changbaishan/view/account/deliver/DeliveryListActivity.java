package com.anxin.changbaishan.view.account.deliver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.DeliveryListEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.DeliveryItemAdapter;
import com.anxin.changbaishan.view.adapter.RecyclerViewStateUtils;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.LoadingFooter;
import com.anxin.changbaishan.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DeliveryListActivity extends SwipeBackActivity implements 
        DeliveryItemAdapter.OnDeliveryListInteractionListener{
    private static final String REQUEST_KEY = "OrderId";

    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private DeliveryListEntity.DataBean mDataBean;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private DeliveryItemAdapter mAdapter;
    private DeliveryListEntity.DataBean.ListBean mItem;
    private int pageIndex = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitleName("配送记录");
        setBack();

        getMyDeliveryList();

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getMyDeliveryList();
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setItemAnimator(new DefaultItemAnimator());
//        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
//                , 2, mActivity.getResources().getColor(R.color.divider_gray)));
        mList.addOnScrollListener(mOnScrollListener);
    }

    private void getMyDeliveryList() {
        VolleyRequest.getMyDeliveryList(pageSize, pageIndex, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            DeliveryListEntity entity =
                                    loadDataUtil.getJsonData(response, DeliveryListEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mDataBean = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getMyDeliveryList();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
                        }
                        mSwipeRefresh.setRefreshing(false);
                        RecyclerViewStateUtils.setFooterViewState(mList, LoadingFooter.State.Normal);
                    }
                });
    }

    private void showData() {
        if (null != mDataBean.getList() && !mDataBean.getList().isEmpty()) {
            if (null == mAdapter) {
                mAdapter = new DeliveryItemAdapter(mActivity, mDataBean.getList(),
                        (DeliveryItemAdapter.OnDeliveryListInteractionListener) mActivity);
                mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
                mList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            } else if (pageIndex == 1) {
                mAdapter.setList(mDataBean.getList());
            } else {
                mAdapter.addData(mDataBean.getList());
            }
        }
    }

    private void cancelDeliveryOrder() {
        VolleyRequest.cancelDeliveryOrder(mItem.getID(), this.getClass().getSimpleName() + "cancel",
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity =
                                    loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {
                                showShortToast(entity.getMessage());
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                cancelDeliveryOrder();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
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
                getMyDeliveryList();
            } else {
                RecyclerViewStateUtils.setFooterViewState(mActivity, mList, pageSize, LoadingFooter.State.TheEnd, null);
            }
        }
    };


    @Override
    public void onDetailItemInteraction(DeliveryListEntity.DataBean.ListBean item) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_KEY, item.getID());
        startAnimActivity(DeliverDetailsActivity.class, bundle);
    }

    @Override
    public void onCancelItemInteraction(DeliveryListEntity.DataBean.ListBean item) {
        mItem = item;
        cancelDeliveryOrder();
    }

    @Override
    public void onCommentItemInteraction(DeliveryListEntity.DataBean.ListBean item) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_KEY, item.getID());
        startAnimActivity(DeliverCommentActivity.class, bundle);
    }
}
