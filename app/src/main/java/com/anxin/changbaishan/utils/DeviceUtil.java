package com.anxin.changbaishan.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Txw on 2016/5/16.
 */
public class DeviceUtil {
    /**
     * 获取当前设备宽高，单位px
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }
    @SuppressWarnings("deprecation")
    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

}
