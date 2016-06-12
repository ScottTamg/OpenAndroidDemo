package com.anxin.changbaishan.http;

import android.support.v4.util.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anxin.changbaishan.entity.BaseEntity;
import com.anxin.changbaishan.utils.LoadDataUtil;
import com.anxin.changbaishan.utils.Md5Util;
import com.anxin.changbaishan.utils.SPUtil;
import com.anxin.changbaishan.view.base.MyApplication;

import java.util.Map;

/**
 * Created by txw on 2015/9/21.
 */
public class VolleyRequest {

    public static final String BASIC_WWW_URL = "http://api.changbaishan.com";
    public static final String TEXT_BASIC_WWW_URL = "http://api99.changbaishan.com";
    public static final String API_URL = TEXT_BASIC_WWW_URL + "/Api.aspx";
    public static final int PL = 2;

    private static final SPUtil spUtil = MyApplication.getInstance().getSpUtil();
    private static final LoadDataUtil LOAD_DATA_UTIL = LoadDataUtil.getInstance();

    /**
     * http://api.changbaishan.com/Api.aspx?action=User&pl={platform}&token={token}&cmd=SendVerifyCodeSms&mobile={mobile}
     *
     * @param moblie
     * @param tag
     * @param listener
     */
    public static void sendVerifyCodeSms(String moblie, String tag, VolleyRequestListener listener) {
        String url = API_URL;
        Map<String, String> params = new ArrayMap<>();
        params.put("action", "User");
        params.put("pl", String.valueOf(PL));
        params.put("token", (String) spUtil.get(SPUtil.TOKEN, ""));
        params.put("cmd", "SendVerifyCodeSms");
        params.put("mobile", moblie);

        if (params.get("token").isEmpty()) {
            volleyPostNoToken(url, params, tag, listener);
        } else {
            volleyPost(url, params, tag, listener);
        }

    }

    /**
     * http://api.changbaishan.com/Api.aspx?action=User&pl={platform}&token={token}&cmd=Login&mobile={mobile}&checkCode={checkCode}&userInviteKey={userInviteKey}&partnerInviteKey={partnerInviteKey}
     *
     * @param moblie
     * @param checkCode
     * @param tag
     * @param listener
     */
    public static void login(String moblie, String checkCode, String tag, VolleyRequestListener listener) {
        String url = API_URL;
        Map<String, String> params = new ArrayMap<>();
        params.put("action", "User");
        params.put("pl", String.valueOf(PL));
        params.put("token", (String) spUtil.get(SPUtil.TOKEN, ""));
        params.put("cmd", "Login");
        params.put("mobile", moblie);
        params.put("checkCode", checkCode);
        params.put("userInviteKey", "");
        params.put("userInviteKey", "");

        if (params.get("token").isEmpty()) {
            volleyPostNoToken(url, params, tag, listener);
        } else {
            volleyPost(url, params, tag, listener);
        }
    }

