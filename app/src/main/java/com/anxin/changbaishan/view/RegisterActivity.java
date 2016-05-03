package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.utils.PhoneUtils;
import com.anxin.changbaishan.utils.RSAUtil;
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
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @Bind(R.id.btn_register)
    Button btnRegister;

    private String phoneNum;
    private String pwd;
    private String encryptPassword;
    private String smsnum;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mActivity = this;
        setTitleName("注册");
        setBack();
    }

    @OnClick({R.id.btn_verification_code, R.id.tv_user_agreement, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification_code:
                phoneNum = etUsername.getText().toString();//手机号码
                pwd = etPassword.getText().toString();//密码
                if ("".equals(phoneNum)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                /*else if ("".equals(pwd)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 6) {
                    Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() > 16) {
                    Toast.makeText(this, "密码长度不能大于16位", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    if (PhoneUtils.isMobileNO(phoneNum)) {
                        //获取短信验证码
                        startProgressDialog();
                        getMyToken();
                    } else {
                        Toast.makeText(this, "请检查手机号码是否正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_user_agreement:
                showShortToast("用户协议");
                break;
            case R.id.btn_register:
                //校验手机号和短信验证码
                phoneNum = etUsername.getText().toString();
                pwd = etPassword.getText().toString();
                smsnum = etVerificationCode.getText().toString();
                if ("".equals(phoneNum)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(pwd)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 6) {
                    Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() > 16) {
                    Toast.makeText(this, "密码长度不能大于16位", Toast.LENGTH_SHORT).show();
                } else if ("".equals(smsnum)) {
                    Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (PhoneUtils.isMobileNO(phoneNum)) {
                        //请求注册接口
                        startProgressDialog();

                        //密码加密
                        byte[] encryptByte = RSAUtil.encryptData(pwd.getBytes());
                        try {
                            encryptPassword = URLEncoder.encode(Base64.encodeToString(encryptByte, Base64.DEFAULT), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        regUser();
                    } else {
                        Toast.makeText(this, "请检查手机号码是否正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void regUser() {
    }

    private void getMyToken() {

    }
}
