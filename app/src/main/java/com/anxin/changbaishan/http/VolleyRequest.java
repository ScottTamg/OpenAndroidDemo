package com.anxin.changbaishan.http;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anxin.changbaishan.utils.LoadDataUtil;
import com.anxin.changbaishan.utils.Md5Util;
import com.anxin.changbaishan.view.MyApplication;
import com.anxin.changbaishan.utils.SPUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by txw on 2015/9/21.
 */
public class VolleyRequest {

    public static final String BASIC_WWW_URL = "http://pub99.bibao.com";
    public static final String API_UPL = BASIC_WWW_URL + "/Api.aspx";
    public static final String TOUZI_LIST_URL = BASIC_WWW_URL + "/ApiBibao.aspx";
    public static final String MOBILEUCENTER_URL = BASIC_WWW_URL + "/pub/mobileucenter.aspx";// 更改用户头像
    public static final String UPDATEMESURL = BASIC_WWW_URL + "/uploadfiles/update.txt";// 获取更新公告
    public static final String APK_DOWNLOAD_URL = BASIC_WWW_URL + "/down.html?cmd=anxinapk";// apk下载
    public static final int PL = 2;

    private static final SPUtil spUtil = MyApplication.getInstance().getSpUtil();
    private static final LoadDataUtil LOAD_DATA_UTIL = LoadDataUtil.getInstance();

