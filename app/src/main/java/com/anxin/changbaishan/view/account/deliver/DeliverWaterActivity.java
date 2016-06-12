package com.anxin.changbaishan.view.account.deliver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.AddressEntity;
import com.anxin.changbaishan.entity.CreatDeliveryOrdersEntity;
import com.anxin.changbaishan.entity.MyProductsListEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.PhoneUtils;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.account.address.SelectAddressActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.CustomNumberLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliverWaterActivity extends SwipeBackActivity {

    @Bind(R.id.tv_surplus)
    TextView mTvSurplus;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_mobile)
    TextView mTvMobile;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.rl_default_address)
    RelativeLayout mRlDefaultAddress;
    @Bind(R.id.send_number)
    CustomNumberLayout mSendNumber;
    @Bind(R.id.tv_min_number)
    TextView mTvMinNumber;
    @Bind(R.id.send_bucket_number)
    CustomNumberLayout mSendBucketNumber;
    @Bind(R.id.tv_min_bucket_number)
    TextView mTvMinBucketNumber;
    @Bind(R.id.rb_working_day)
    RadioButton mRbWorkingDay;
    @Bind(R.id.rb_rest_day)
    RadioButton mRbRestDay;
    @Bind(R.id.rb_all_day)
    RadioButton mRbAllDay;
    @Bind(R.id.et_remark)
    EditText mEtRemark;
    @Bind(R.id.btn_appointment)
    Button mBtnAppointment;
    @Bind(R.id.btn_right)
    Button mBtnRight;

    private MyProductsListEntity.DataBean mData;
    private int deliveryTime = 0;
    AddressEntity.DataBean.ListBean mSelectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_water);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("预约送水");
        setRight();

        mBtnRight.setText("配送记录");

        getMyProductsList();

        mSendNumber.setTextChangedListener(new CustomNumberLayout.TextChangedListener() {
            @Override
            public void onTextChangedInteraction(int value) {
                if (null != mData) {
                    mSendBucketNumber.setCurrentNum((int) (value * mData.getCupCount()));
                    mSendBucketNumber.setMaxNum((int) (value * mData.getCupCount()));
                    mTvMinBucketNumber.setText("最多选择" + (int) (value * mData.getCupCount()));
                }
            }
        });

        mSendBucketNumber.setTextChangedListener(new CustomNumberLayout.TextChangedListener() {
            @Override
            public void onTextChangedInteraction(int value) {
                // TODO: 2016/5/24
            }
        });

        mRbWorkingDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    deliveryTime = 1;
                    buttonView.setBackgroundResource(R.color.default_red);
                    buttonView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    buttonView.setBackgroundResource(R.drawable.edit_bg);
                    buttonView.setTextColor(getResources().getColor(R.color.black_666));
                }
            }
        });

        mRbRestDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    deliveryTime = 2;
                    buttonView.setBackgroundResource(R.color.default_red);
                    buttonView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    buttonView.setBackgroundResource(R.drawable.edit_bg);
                    buttonView.setTextColor(getResources().getColor(R.color.black_666));
                }
            }
        });

        mRbAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    deliveryTime = 0;
                    buttonView.setBackgroundResource(R.color.default_red);
                    buttonView.setTextColor(getResources().getColor(R.color.white));
                } else {
                    buttonView.setBackgroundResource(R.drawable.edit_bg);
                    buttonView.setTextColor(getResources().getColor(R.color.black_666));
                }
            }
        });
    }

    @OnClick({R.id.tv_phone, R.id.rl_default_address, R.id.btn_appointment, R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
//                showShortToast("电话预约");
                PhoneUtils.callDial(mActivity, PhoneUtils.PHONE);
                break;
            case R.id.rl_default_address:
//                startAnimActivity(SelectAddressActivity.class);
                Intent intent = new Intent(mActivity, SelectAddressActivity.class);
                intent.putExtra("OrderId", mData.getAddressId());
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_appointment:
                creatDeliveryOrders();
                break;
            case R.id.btn_right:
                startAnimActivity(DeliveryListActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
                mSelectedAddress = data.getParcelableExtra("SelectAddress");
                setAddress();
                break;
        }
    }

    private void setAddress() {
        if (mSelectedAddress != null) {
            mData.setAddressId(mSelectedAddress.getID());
            mData.setAddressMobile(mSelectedAddress.getMobile());
            mData.setAddressUserName(mSelectedAddress.getUserName());
            StringBuilder sb = new StringBuilder();
            sb.append(mSelectedAddress.getCityName());
            sb.append(mSelectedAddress.getDistrictName());
            sb.append(mSelectedAddress.getAddress());
            mData.setAddress(sb.toString());
            showData();
        }
    }

    private void getMyProductsList() {
        VolleyRequest.getMyProductsList(1, this.getClass().getSimpleName(),
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            MyProductsListEntity entity =
                                    loadDataUtil.getJsonData(response, MyProductsListEntity.class);
                            if (0 == entity.getErrorNo()) {
                                mData = entity.getData();
                                showData();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                getMyProductsList();
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

    private void showData() {
        mTvSurplus.setText(String.valueOf(mData.getYWaterCount()));
        mTvName.setText(mData.getAddressUserName());
        mTvMobile.setText(mData.getAddressMobile());
        mTvAddress.setText(mData.getAddress());
        mTvMinNumber.setText(String.valueOf(mData.getInDeliveryCount()) + "箱起送");
        mSendNumber.setCurrentNum(mData.getInDeliveryCount());
        mSendNumber.setMinNum(mData.getInDeliveryCount());
//        mSendNumber.setMaxNum(mData.getYWaterCount());
        mTvMinBucketNumber.setText("最多选择" + String.valueOf(
                (int) (mData.getInDeliveryCount() * mData.getCupCount())));
        mSendBucketNumber.setCurrentNum((int) (mData.getInDeliveryCount() * mData.getCupCount()));
        mSendBucketNumber.setMinNum(0);
        mSendBucketNumber.setMaxNum((int) (mData.getInDeliveryCount() * mData.getCupCount()));

    }

    private void creatDeliveryOrders() {
        StringBuilder str = new StringBuilder();
        str.append("[{\"productId\":1,\"count\":");
        str.append(mSendBucketNumber.getCurrentNum());
        str.append("},{\"productId\":2,\"count\":");
        str.append(mSendNumber.getCurrentNum());
        str.append("}]");
        Log.e("StringBuilder", "creatDeliveryOrders: " + str.toString());

        VolleyRequest.creatDeliveryOrders(mEtRemark.getText().toString() ,str.toString(),
                deliveryTime, mData.getAddressId(),
                this.getClass().getSimpleName() + "creta",
                new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            CreatDeliveryOrdersEntity entity = loadDataUtil.getJsonData(
                                    response, CreatDeliveryOrdersEntity.class);
                            if (0 == entity.getErrorNo()) {
                                showShortToast("Orderid" + entity.getData().getOrderId());
                                startAnimActivity(DeliveryListActivity.class);
                                scrollToFinishActivity();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                creatDeliveryOrders();
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

}
