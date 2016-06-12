package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/5/4.
 */
public class LoginEntity extends BaseEntity{

    /**
     * atoken : asdf231lk4j2l;3sv02323423
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String atoken;

        public String getAtoken() {
            return atoken;
        }

        public void setAtoken(String atoken) {
            this.atoken = atoken;
        }
    }
}
