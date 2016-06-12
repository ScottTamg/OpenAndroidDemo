package com.anxin.changbaishan.utils;

import com.anxin.changbaishan.entity.ProductEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Txw on 2016/4/7.
 */
public class LoadDataUtil {
    private volatile static LoadDataUtil sInstance;

    public static LoadDataUtil getInstance() {
        if (sInstance == null) {
            synchronized (LoadDataUtil.class) {
                if (sInstance == null) {
                    sInstance = new LoadDataUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取Json中的指定value
     * @param json json字符串
     * @param key 指定key
     * @return 指定value
     */
    public String getJsonValue(String json, String key) {
        String value = null;
        try {
            JSONObject jo = new JSONObject(json);
            value = jo.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * JSON解析
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getJsonData(String json, Class<T> tClass) {
        Gson gson = new Gson();
        T fromClass = gson.fromJson(json, tClass);
        return fromClass;
    }

    public ArrayList<ProductEntity.DataBean.ListBean> getJsonListData(String json, Class<ProductEntity.DataBean.ListBean> tClass) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProductEntity.DataBean.ListBean>>(){}.getType();
        ArrayList<ProductEntity.DataBean.ListBean> list = gson.fromJson(json, type);
        return list;
    }
}