    /**
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetUserInfo&pl={pl}&atoken={atoken}&token={token}
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void getUserInfo(String token, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetUserInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     *  http://pub.bibao.com/Api.aspx?action=UserApi&pl={platform}&token={token}&cmd=Login&phoneNum={phoneNum}&pwd={pwd}
     * @param phoneNum
     * @param userPwd
     * @param tag
     * @param listener
     */
    public static void login(String token, String phoneNum, String userPwd, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserApi&cmd=Login");
        url.append("&pl=");
        url.append(PL);
        url.append("&phoneNum=");
        url.append(phoneNum);
        url.append("&pwd=");
        url.append(userPwd);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 修改登录密码
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=ChangePassword&pl={pl}&atoken={atoken}&token={token}&oldPwd={oldPassword}&newPwd={newPassword}
     * @param oldPwd
     * @param newPwd
     * @param tag
     * @param listener
     */
    public static void updateLoginPwd(String token, String oldPwd, String newPwd, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=ChangePassword");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&pwd=");
        url.append(oldPwd);
        url.append("&oldPwd=");
        url.append(oldPwd);
        url.append("&newPwd=");
        url.append(newPwd);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=ChangeDrawPassword&pl={pl}&atoken={atoken}&token={token}&oldPwd={oldPassword}&newPwd={newPassword}
     * 修改提现密码
     * @param oldPwd
     * @param newPwd
     * @param tag
     * @param listener
     */
    public static void updateDrawPwd(String token, String aToken, String oldPwd, String newPwd, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=ChangeDrawPassword");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&pwd=");
        url.append(oldPwd);
        url.append("&oldPwd=");
        url.append(oldPwd);
        url.append("&newPwd=");
        url.append(newPwd);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 通过手机验证码修改提现密码
     * @param token
     * @param newPwd
     * @param checkCode
     * @param tag
     * @param listener
     */
    public static void updateDrawPwdByMobile(String token, String aToken, String newPwd, String checkCode, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=UpdateDrawPwdByMobile");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&newPwd=");
        url.append(newPwd);
        url.append("&checkCode=");
        url.append(checkCode);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=CheckMobileCode&pl={pl}&atoken={atoken}&token={token}&checkCode={checkCode}
     * 验证手机验证码是否正确（用在找回交易密码的，下一步等地方。）
     * @param token
     * @param checkCode
     * @param tag
     * @param listener
     */
    public static void checkMobileCode(String token, String aToken, String checkCode, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=CheckMobileCode");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&checkCode=");
        url.append(checkCode);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 注册页，获取手机验证码
     * @param phoneNum
     * @param tag
     * @param listener
     */
    public static void sendRegCheckCode(String token, String phoneNum, String vCode, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(TOUZI_LIST_URL);
        url.append("?action=UserApi&cmd=SendRegCheckCode");
        url.append("&pl=");
        url.append(PL);
        url.append("&phoneNum=");
        url.append(phoneNum);
        url.append("&vCode=");
        url.append(vCode);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * http://pub2.anxin.com/ApiBibao.aspx?action=UserApi&cmd=RegUser&token=111&pl=1&phoneNum=13810957032&pwd=1111&checkCode=1111
     * @param token
     * @param phoneNum
     * @param pwd
     * @param checkCode
     * @param tag
     * @param listener
     */
    public static void regUser(String token, String phoneNum, String pwd, String checkCode, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserApi&cmd=RegUser");
        url.append("&pl=");
        url.append(PL);
        url.append("&phoneNum=");
        url.append(phoneNum);
        url.append("&pwd=");
        url.append(pwd);
        url.append("&checkCode=");
        url.append(checkCode);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 修改密码接口
     * http://pub.bibao.com/Api.aspx?action=UserApi&pl={platform}&token={token}&cmd=ResetPasswordByMobile&phoneNum={phoneNum}&pwd={pwd}&checkCode={checkCode}
     * @param token
     * @param phoneNum
     * @param pwd
     * @param checkCode
     * @param tag
     * @param listener
     */
    public static void resetPasswordByMobile(String token, String phoneNum, String pwd, String checkCode, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserApi&cmd=ResetPasswordByMobile");
        url.append("&pl=");
        url.append(PL);
        url.append("&phoneNum=");
        url.append(phoneNum);
        url.append("&pwd=");
        url.append(pwd);
        url.append("&checkCode=");
        url.append(checkCode);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 发送手机验证码
     * @param tag
     * @param listener
     */
    public static void sendDrawPwdMsgCheckCode(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=SendDrawPwdMsgCheckCode");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户资产
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetAccountSummary&pl=2&token=111
     * @param token
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void getAccountSummary(String token, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetAccountSummary");
        url.append("&pl=");
        url.append(PL);
        url.append("&aToken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }


    /**
     * 得到用户的收益记录（首页详情页面的），可分页，需登录
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetLoanProfitList&pl=2&token=111&pageSize=10&pageIndex=1
     * @param token
     * @param aToken
     * @param pageIndex
     * @param pageSize
     * @param tag
     * @param listener
     */
    public static void getLoanProfitList(String token, String aToken, int pageIndex, int pageSize, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetLoanProfitList");
        url.append("&pl=");
        url.append(PL);
        url.append("&aToken=");
        url.append(aToken);
        url.append("&pageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到用户的交易记录
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetLoanList&pl=2&token=111&pageSize=10&pageIndex=1
     * @param token
     * @param aToken
     * @param tag
     * @param pageIndex
     * @param pageSize
     * @param listener
     */
    public static void getLoanList(String token, String aToken, int pageIndex, int pageSize, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetLoanList");
        url.append("&pl=");
        url.append(PL);
        url.append("&aToken=");
        url.append(aToken);
        url.append("&pageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到某个投资的信息
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetLoanDetail&pl=2&token=111&loanId=3
     * @param token
     * @param aToken
     * @param loanId
     * @param tag
     * @param listener
     */
    public static void getLoanDetail(String token, String aToken, String loanId, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetLoanDetail");
        url.append("&pl=");
        url.append(PL);
        url.append("&aToken=");
        url.append(aToken);
        url.append("&loanId=");
        url.append(loanId);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 投资详情
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetDetails&token=111&pl=1&borrowId=100000
     * @param borrowId
     * @param tag
     * @param listener
     */
    public static void getDetails(String token, int borrowId, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetDetails");
        url.append("&pl=");
        url.append(PL);
        url.append("&borrowId=");
        url.append(borrowId);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 投标详情页的投标详情字段
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetDescription&token=111&pl=1&borrowId=100000
     * @param token
     * @param borrowId
     * @param tag
     * @param listener
     */
    public static void getDescription(String token, int borrowId, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetDescription");
        url.append("&pl=");
        url.append(PL);
        url.append("&borrowId=");
        url.append(borrowId);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取投标的收益等信息（需要登录）
     * url:http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetInvestMoneyInfo&token=111&pl=1&borrowId=100000&atoken=
     * @param token
     * @param aToken
     * @param borrowId
     * @param tag
     * @param listener
     */
    public static void getInvestMoneyInfo(String token, String aToken, int borrowId, String amount, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetInvestMoneyInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&aToken=");
        url.append(aToken);
        url.append("&borrowId=");
        url.append(borrowId);
        url.append("&amount=");
        url.append(amount);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到投资列表XX
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetList&token=111&pl=1&pageSize=10&pageIndex=1&version
     * @param token
     * @param version
     * @param pageIndex
     * @param pageSize
     * @param tag
     * @param listener
     */
    public static void getTouZiList(String token, String version, int pageIndex, int pageSize, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetList");
        url.append("&pl=");
        url.append(PL);
        url.append("&version=");
        url.append(version);
        url.append("&pageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 投资页轮播图片
     * http://pub99.bibao.com/Api.aspx?action=NoToken&cmd=GetPhotoList&pl=3
     * @param token
     * @param tag
     * @param listener
     */
    public static void getInvestSlides(String token, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=NoToken&cmd=GetPhotoList");
        url.append("&pl=");
        url.append(PL);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到精选借款标
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetListTop1&token=111&pl=1&period=3
     * @param token
     * @param period
     * @param tag
     * @param listener
     */
    public static void getListTop1(String token, int period, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetListTop1");
        url.append("&pl=");
        url.append(PL);
        url.append("&period=");
        url.append(period);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 身份证验证
     * @param token
     * @param realName
     * @param idcard
     * @param tag
     * @param listener
     */
    public static void getIdCardCheck(String token, String aToken, String realName, String idcard, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=IdCardCheck");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&realName=");
        url.append(realName);
        url.append("&idcard=");
        url.append(idcard);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取身份验证信息
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetUserAuthenticate&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param tag
     * @param listener
     */
    public static void getUserAuthenticate(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetUserAuthenticate");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取支持的银行列表
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetBankCardBankList&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param tag
     * @param listener
     */
    public static void getBankCardBankList(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetBankCardBankList");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取绑定银行卡信息
     *  http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetBankCardInfo&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param tag
     * @param listener
     */
    public static void getBankCardInfo(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetBankCardInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=UpdateBankCardInfo&pl={pl}&atoken={atoken}&token={token}&province={province}&city={city}&bankType={bankType}&bankName={bankName}&bankAccount={bankAccount}
     * 更新银行卡
     * @param token
     * @param province
     * @param city
     * @param bankType
     * @param bankName
     * @param bankAccount
     * @param tag
     * @param listener
     */
    public static void updateBankCardInfo(String token, String aToken, String province, String city, int bankType, String bankName, String bankAccount, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=UpdateBankCardInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&province=");
        url.append(province);
        url.append("&city=");
        url.append(city);
        url.append("&bankType=");
        url.append(bankType);
        url.append("&bankName=");
        url.append(bankName);
        url.append("&bankAccount=");
        url.append(bankAccount);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 读取省份
     * http://pub.bibao.com/Api.aspx?action=CommonApi&cmd=GetProvinces&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param tag
     * @param listener
     */
    public static void getProvinces(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=CommonApi&cmd=GetProvinces");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 读取城市
     * http://pub.bibao.com/Api.aspx?action=CommonApi&cmd=GetCitys&pl={pl}&atoken={atoken}&token={token}&province={province}
     * @param token
     * @param province
     * @param tag
     * @param listener
     */
    public static void getCitys(String token, String aToken, String province, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=CommonApi&cmd=GetCitys");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&province=");
        url.append(province);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取银行卡相关的省市信息
     * http://pub.bibao.com/Api.aspx?action=GetBankCardProvinceCityList&cmd=GetBankCardBankList&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void GetBankCardProvinceCityList(String token, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=GetBankCardProvinceCityList&cmd=GetBankCardBankList");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到提现的基本信息
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetDrawInfo&pl={pl}&atoken={atoken}&token={token}
     * @param token
     * @param tag
     * @param listener
     */
    public static void getDrawInfo(String token, String aToken, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetDrawInfo");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 计算提现手续费
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetDrawFee&pl={pl}&atoken={atoken}&token={token}&drawAmount={amount}
     * @param token
     * @param drawAccount
     * @param tag
     * @param listener
     */
    public static void getDrawFee(String token,String aToken, int drawAccount, String tag,  VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetDrawFee");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&drawAccount=");
        url.append(drawAccount);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 提现列表
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=GetDrawList&pl={pl}&atoken={atoken}&token={token}&time1={startTime}&time2={endTime}&state={state}&pageSize={pageSize}
     * @param token
     * @param pageIndex
     * @param pageSize
     * @param tag
     * @param listener
     */
    public static void getDrawList(String token, String aToken, int pageIndex, int pageSize, String startTime,
                                   String endTime, int state, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetDrawList");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&PageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&time1=");
        url.append(startTime);
        url.append("&time2=");
        url.append(endTime);
        url.append("&state=");
        url.append(state);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 投资收益列表
     * http://pub99.anxin.com/Apibibao.aspx?action=UserCenter&cmd=GetInterestByMonth&uid=100300&pwd=e10adc3949ba59abbe56e057f20f883e&token=111&pl=2
     * @param token
     * @param uid
     * @param pwd
     * @param tag
     * @param listener
     */
    public static void getInterestByMonth(String token, int uid, String pwd, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(TOUZI_LIST_URL);
        url.append("?action=UserCenter&cmd=GetInterestByMonth");
        url.append("&pl=");
        url.append(PL);
        url.append("&uid=");
        url.append(uid);
        url.append("&pwd=");
        url.append(pwd);
        url.append("&token=");
        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 申请提现
     * http://pub.bibao.com/Api.aspx?action=UserCenter&cmd=ApplyDrawMoney&pl={pl}&atoken={atoken}&token={token}&drawPwd={drawPassword}&drawAccount={drawAccount}&sign={sign}
     * @param token
     * @param drawPwd
     * @param drawAmount
     * @param tag
     * @param listener
     */
    public static void getApplyDrawMoney(String token, String aToken, String encryptPwd, String drawPwd, String drawAmount, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=ApplyDrawMoney");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&drawPwd=");
        url.append(encryptPwd);
        url.append("&drawAmount=");
        url.append(drawAmount);
        url.append("&");

        StringBuffer sign = new StringBuffer();
        sign.append("atoken=");
        try {
            sign.append(URLDecoder.decode(aToken, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        sign.append("&drawPwd=");
        sign.append(drawPwd);
        sign.append("&drawAmount=");
        sign.append(drawAmount);

//        url.append(sign.toString());
        url.append("&sign=");

        sign.append("&key=san_lou_301");
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
     * 计算标的总收益
     * http://pub99.anxin.com/ApiBiBao.aspx?action=Invest&cmd=CalculateProfit&token=111&pl=1&borrowId=504058&amount=100
     * @param token
     * @param borrowId
     * @param amount
     * @param tag
     * @param listener
     */
    public static void getCalculateProfit(String token, String borrowId, String amount, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(TOUZI_LIST_URL);
        url.append("?action=Invest&cmd=CalculateProfit");
        url.append("&pl=");
        url.append(PL);
        url.append("&borrowId=");
        url.append(borrowId);
        url.append("&amount=");
        url.append(amount);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 根据用户输入的投标金额，计算预期收益(需要登录)
     * http://pub2.bibao.com/Api.aspx?action=DiYa&cmd=GetProfitByAmount&token=111&pl=1&borrowId=100000&amount=1000&atoken=
     * @param token
     * @param aToken
     * @param borrowId
     * @param amount
     * @param tag
     * @param listener
     */
    public static void GetProfitByAmount(String token, String aToken, String borrowId, String amount, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=GetProfitByAmount");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&borrowId=");
        url.append(borrowId);
        url.append("&amount=");
        url.append(amount);
        url.append("&token=");

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
     * @param token
     * @param borrowid
     * @param amount
     * @param couponIds
     * @param tag
     * @param listener
     */
    public static void getAddLoan(String token, String aToken, int borrowid, String amount, int couponIds, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=DiYa&cmd=AddLoan");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
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
     * 获取余额生息信息
     *
     * @param token
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void getInterestRiseSummary(String token, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetInterestRiseSummary");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 余额生息收益列表
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetInterestRiseReturnList&pl=2&token=111
     * @param token
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void getInterestRiseReturnList(String token, String aToken, int pageIndex, int pageSize, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetInterestRiseReturnList");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&PageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 开通余额生息
     * @param token
     * @param aToken
     * @param drawPwd
     * @param tag
     * @param listener
     */
    public static void openInterestRise(String token, String aToken, String drawPwd, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=OpenInterestRise");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&drawPwd=");
        url.append(drawPwd);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取余额生息服务协议
     * @param token
     * @param aToken
     * @param tag
     * @param listener
     */
    public static void getInterrestRiseProtocalHtml(String token, String aToken, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetInterrestRiseProtocalHtml");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到所有红包的列表（用户个人中心，我的优惠页展示）
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetRedBagList&pl=2&token=111&pageSize=10&pageIndex=1
     * @param token
     * @param aToken
     * @param pageIndex
     * @param pageSize
     * @param tag
     * @param listener
     */
    public static void getRedBagList(String token, String aToken, int pageIndex, int pageSize, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetRedBagList");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&PageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 得到可使用的红包的列表（用于投标页面，选择红包）
     * http://pub2.bibao.com/Api.aspx?action=UserCenter&cmd=GetRedBagListCanUse&pl=2&token=111&pageSize=10&pageIndex=1
     * @param token
     * @param aToken
     * @param pageIndex
     * @param pageSize
     * @param tag
     * @param listener
     */
    public static void getRedBagListCanUse(String token, String aToken, String amount, int pageIndex, int pageSize, String tag, VolleyRequestListener listener) {
        StringBuffer url = new StringBuffer();
        url.append(API_UPL);
        url.append("?action=UserCenter&cmd=GetRedBagListCanUse");
        url.append("&pl=");
        url.append(PL);
        url.append("&atoken=");
        url.append(aToken);
        url.append("&amount=");
        url.append(amount);
        url.append("&PageIndex=");
        url.append(pageIndex);
        url.append("&pageSize=");
        url.append(pageSize);
        url.append("&token=");

        if (token.equals("")) {
            volleyGetNoToken(url.toString(), tag, listener);
        } else {
            url.append(token);
            volleyGet(url.toString(), tag, listener);
        }
    }

    /**
     * 获取token
     * @param tag
     * @param listener
     */
    public static void getToken( String tag,  VolleyRequestListener listener) {
        String tokenUrl = "http://pub99.bibao.com/Api.aspx?action=Token&cmd=getToken&key=a2288d34bd9d540ca7d5e7eb507c897e&pl=" + PL;
        volleyGet(tokenUrl.toString(), tag, listener);
    }

    public static void volleyGet(String url, String tag,  VolleyRequestListener listener) {
        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), listener.onSuccess(), listener.onError());
        MyApplication.getInstance().addToQueue(request, tag);
    }

    public static void volleyGetNoToken(final String url,  final String tag,  final VolleyRequestListener listener) {
        String tokenUrl = "http://pub99.bibao.com/Api.aspx?action=Token&cmd=getToken&key=a2288d34bd9d540ca7d5e7eb507c897e&pl=" + PL;
        MyApplication.getInstance().cancelPendingRequests(tag);
        StringRequest request = new StringRequest(Request.Method.GET, tokenUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String errorNo = LOAD_DATA_UTIL.getJsonValue(response, "errorNo");
                String message = LOAD_DATA_UTIL.getJsonValue(response, "message");
                if ("0".equals(errorNo)) {
                    spUtil.put(SPUtil.TOKEN, message);
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
}
