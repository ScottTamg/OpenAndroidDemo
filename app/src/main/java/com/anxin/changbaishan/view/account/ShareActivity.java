package com.anxin.changbaishan.view.account;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.entity.InviteInfoEntity;
import com.anxin.changbaishan.http.VolleyRequest;
import com.anxin.changbaishan.http.VolleyRequestListener;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.RegisterActivity;
import com.anxin.changbaishan.view.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends SwipeBackActivity {

    @Bind(R.id.tv_reg)
    TextView mTvReg;
    @Bind(R.id.tv_deliver)
    TextView mTvDeliver;
    @Bind(R.id.tv_total)
    TextView mTvTotal;
    @Bind(R.id.btn_share)
    Button mBtnShare;
    @Bind(R.id.btn_right)
    Button mBtnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setBack();
        setTitleName("分享送积分");
        setRight();
        mBtnRight.setText("");
        mBtnRight.setBackgroundResource(R.drawable.share_icon);

        gtInviteInfo();
    }

    @OnClick({R.id.btn_right, R.id.btn_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right:
                showPopuuWindow();
                break;
            case R.id.btn_share:
                showPopuuWindow();
                break;
        }
    }

    private void showPopuuWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.layout_popup_window, null));
        popupWindow.setBackgroundDrawable(new ColorDrawable(0Xaa000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(findViewById(R.id.btn_share), Gravity.BOTTOM, 0, 0);
    }


    private void gtInviteInfo() {
        VolleyRequest.getInviteInfo(this.getClass().getSimpleName(), new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    InviteInfoEntity entity = loadDataUtil.getJsonData(response, InviteInfoEntity.class);
                    if (0 == entity.getErrorNo()) {
                        showShortToast(entity.getData().getUserLink());
                    } else if (-99 == entity.getErrorNo()) {
                        spUtil.put(SPUtil.TOKEN, "");
                        gtInviteInfo();
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
