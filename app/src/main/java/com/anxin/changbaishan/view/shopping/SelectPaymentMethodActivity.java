package com.anxin.changbaishan.view.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPaymentMethodActivity extends SwipeBackActivity {

    @Bind(R.id.cb_wx)
    CheckBox mCbWx;
    @Bind(R.id.rl_wx)
    RelativeLayout mRlWx;
    @Bind(R.id.cb_zfb)
    CheckBox mCbZfb;
    @Bind(R.id.rl_zfb)
    RelativeLayout mRlZfb;
    @Bind(R.id.btn_done)
    Button mBtnDone;

    private int mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("支付");

        mOrderId = getIntent().getExtras().getInt("mOrderId", 0);

        mCbWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbZfb.setChecked(false);
                }
            }
        });

        mCbZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbWx.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.rl_wx, R.id.rl_zfb, R.id.btn_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wx:
                mCbWx.setChecked(!mCbWx.isChecked());
                if (mCbWx.isChecked()) {
                    mCbZfb.setChecked(false);
                }
                break;
            case R.id.rl_zfb:
                mCbZfb.setChecked(!mCbZfb.isChecked());
                if (mCbZfb.isChecked()) {
                    mCbWx.setChecked(false);
                }
                break;
            case R.id.btn_done:
                paySuccess();
                break;
        }
    }

    private void paySuccess() {
        VolleyRequest.paySuccess(mOrderId, this.getClass().getSimpleName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                    if (0 == entity.getErrorNo()) {
                        showShortToast("确认订单成功");
                        scrollToFinishActivity();
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        paySuccess();
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
