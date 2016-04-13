package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.RSAUtil;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.base.SwipeBackActivity;
import com.anxin.changbaishan.widget.CustomAlertDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_denglu)
    Button btnDenglu;
    @Bind(R.id.btn_zhuce)
    TextView btnZhuce;
    @Bind(R.id.tv_forgetpwd)
    TextView tvForgetpwd;

    private String codeUserName;
    private String encryptPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;
        setTitleName("登录");
        setBack();
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_denglu, R.id.btn_zhuce, R.id.tv_forgetpwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_denglu:
                String username = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();
                byte[] encryptByte = RSAUtil.encryptData(pwd.getBytes());
                try {
                    codeUserName = URLEncoder.encode(username, "utf-8");
                    encryptPassword = URLEncoder.encode(Base64.encodeToString(encryptByte, Base64.DEFAULT), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //判断是否存在空项
                if ("".equals(username)) {
                    Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(pwd)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    //访问接口
                    startProgressDialog();
                    login();
                }
                break;
            case R.id.btn_zhuce:
                startAnimActivity(RegisterActivity.class);
                break;
            case R.id.tv_forgetpwd:
                startAnimActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    private void login() {
        VolleyRequest.login((String) spUtil.get(SPUtil.TOKEN, ""), codeUserName, encryptPassword, this.getClass().getName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String request, String error) {
                CustomAlertDialog dialog;
                stopProgressDialog();
                /*if (isSuccess) {
                    DengLuEntity entity = loadDataUtils.getJsonData(request, DengLuEntity.class);

                    if (0 == (entity.getErrorNo()) && entity != null) {
                        //登录成功
                        if (entity.getData() != null) {
                            try {
                                spUtil.saveToSp(SPUtil.ATOKEN, URLEncoder.encode(entity.getData().getAtoken(), "utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            spUtil.saveToSp(SPUtil.ISDENGLU, true);
                            finish();
                            //设置手势密码
                            // TODO: 2015/11/9  txw 存在登录成功后页面跳转混乱
                            if (spUtil.getFromSp(SPUtil.ISGESTURE, false)) {
                                startAnimActivity(LockPatternCreatActivity.class);
                            } else {
                                startAnimActivity(MainTabActivity.class);
                            }

                        } else {
                            dialog = new MyAlertDialog(GengDuo_DengLu_Activity.this, "确定", entity.getMessage());
                        }


                    } else if (-99 == (entity.getErrorNo())) {
                        spUtil.saveToSp(SPUtil.TOKEN, "");
                        login();
                    } else {
                        if ("此用户不存在".equals(entity.getMessage())) {
                            MyAlertDialog.InterfaceConfirm confirm = new MyAlertDialog.InterfaceConfirm() {
                                @Override
                                public void confirm() {
                                    startAnimActivity(GengDuo_ZhuCe_Activity.class);
                                }
                            };
                            dialog = new MyAlertDialog(GengDuo_DengLu_Activity.this, "注册", entity.getMessage(), confirm);
                        } else {
                            dialog = new MyAlertDialog(GengDuo_DengLu_Activity.this, "确定", entity.getMessage());
                        }
                    }
                } else {
                    dialog = new MyAlertDialog(GengDuo_DengLu_Activity.this, "确定", error);
                }*/
            }
        });
    }


}