    /**
     * 读取城市
     * http://api2.changbaishan.com/api.aspx?action=CommonApi&cmd=GetCitys&pl=2&token=111{pl}&atoken={atoken}&token={token}&province={province}
     *
     * @param tag
     * @param listener
     */
    public static void getCitys(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=CommonApi&cmd=GetCitys");
        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取地区
     *
     * @param pid
     * @param tag
     * @param listener
     */
    public static void getAreas(int pid, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=CommonApi&cmd=GetCitys");
        url.append("&pl=");
        url.append(PL);
        url.append("&pid=");
        url.append(pid);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 投标
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=AddLoan&token=111&pl=1&borrowId=100000&amount=1000&couponIds=11
     *
     * @param token
     * @param borrowid
     * @param amount
     * @param couponIds
     * @param tag
     * @param listener
     */
    public static void getAddLoan(String token, int borrowid, String amount,
                                  int couponIds, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=DiYa&cmd=AddLoan");
        url.append("&pl=");
        url.append(PL);

        url.append("&couponIds=");
        url.append(couponIds);

        StringBuffer sign = new StringBuffer();
        sign.append("&borrowid=");
        sign.append(borrowid);
        sign.append("&amount=");
        sign.append(amount);

        url.append(sign.toString());
        url.append("&sign=");

        sign.append("&key=301-303-311-312");
        String md5 = Md5Util.encode(sign.toString());
        url.append(md5);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到产品列表
     * http://api2.changbaishan.com/api.aspx?action=Products&cmd=GetProductList&pl=2&token=111
     *
     * @param tag
     * @param listener
     */
    public static void getProductList(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Products&cmd=GetProductList");
        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到体验套餐
     * http://api2.changbaishan.com/api.aspx?action=Products&cmd=GetTrialProductList&pl=2&token=111
     *
     * @param tag
     * @param listener
     */
    public static void getTrialProductList(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Products&cmd=GetTrialProductList");
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 生成初始的订单
     * http://api2.changbaishan.com/api.aspx?action=Orders&cmd=CreatMyOrders&pl=2&token=111&list=[{"productId":1,"count":1},{"productId":2,"count":2}]
     *
     * @param list
     * @param tag
     * @param listener
     */
    public static void creatMyOrders(String list, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Orders&cmd=CreatMyOrders");
        url.append("&list=");
        url.append(list);
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 确认订单
     * http://api2.changbaishan.com/api.aspx?action=Orders&cmd=ConfirmOrders&pl=2&token=111&orderId=1
     *
     * @param orderId
     * @param invoiceType
     * @param invoiceAddress
     * @param userBonusPoints
     * @param tag
     * @param listener
     */
    public static void confirmOrders(int orderId, int invoiceType, String invoiceAddress,
                                     int userBonusPoints, String remark,
                                     String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Orders&cmd=ConfirmOrders");
        url.append("&orderId=");
        url.append(orderId);
//        url.append("&invoiceType=");
//        url.append(invoiceType);
//        url.append("&invoiceAddress=");
//        url.append(invoiceAddress);
        url.append("&userBonusPoints=");
        url.append(userBonusPoints);
        url.append("&remark=");
        url.append(remark);
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 订单详情
     * http://api2.changbaishan.com/api.aspx?action=Orders&cmd=GetOrderModel&pl=2&token=111&orderId=100037
     *
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void getOrderModel(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Orders&cmd=GetOrderModel");
        url.append("&orderId=");
        url.append(orderId);

        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取我的订单列表
     * http://api2.changbaishan.com/api.aspx?action=Orders&cmd=GetMyOrderList&pl=2&token=111&orderState=0
     * @param orderState
     * @param tag
     * @param listener
     */
    public static void getMyOrderList(int orderState, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Orders&cmd=GetMyOrderList");
        url.append("&orderState=");
        url.append(orderState);
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 取消订单
     * http://api2.changbaishan.com/api.aspx?action=Orders&cmd=CancelOrder&pl=2&token=111&orderId=1
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void cancelOrder(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Orders&cmd=CancelOrder");
        url.append("&orderId=");
        url.append(orderId);
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 添加用户的收获地址
     *
     * @param cityId
     * @param districtId
     * @param address
     * @param userName
     * @param mobile
     * @param setDefault
     * @param tag
     * @param listener
     */
    public static void addAddress(int cityId, int districtId, String address,
                                  String userName, String mobile, int setDefault,
                                    String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserAddress&cmd=AddAddress");
        url.append("&cityId=");
        url.append(cityId);
        url.append("&districtId=");
        url.append(districtId);
        url.append("&address=");
        url.append(address);
        url.append("&userName=");
        url.append(userName);
        url.append("&mobile=");
        url.append(mobile);
        url.append("&setDefault=");
        url.append(setDefault);
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 编辑用户的收获地址
     *
     * @param cityId
     * @param districtId
     * @param address
     * @param userName
     * @param mobile
     * @param setDefault
     * @param tag
     * @param listener
     */
    public static void editAddress(int cityId, int districtId, String address,
                                   String userName, String mobile, int setDefault, int addressId,
                                     String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserAddress&cmd=EditAddress");
        url.append("&cityId=");
        url.append(cityId);
        url.append("&districtId=");
        url.append(districtId);
        url.append("&address=");
        url.append(address);
        url.append("&userName=");
        url.append(userName);
        url.append("&mobile=");
        url.append(mobile);
        url.append("&setDefault=");
        url.append(setDefault);
        url.append("&addressId=");
        url.append(addressId);

        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户的收获地址列表
     * http://api2.changbaishan.com/api.aspx?action=UserAddress&cmd=GetAddressList&token=111&pl=1
     *
     * @param tag
     * @param listener
     */
    public static void getAddressList(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserAddress&cmd=GetAddressList");
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 删除用户的某个收获地址
     *
     * @param addressId
     * @param tag
     * @param listener
     */
    public static void deleteAddress(int addressId, String tag,
                                     VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserAddress&cmd=DeleteAddress");
        url.append("&pl=");
        url.append(PL);
        url.append("&addressId=");
        url.append(addressId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 设置默认收获地址
     * http://api2.changbaishan.com/api.aspx?action=UserAddress&cmd=SetDefaultAddress
     *
     * @param addressId
     * @param tag
     * @param listener
     */
    public static void setDefaultAddress(int addressId, String tag,
                                         VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserAddress&cmd=SetDefaultAddress");
        url.append("&pl=");
        url.append(PL);
        url.append("&addressId=");
        url.append(addressId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 生成初始的预约送水订单
     * @param list
     * @param deliveryTime
     * @param userAddressId
     * @param tag
     * @param listener
     */
    public static void creatDeliveryOrders(String remark, String list, int deliveryTime, int userAddressId,
                                           String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=CreatDeliveryOrders");
        url.append("&pl=");
        url.append(PL);
        url.append("&remark=");
        url.append(remark);
        url.append("&list=");
        url.append(list);
        url.append("&deliveryTime=");
        url.append(deliveryTime);
        url.append("&userAddressId=");
        url.append(userAddressId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到配送的列表
     * @param tag
     * @param listener
     */
    public static void getMyDeliveryList(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=GetMyDeliveryList");
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到配送的详情
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void getMyDeliveryModel(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=GetMyDeliveryModel");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到配送单的评论及回复
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void getDeliveryComment(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=GetDeliveryComment");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 添加一条评论
     * @param parentId
     * @param content
     * @param stars
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void addComment(int parentId, String content, int stars, int orderId, String tag,
                                  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=AddComment");
        url.append("&pl=");
        url.append(PL);
        url.append("&parentId=");
        url.append(parentId);
        url.append("&content=");
        url.append(content);
        url.append("&stars=");
        url.append(stars);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 删除一条评论
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void deleteComment(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=DeleteComment");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 取消订单
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void cancelDeliveryOrder(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=CancelOrder");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取评论的展示信息
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void getDeliveryCommentInfo(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=Delivery&cmd=GetDeliveryCommentInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户的可用积分和列表
     * @param typeId
     * @param tag
     * @param listener
     */
    public static void getUserBonusPoints(int typeId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetUserBonusPoints");
        url.append("&pl=");
        url.append(PL);
        url.append("&typeId=");
        url.append(typeId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     *
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void paySuccess(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=PaySuccess");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到我的存量信息
     * @param getAddress
     * @param tag
     * @param listener
     */
    public static void getMyProductsList(int getAddress, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetMyProductsList");
        url.append("&pl=");
        url.append(PL);
        url.append("&getaddress=");
        url.append(getAddress);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户的信息
     * @param tag
     * @param listener
     */
    public static void getMyInfo(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetMyInfo");
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户的分享链接地址
     * @param tag
     * @param listener
     */
    public static void getInviteInfo( String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetInviteInfo");
        url.append("&pl=");
        url.append(PL);

        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     *
     * @param list
     * @param friendName
     * @param friendMobile
     * @param tag
     * @param listener
     */
    public static void creatFriendOrder(String list, String friendName, String friendMobile,
                                        String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=CreatFriendOrder");
        url.append("&pl=");
        url.append(PL);
        url.append("&list=");
        url.append(list);
        url.append("&friendName=");
        url.append(friendName);
        url.append("&friendMobile=");
        url.append(friendMobile);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 取消，撤回赠送（只能是未领取的订单）
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void cancelFriendOrder(int orderId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=CancelFriendOrder");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 接受好友的赠送（只能是未领取的订单）
     * @param orderId
     * @param giftKey
     * @param tag
     * @param listener
     */
    public static void acceptFriendOrder(String orderId, String giftKey, String tag,
                                         VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=AcceptFriendOrder");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);
        url.append("&giftKey=");
        url.append(giftKey);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到我送出的订单列表
     * @param tag
     * @param listener
     */
    public static void getMyFriendOrderList(String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetMyFriendOrderList");
        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到我送出的订单的详情
     * @param orderId
     * @param tag
     * @param listener
     */
    public static void getMyFriendDetail(int orderId, String tag,
                                         VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetMyFriendDetail");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到送给我的订单的列表
     * @param tag
     * @param listener
     */
    public static void getGiveToMeFriendOrderList(String tag,
                                                  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetGiveToMeFriendOrderList");
        url.append("&pl=");
        url.append(PL);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到送给我的订单的详情
     * @param orderId
     * @param giftKey
     * @param tag
     * @param listener
     */
    public static void GetGiveToMeFriendOrderDetail(String orderId, String giftKey, String tag,
                                                    VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_URL);
        url.append("?action=UserCenter&cmd=GetGiveToMeFriendOrderDetail");
        url.append("&pl=");
        url.append(PL);
        url.append("&orderId=");
        url.append(orderId);
        url.append("&giftKey=");
        url.append(giftKey);
        url.append("&token=");
        String token = (String) spUtil.get(SPUtil.TOKEN, "");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取token
     *
     * @param tag
     * @param listener
     */
    public static void getToken(String tag, VolleyRequestListener listener) {
        String tokenUrl = API_URL + "?action=Token&cmd=getToken&key=28101be64166ea4d150ba6a5cce6f8c8&pl=" + PL;
        volleyGet(tokenUrl.toString(), tag, listener);
    }

    public static void volleyGet(String url, String tag, VolleyRequestListener listener) {
        StringBuilder str = new StringBuilder(url);
        str.append("&aToken=");
        str.append((String) spUtil.get(SPUtil.ATOKEN, ""));

        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(
                Request.Method.GET, str.toString(), listener.onSuccess(), listener.onError());
        MyApplication.getInstance().addToQueue(request, tag);
    }

    public static void volleyGetNoToken(final String url, final String tag, final VolleyRequestListener listener) {
        String tokenUrl = API_URL + "?action=Token&cmd=getToken&key=28101be64166ea4d150ba6a5cce6f8c8&pl=" + PL;
        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(Request.Method.GET, tokenUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                BaseEntity entity = LOAD_DATA_UTIL.getJsonData(response, BaseEntity.class);
                if (0 == entity.getErrorNo()) {
                    spUtil.put(SPUtil.TOKEN, entity.getMessage());
                } else {
                    spUtil.put(SPUtil.TOKEN, "");
                }
                volleyGet(url + spUtil.get(SPUtil.TOKEN, ""), tag, listener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.success(false, null, error.getMessage());
            }
        });
        MyApplication.getInstance().addToQueue(request, tag);

    }

    public static void volleyPost(String url, final Map<String, String> params, final String tag,
                                  final VolleyRequestListener listener) {
        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(
                Request.Method.POST, url, listener.onSuccess(), listener.onError()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        MyApplication.getInstance().addToQueue(request, tag);
    }

    public static void volleyPostNoToken(final String url, final Map<String, String> params,
                                         final String tag, final VolleyRequestListener listener) {
        String tokenUrl = API_URL + "?action=Token&cmd=getToken&key=28101be64166ea4d150ba6a5cce6f8c8&pl=" + PL;
        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(Request.Method.GET, tokenUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                BaseEntity entity = LOAD_DATA_UTIL.getJsonData(response, BaseEntity.class);
                if (0 == entity.getErrorNo()) {
                    spUtil.put(SPUtil.TOKEN, entity.getMessage());
                    params.put("token", entity.getMessage());
                } else {
                    spUtil.put(SPUtil.TOKEN, "");
                }
                volleyPost(url, params, tag, listener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.success(false, null, error.getMessage());
            }
        });
        MyApplication.getInstance().addToQueue(request, tag);
    }
}
