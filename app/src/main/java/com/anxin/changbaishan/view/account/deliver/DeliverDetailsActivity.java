package com.anxin.changbaishan.view.account.deliver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.DeliveryModelEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.ImageLoadUtil;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.DeliverDetailAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DeliverDetailsActivity extends SwipeBackActivity {

    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private int mOrderId;
    private DeliveryModelEntity.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_details);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("配送详情");

        mOrderId = getIntent().getExtras().getInt("OrderId", 0);
        if (0 != mOrderId) {
            getMyDeliveryModel();
        }

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyDeliveryModel();
            }
        });
    }

    private void getMyDeliveryModel() {
        VolleyRequest.getMyDeliveryModel(mOrderId, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            DeliveryModelEntity entity =
                                    loadDataUtil.getJsonData(response, DeliveryModelEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getMyDeliveryModel();
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
        DeliverDetailAdapter adapter = new DeliverDetailAdapter(mActivity, mData.getRecordList());
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(adapter);
//        RecycleViewDivider divider = new RecycleViewDivider(mActivity
//                , LinearLayoutManager.HORIZONTAL, 2
//                , mActivity.getResources().getColor(R.color.divider_gray));
//        mList.addItemDecoration(divider);
        mList.setAdapter(headerAndFooterRecyclerViewAdapter);
        mList.setLayoutManager(new LinearLayoutManager(mActivity));

        setHeadView();
    }

    private void setHeadView() {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout headView =
                (LinearLayout)inflater.inflate(R.layout.layout_deliver_details_header, null);
        ImageView imgIcon = (ImageView)headView.findViewById(R.id.img_icon);
        ImageView imgOutIcon = (ImageView)headView.findViewById(R.id.img_out_of_stock);
        TextView tvTitle = (TextView)headView.findViewById(R.id.tv_title);
        TextView tvCount = (TextView)headView.findViewById(R.id.tv_count);
        TextView tvMoney = (TextView)headView.findViewById(R.id.tv_money);
        TextView tvStart = (TextView)headView.findViewById(R.id.tv_start);
        TextView tvId = (TextView)headView.findViewById(R.id.tv_id);
        TextView tvTime = (TextView)headView.findViewById(R.id.tv_time);
        TextView tvRemark = (TextView)headView.findViewById(R.id.tv_remark);

        if (mData.getOrderList().size() >= 1) {
            ImageLoadUtil.loadImage(mActivity, mData.getOrderList().get(0).getPhoto(), imgIcon);
            imgOutIcon.setVisibility(View.GONE);
            tvTitle.setText(mData.getOrderList().get(0).getName());
            tvCount.setText("X" + mData.getOrderList().get(0).getCount());
        }
        if (mData.getOrderList().size() >= 2) {
            tvMoney.setText("X" + mData.getOrderList().get(1).getCount());
        }
        tvStart.setText(mData.getStateName());
        tvId.setText(String.valueOf(mData.getID()));
        tvTime.setText(mData.getCTime());
        tvRemark.setText(mData.getStateName());

        RecyclerViewUtils.setHeaderView(mList, headView);
    }
}
