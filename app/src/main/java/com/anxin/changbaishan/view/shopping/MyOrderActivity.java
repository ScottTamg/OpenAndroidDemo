package com.anxin.changbaishan.view.shopping;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.OrderModelEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.widget.RecycleViewDivider;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.adapter.OrderItemAdapter;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.SwitchButton.SwitchButton;
import com.anxin.changbaishan.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.anxin.changbaishan.widget.recyclerview.RecyclerViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends SwipeBackActivity {

    @Bind(R.id.list)
    RecyclerView mList;
    @Bind(R.id.tv_total)
    TextView mTvTotal;
    @Bind(R.id.tv_freight)
    TextView mTvFreight;
    @Bind(R.id.btn_done)
    Button mBtnDone;

    private int mOrderId;
    private OrderModelEntity.DataBean mData;
    private int mUserBonusPoints = 0;
    private EditText mEtremark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitle("我的订单");
        Bundle bundle = getIntent().getExtras();
        mOrderId = Integer.parseInt(bundle.getString("orderId"));
        if (0 != mOrderId) {
            getOrderModel();
        }
    }

    private void showData() {

        mTvTotal.setText(mData.getRealMoney() + "元");

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
        LinearLayout footView = (LinearLayout) inflater.inflate(R.layout.layout_order_footer, null);
        mEtremark = (EditText) footView.findViewById(R.id.et_remark);
        TextView tvPointMessage = (TextView) footView.findViewById(R.id.tv_bonus_points_message);
        SwitchButton swUse = (SwitchButton) footView.findViewById(R.id.switch_use);

        tvPointMessage.setText(mData.getBonusPointsMessage());
        RecyclerViewUtils.setFooterView(mList, footView);
        swUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mUserBonusPoints = 1;
                } else {
                    mUserBonusPoints = 0;
                }
            }
        });

    }

    @OnClick(R.id.btn_done)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                startProgressDialog();
                ConfirmOrders();
                break;
        }
    }

    private void getOrderModel() {
        VolleyRequest.getOrderModel(mOrderId, this.getClass().getSimpleName(), new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            OrderModelEntity entity = loadDataUtil.getJsonData(response, OrderModelEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getOrderModel();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
                        }
                        stopProgressDialog();
                    }
                });
    }

    private void ConfirmOrders() {
        VolleyRequest.confirmOrders(mOrderId, 0, "", mUserBonusPoints,
                mEtremark.getText().toString(), this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {
                                // TODO: 2016/5/11 确认订单成功
                                showShortToast("确认订单成功");
                                Bundle bundle = new Bundle();
                                bundle.putInt("mOrderId", mOrderId);
                                startAnimActivity(SelectPaymentMethodActivity.class, bundle);
                                finish();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                ConfirmOrders();
                            } else if (-52 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.ATOKEN, "");
                                startAnimActivity(RegisterActivity.class);
                            } else {
                                showShortToast(entity.getMessage());
                            }
                        } else {
                            showShortToast(error);
                        }
                        stopProgressDialog();
                    }
                });
    }
}
