package com.anxin.changbaishan.view.account.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.OrderModelEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.OrderItemAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.view.shopping.SelectPaymentMethodActivity;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends SwipeBackActivity {

    @Bind(R.id.list)
    RecyclerView mList;

    private int mOrderId;
    private int mStart;
    private OrderModelEntity.DataBean mData;

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
        mStart = getIntent().getExtras().getInt("section_number", 0);
        if (0 != mOrderId) {
            getOrderModel();
        }
    }

    private void getOrderModel() {
        VolleyRequest.getOrderModel(mOrderId, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            OrderModelEntity entity = loadDataUtil.getJsonData(response,
                                    OrderModelEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(spUtil.TOKEN, "");
                                getOrderModel();
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
        OrderItemAdapter adapter = new OrderItemAdapter(this, mData.getList());
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

        if(0 == mStart) {
            mBtnPayment.setVisibility(View.VISIBLE);
        } else {
            mBtnPayment.setVisibility(View.GONE);
        }

        mBtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("mOrderId", mOrderId);
                startAnimActivity(SelectPaymentMethodActivity.class, bundle);
            }
        });

        mTvOrderState.setText(mData.getBonusPointsMessage());
        mTvOrderNumber.setText(String.valueOf(mData.getBonusPoints()));
        mTvBonusPoints.setText(mData.getNeedMoney());
        mTvRealMoney.setText(String.valueOf(mData.getRealMoney()));
        mTvOrderTime.setText(mData.getNeedMoney());
        mTvOrderNote.setText(String.valueOf(mData.getBonusMoney()));
        mTvOrderAmount.setText(mData.getBonusPointsMessage());

        RecyclerViewUtils.setFooterView(mList, footView);
    }
}
