
package com.anxin.changbaishan.view.base;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.view.base.BaseActivity;
import com.anxin.changbaishan.widget.swipebacklayout.SwipeBackActivityBase;
import com.anxin.changbaishan.widget.swipebacklayout.SwipeBackActivityHelper;
import com.anxin.changbaishan.widget.swipebacklayout.SwipeBackLayout;

public class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivtyCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.back_in, R.anim.back_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void setTitleName(String title) {
        TextView textView = (TextView) findViewById(R.id.title_textview);
        textView.setText(title);
    }

    protected void setNoBack() {
        ImageView back = (ImageView) findViewById(R.id.title_goback);
        back.setVisibility(View.GONE);
    }

    protected void setBack() {
        ImageView back = (ImageView) findViewById(R.id.title_goback);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.back_in, R.anim.back_out);
            }
        });
    }
}
