package com.anxin.changbaishan.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends SwipeBackActivity implements View.OnClickListener {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.btn_verification_code)
    Button btnVerificationCode;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @Bind(R.id.btn_done)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mActivity = this;
        setBack();
        setTitleName("忘记密码");
    }

    @OnClick({R.id.btn_verification_code, R.id.btn_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification_code:
                break;
            case R.id.btn_done:
                break;
        }
    }
}
