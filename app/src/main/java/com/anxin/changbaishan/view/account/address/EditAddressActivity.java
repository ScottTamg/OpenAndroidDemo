package com.anxin.changbaishan.view.account.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.AddressEntity;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.home.LoationActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.SwitchButton.SwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAddressActivity extends SwipeBackActivity {

    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout mRlAddress;
    @Bind(R.id.et_message)
    EditText mEtDetailedAddress;
    @Bind(R.id.switch_default)
    SwitchButton mSwitchDefault;
    @Bind(R.id.btn_done)
    Button mBtnDone;

    private int mCityId;
    private int mAreaId;
    private int setDefault;
    private Bundle mBundle;
    private AddressEntity.DataBean.ListBean mAddressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        setTitleName("编辑收货地址");
        setBack();

        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        mAddressItem = (AddressEntity.DataBean.ListBean) bundle.get("Address");
        if (null != mAddressItem) {
            mEtName.setText(mAddressItem.getUserName());
            mEtPhone.setText(mAddressItem.getMobile());
            mCityId = mAddressItem.getCityID();
            mAreaId = mAddressItem.getDistrictID();
            mTvAddress.setText(mAddressItem.getCityName() + " " + mAddressItem.getDistrictName());
            mEtDetailedAddress.setText(mAddressItem.getAddress());
            setDefault = mAddressItem.getState();
            if (1 == mAddressItem.getState()) {
                mSwitchDefault.setChecked(true);
            } else {
                mSwitchDefault.setChecked(false);
            }
        } else {
            showShortToast("AddressItem is null.");
        }
        mSwitchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setDefault = 1;
                } else {
                    setDefault = 0;
                }
            }
        });
    }

    @OnClick({R.id.rl_address, R.id.btn_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                Intent intent = new Intent(mActivity, LoationActivity.class);
                intent.putExtra("address", true);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_done:
                validate();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            mTvAddress.setText(data.getStringExtra("CityName") + "   " + data.getStringExtra("AreaName"));
            mCityId = data.getIntExtra("CityId", 0);
            mAreaId = data.getIntExtra("AreaId", 0);
        }
    }

    private void validate() {

        if (null == mEtName.getText().toString() || mEtName.getText().toString().length() <= 0) {
            showShortToast("请输入收货人姓名");
        } else if (null == mEtPhone.getText().toString() || mEtPhone.getText().toString().length() <= 0) {
            showShortToast("请输入手机号码");
        } else if (null == mEtDetailedAddress.getText().toString() || mEtDetailedAddress.getText().toString().length() <= 0) {
            showShortToast("请输入详细地址");
        } else if (0 == mCityId) {
            showShortToast("请选择所在地区");
        } else {
            if (mSwitchDefault.isChecked()) {
                setDefault = 1;
            } else {
                setDefault = 0;
            }
            startProgressDialog();
            editAddress();
        }

    }

    private void editAddress() {
        VolleyRequest.editAddress(mCityId, mAreaId, mEtDetailedAddress.getText().toString(),
                mEtName.getText().toString(), mEtPhone.getText().toString(),
                setDefault, mAddressItem.getID(),
                this.getClass().getSimpleName(), new VolleyRequestListener() {
                    @Override
                    public void success(boolean isSuccess, String response, String error) {
                        if (isSuccess) {
                            BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                            if (0 == entity.getErrorNo()) {
                                showShortToast(entity.getMessage());
                                scrollToFinishActivity();
                            } else if (-99 == entity.getErrorNo()) {
                                spUtil.put(SPUtil.TOKEN, "");
                                editAddress();
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
