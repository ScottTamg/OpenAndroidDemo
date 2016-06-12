package com.anxin.changbaishan.view.account.friend;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.MyFriendOrderDetail;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.FriendOrderItemAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendOrderDetailsActivity extends SwipeBackActivity {

    @Bind(R.id.list)
    RecyclerView mList;

    private int mOrderId;
    private MyFriendOrderDetail.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("订单详情");

        mOrderId = getIntent().getExtras().getInt("OrderId", 0);
        if (0 != mOrderId) {
            getMyFriendDetail();
        }
    }

//    private void getOrderModel() {
//        VolleyRequest.getOrderModel(mOrderId, this.getClass().getSimpleName(),
//                new VolleyRequestListener() {
//                    @Override
//                    public void success(boolean isSuccess, String response, String error) {
//                        if (isSuccess) {
//                            OrderModelEntity entity = loadDataUtil.getJsonData(response,
//                                    OrderModelEntity.class);
//                            if (0 == entity.getErrorNo()) {
//                                mData = entity.getData();
//                                showData();
//                            } else if (-99 == entity.getErrorNo()) {
//                                spUtil.put(spUtil.TOKEN, "");
//                                getOrderModel();
//                            } else if (-52 == entity.getErrorNo()) {
//                                spUtil.put(spUtil.ATOKEN, "");
//                                startAnimActivity(RegisterActivity.class);
//                            } else {
//                                showShortToast(entity.getMessage());
//                            }
//                        } else {
//                            showShortToast(error);
//                        }
//                    }
//                });
//    }

    private void getMyFriendDetail() {
        VolleyRequest.getMyFriendDetail(mOrderId, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            MyFriendOrderDetail entity = loadDataUtil.getJsonData(response,
                                    MyFriendOrderDetail.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(spUtil.TOKEN, "");
                                getMyFriendDetail();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(spUtil.ATOKEN, "");
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

    private void showData() {
        FriendOrderItemAdapter adapter = new FriendOrderItemAdapter(this, mData.getProductList());
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(adapter);
        RecycleViewDivider divider = new RecycleViewDivider(mActivity
                , LinearLayoutManager.HORIZONTAL, 2
                , mActivity.getResources().getColor(R.color.divider_gray));
        mList.addItemDecoration(divider);
        mList.setAdapter(headerAndFooterRecyclerViewAdapter);
        mList.setLayoutManager(new LinearLayoutManager(mActivity));

        setFootView();
    }

    private void setFootView() {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout footView =
                (LinearLayout) inflater.inflate(R.layout.layout_order_details_footer, null);
        TextView mTvOrderState = (TextView) footView.findViewById(R.id.tv_order_state);
        TextView mTvOrderNumber = (TextView) footView.findViewById(R.id.tv_order_number);
        TextView mTvOrderTime = (TextView) footView.findViewById(R.id.tv_order_time);
        TextView mTvOrderNote = (TextView) footView.findViewById(R.id.tv_order_note);
        TextView mTvOrderAmount = (TextView) footView.findViewById(R.id.tv_order_amount);
        TextView mTvBonusPoints = (TextView) footView.findViewById(R.id.tv_bonus_points);
        TextView mTvRealMoney = (TextView) footView.findViewById(R.id.tv_bonus_points);
        Button mBtnPayment = (Button) footView.findViewById(R.id.btn_payment);


        mTvOrderState.setText(mData.getStateName());
        mTvOrderNumber.setText(String.valueOf(mData.getFriendMobile()));
        mTvBonusPoints.setText(mData.getOrderTime());
        mTvRealMoney.setText(String.valueOf(mData.getFriendName()));
        mTvOrderTime.setText(mData.getOrderTime());
        mTvOrderNote.setText(String.valueOf(mData.getFriendToken()));
        mTvOrderAmount.setText(mData.getStateName());

        RecyclerViewUtils.setFooterView(mList, footView);
    }
}
