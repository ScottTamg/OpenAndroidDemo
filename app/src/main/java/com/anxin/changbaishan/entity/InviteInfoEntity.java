package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/6/5.
 */
public class InviteInfoEntity extends BaseEntity{
    /**
     * userLink : http://m.changbaishan.com/invite/user.html?key=100015
     * courierLink : http://m.changbaishan.com/invite/courier.html?key=100015
     * key : 100015
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String userLink;
        private String courierLink;
        private String key;

        public String getUserLink() {
            return userLink;
        }

        public void setUserLink(String userLink) {
            this.userLink = userLink;
        }

        public String getCourierLink() {
            return courierLink;
        }

        public void setCourierLink(String courierLink) {
            this.courierLink = courierLink;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
