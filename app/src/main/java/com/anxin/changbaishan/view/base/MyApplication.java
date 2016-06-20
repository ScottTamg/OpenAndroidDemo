package com.anxin.changbaishan.view.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.anxin.changbaishan.utils.SPUtil;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Txw on 2016/4/6.
 */
public class MyApplication extends Application {

    private static final String TAG = "VolleyPatterns";
    private static final int TIMEOUTMS = 20 * 1000;
    private static final int MAXNUMRETRIES = 1;
    private static final float BACKOFFMULTIPLIER = 1.0f;
    private volatile static MyApplication sInstance;

    private RequestQueue mQueues;
    private SPUtil mSpUtil;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mSpUtil = new SPUtil(getApplicationContext());
//        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication)context.getApplicationContext();
        return application.mRefWatcher;
    }

    /**
     * 获取MyApplication实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        if (sInstance == null) {
            synchronized (MyApplication.class) {
                if (sInstance == null) {
                    sInstance = new MyApplication();
                }
            }
        }
        return sInstance;
    }

    public SPUtil getSpUtil() {
        if (mSpUtil == null) {
            mSpUtil = new SPUtil(getApplicationContext());
        }
        return mSpUtil;
    }

    /**
     * 获取当前实例的RequestQueues
     *
     * @return
     */
    public RequestQueue getQueues() {
        if (mQueues == null) {
            mQueues = Volley.newRequestQueue(getApplicationContext());
        }
        return mQueues;
    }

    /**
     * 将request加入队列中
     *
     * @param request
     * @param tag
     * @param <T>
     */
    public <T> void addToQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", request.getUrl());
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUTMS, MAXNUMRETRIES, BACKOFFMULTIPLIER));
        getQueues().add(request);
//        getQueues().start();
    }

    public <T> void addToQueue(Request<T> request) {
        this.addToQueue(request, TAG);
    }

    /**
     * 取消队列中所有该Tag的request
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mQueues != null) {
            mQueues.cancelAll(tag);
        }
    }

    /**
     * 取消队列中所有该Tag的request
     */
    public void cancelPendingRequests() {
        this.cancelPendingRequests(TAG);
    }
}
