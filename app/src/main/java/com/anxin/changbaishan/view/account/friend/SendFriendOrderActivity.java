package com.anxin.changbaishan.view.account.friend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.FriendOrderEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.SendFriendOrderAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.RecycleViewDivider;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendFriendOrderActivity extends SwipeBackActivity
        implements SendFriendOrderAdapter.OnSendFriendListInteractionListener{

    @Bind(R.id.tv_water_count)
    TextView mTvWaterCount;
    @Bind(R.id.tv_cup_count)
    TextView mTvCupCount;
    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private FriendOrderEntity.DataBean mData;
    private SendFriendOrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_order);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("送出的礼物");

        getMyFriendOrderList();

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyFriendOrderList();
            }
        });

        mList.setLayoutManager(new LinearLayoutManager(mActivity));
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL
                , 2, mActivity.getResources().getColor(R.color.divider_gray)));
    }


    private void getMyFriendOrderList() {
        VolleyRequest.getMyFriendOrderList(this.getClass().getSimpleName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    FriendOrderEntity entity =
                            loadDataUtil.getJsonData(response, FriendOrderEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mData = entity.getData();
                        showData();
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        getMyFriendOrderList();
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
            }
        });
    }

    private void showData() {
        if (null != mData) {
            mTvWaterCount.setText(mData.getWaterCount() + "箱水");
            mTvCupCount.setText(mData.getCupCount() + "个桶");
            if (null == mAdapter) {
                mAdapter = new SendFriendOrderAdapter(mActivity, mData.getList(),
                        (SendFriendOrderAdapter.OnSendFriendListInteractionListener) mActivity);
                mList.setAdapter(mAdapter);
            } else {
                mAdapter.setList(mData.getList());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void cancelFriendOrder(final int orderId) {
        VolleyRequest.cancelFriendOrder(orderId, this.getClass().getSimpleName() + "cancel",
                new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    BaseEntity entity =
                            loadDataUtil.getJsonData(response, BaseEntity.class);
                    if (0 == entity.getErrorNo()) {
                        // TODO: 2016/5/31
                        getMyFriendOrderList();
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        cancelFriendOrder(orderId);
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

    @Override
    public void onDetailInteraction(FriendOrderEntity.DataBean.ListBean item) {
        // TODO: 2016/5/31
        showShortToast("Detail " + item.getOrderId());
        Bundle bundle = new Bundle();
        bundle.putInt("OrderId", item.getOrderId());
        startAnimActivity(FriendOrderDetailsActivity.class, bundle);
    }

    @Override
    public void onCancelInteraction(FriendOrderEntity.DataBean.ListBean item) {
        showShortToast("cancel " + item.getOrderId());
//        cancelFriendOrder(item.getOrderId());
    }
}
