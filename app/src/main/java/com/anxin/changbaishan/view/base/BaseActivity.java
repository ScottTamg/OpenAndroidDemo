package com.anxin.changbaishan.view.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.anxin.changbaishan.R;
import com.anxin.changbaishan.utils.LoadDataUtil;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.MyApplication;
import com.anxin.changbaishan.widget.CustomProgressDialog;

/**
 * Created by Txw on 2016/3/31.
 */
public class BaseActivity extends AppCompatActivity {

    /*
    *  当前Activity状态
   */
    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    protected Activity mActivity;
    protected SPUtil spUtil;
    protected LoadDataUtil loadDataUtil;

    private CustomProgressDialog progressDialog;
    //Activity状态
    private ActivityState activityState = ActivityState.DESTROY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = this;
        KJActivityStack.create().addActivity(this);
        super.onCreate(savedInstanceState);
        spUtil = MyApplication.getInstance().getSpUtil();
        loadDataUtil = LoadDataUtil.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ActivityState.RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ActivityState.STOP;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        activityState = ActivityState.DESTROY;
        super.onDestroy();
        KJActivityStack.create().finishActivity(this);
    }

    protected void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
        }

        progressDialog.show();
    }

    protected void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void startAnimActivity(Class<?> mClass) {
        startMyActivity(mClass);
        overridePendingTransition(R.anim.main_in, R.anim.main_out);
    }

    protected void startAnimActivity(Class<?> mClass, Bundle mBundle) {
        startMyActivity(mClass, mBundle);
        overridePendingTransition(R.anim.main_in, R.anim.main_out);
    }

    private void startMyActivity(Class<?> mClass, Bundle mBundle) {
        Intent intent = new Intent(this, mClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    private void startMyActivity(Class<?> mClass) {
        startMyActivity(mClass, null);
    }

    protected void showLongToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
//        ToastView toastView = new ToastView(this, Gravity.BOTTOM, false, pMsg, null);
//        toastView.showLong();
    }

    public void showShortToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
//        ToastView toastView = new ToastView(this, Gravity.BOTTOM, false, pMsg, null);
//        toastView.show();
    }
}
