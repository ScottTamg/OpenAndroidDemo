package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.entity.LoginEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.PhoneUtils;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Txw on 2016/4/8.
 */
public class RegisterActivity extends SwipeBackActivity implements View.OnClickListener {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.btn_verification_code)
    Button btnVerificationCode;
    @Bind(R.id.btn_register)
    Button btnRegister;

    private String phoneNum;
    private String smsnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mActivity = this;
        setTitleName("注册");
        setBack();
    }

    @OnClick({R.id.btn_verification_code, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification_code:
                phoneNum = etUsername.getText().toString();//手机号码
                if ("".equals(phoneNum)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (PhoneUtils.isMobileNO(phoneNum)) {
                        //获取短信验证码
                        startProgressDialog();
                        getSMSCode();
                    } else {
                        Toast.makeText(this, "请检查手机号码是否正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_register:
                //校验手机号和短信验证码
                phoneNum = etUsername.getText().toString();
                smsnum = etVerificationCode.getText().toString();
                if ("".equals(phoneNum)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(smsnum)) {
                    Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (PhoneUtils.isMobileNO(phoneNum)) {
                        //请求注册接口
                        startProgressDialog();
                        regUser();
                    } else {
                        Toast.makeText(this, "请检查手机号码是否正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void regUser() {
        VolleyRequest.login(phoneNum, smsnum, "Login", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    LoginEntity entity = loadDataUtil.getJsonData(response, LoginEntity.class);
                    if (0 == entity.getErrorNo()) {
                        showShortToast("登录成功");
                        try {
                            spUtil.put(SPUtil.ATOKEN,
                                    URLEncoder.encode(entity.getData().getAtoken(), "utf-8"));
                            spUtil.put(SPUtil.USER_MOBILE_PHONE, phoneNum);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        scrollToFinishActivity();
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        regUser();
                    } else {
                        showShortToast("失败：" + error);
                    }
                } else {
                    showShortToast(error);
                }
                stopProgressDialog();
            }
        });
    }

    private void getSMSCode() {
        /*VolleyRequest.getToken("token", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {}
                stopProgressDialog();
            }
        });*/

        VolleyRequest.sendVerifyCodeSms(phoneNum, "SMSCode", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    BaseEntity entity = loadDataUtil.getJsonData(response, BaseEntity.class);
                    if (0 == entity.getErrorNo()) {
                        showShortToast("短信验证码已发送");
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        getSMSCode();
                    } else {
                        showShortToast("发送失败：" + error);
                    }
                } else {
                    showShortToast(error);
                }
                stopProgressDialog();
            }
        });
    }
}
