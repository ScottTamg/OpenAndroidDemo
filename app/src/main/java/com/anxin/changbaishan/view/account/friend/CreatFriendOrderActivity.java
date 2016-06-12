package com.anxin.changbaishan.view.account.friend;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.CreatDeliveryOrdersEntity;
import com.anxin.changbaishan.entity.MyProductsListEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.CustomNumberLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatFriendOrderActivity extends SwipeBackActivity {

    @Bind(R.id.send_number)
    CustomNumberLayout mSendNumber;
    @Bind(R.id.tv_min_number)
    TextView mTvMinNumber;
    @Bind(R.id.send_bucket_number)
    CustomNumberLayout mSendBucketNumber;
    @Bind(R.id.tv_min_bucket_number)
    TextView mTvMinBucketNumber;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.btn_done)
    Button mBtnDone;
    @Bind(R.id.et_name)
    EditText mEtName;

    private MyProductsListEntity.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_friend_order);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("赠送好友");

        Bundle bundle = getIntent().getExtras();
        mData = bundle.getParcelable("MyProductsListEntity");

        mTvMinNumber.setText(String.valueOf(mData.getInDeliveryCount()) + "箱起送");
        mSendNumber.setCurrentNum(mData.getInDeliveryCount());
        mSendNumber.setMinNum(mData.getInDeliveryCount());
        mTvMinBucketNumber.setText("最多选择" + String.valueOf(
                (int) (mData.getInDeliveryCount() * mData.getCupCount())));
        mSendBucketNumber.setCurrentNum((int) (mData.getInDeliveryCount() * mData.getCupCount()));
        mSendBucketNumber.setMinNum(0);
        mSendBucketNumber.setMaxNum((int) (mData.getInDeliveryCount() * mData.getCupCount()));

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
    }

    @OnClick(R.id.btn_done)
    public void onClick() {
        StringBuilder str = new StringBuilder();
        str.append("[{\"productId\":1,\"count\":");
        str.append(mSendBucketNumber.getCurrentNum());
        str.append("},{\"productId\":2,\"count\":");
        str.append(mSendNumber.getCurrentNum());
        str.append("}]");
        Log.e("StringBuilder", "creatDeliveryOrders: " + str.toString());
        creatFriendOrder(str.toString());
    }

    private void creatFriendOrder(final String list) {
        VolleyRequest.creatFriendOrder(list, mEtName.getText().toString(), mEtPhone.getText().toString(),
                this.getClass().getSimpleName(), new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            CreatDeliveryOrdersEntity entity = loadDataUtil.getJsonData(
                                    response, CreatDeliveryOrdersEntity.class);
                            if (0 == entity.getErrorNo()) {
                                showShortToast("Orderid" + entity.getData().getOrderId());
                                scrollToFinishActivity();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                creatFriendOrder(list);
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
