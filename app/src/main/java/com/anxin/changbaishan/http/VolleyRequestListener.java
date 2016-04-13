package com.anxin.changbaishan.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by txw on 2015/9/21.
 */
public abstract class VolleyRequestListener {

    private static Response.Listener mListener;
    private static Response.ErrorListener mErrorListener;

    public abstract void success(boolean isSuccess, String request, String error);

    public Response.Listener<String> onSuccess() {
        mListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //请求成功，返回data
                success(true, response, null);
            }
        };
        return mListener;
    }

    public Response.ErrorListener onError() {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //请求失败
                success(false, null, error.getMessage());
            }
        };
        return mErrorListener;
    }
}
