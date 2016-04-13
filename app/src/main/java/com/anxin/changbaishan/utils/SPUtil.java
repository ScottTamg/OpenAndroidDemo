package com.anxin.changbaishan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Txw on 2016/4/6.
 */
public class SPUtil {

    /* 保存在手机里面的文件名 */
    public static final String FILE_NAME = "share_data";
    public static final String TOKEN = "token";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public SPUtil(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, context.MODE_APPEND);
        editor = sp.edit();
    }

    /**
     * 保存数据的方法，我们根据保存的数据的具体类型,调用相对应的方法保存值
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String)object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer)object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean)object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float)object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long)object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferenceCampat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof  String) {
            return sp.getString(key, (String)defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer)defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean)defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float)defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }

    /**
     * 移除某个Key值已经对应的值
     * @param key
     */
    public static void remove(String key) {
        editor.remove(key);
        SharedPreferenceCampat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        editor.clear();
        SharedPreferenceCampat.apply(editor);
    }

    /**
     * 查询某个Key是否已经存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferenceCampat.apply方法的一个兼容类
     */
    private static class SharedPreferenceCampat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}
