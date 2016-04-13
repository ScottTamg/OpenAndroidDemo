package com.anxin.changbaishan.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
}
