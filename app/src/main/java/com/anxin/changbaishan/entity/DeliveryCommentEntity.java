package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/5/29.
 */
public class DeliveryCommentEntity extends BaseEntity {

    /**
     * orderId : 3
     * photo : http://img.anxin.com/users/userHeadImage/mini/sysavatar/txzl06.jpg
     * name : 送水员
     * addtime : 2016-06-06 13:41
     * stars : 0
     * starCount : 0.00
     * totalCount : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int orderId;
        private String photo;
        private String name;
        private String addtime;
        private int stars;
        private String starCount;
        private int totalCount;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getStarCount() {
            return starCount;
        }

        public void setStarCount(String starCount) {
            this.starCount = starCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
