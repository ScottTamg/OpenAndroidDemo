package com.anxin.changbaishan.utils;

/**
 * Created by Txw on 2016/4/8.
 */
public class PhoneUtils {
    /**
     * 验证手机格式（更准确一些）
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "^(1[0-9][0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
//		String telRegex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";// 更具体严格
        if (mobiles.equals(""))
            return false;
        else
            return mobiles.matches(telRegex);
    }
}
