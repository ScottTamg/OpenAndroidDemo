package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/29.
 */
public class BonusPointsEntity extends BaseEntity {

    /**
     * bonusPoints : 15400
     * money : 154.00
     * totalCount : 3
     * list : [{"name":"组合订单","count":4,"photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png","date":"2016-05-12","amount":"+9640","stateName":"订水送积分"},{"name":"组合订单","count":4,"photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png","date":"2016-05-12","amount":"+4600","stateName":"订水送积分"},{"name":"组合订单","count":2,"photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png","date":"2016-05-12","amount":"+1160","stateName":"订水送积分"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int bonusPoints;
        private String money;
        private int totalCount;
        /**
         * name : 组合订单
         * count : 4
         * photo : http://static.anxin.com/changbaishan/m/images/home/temp-product.png
         * date : 2016-05-12
         * amount : +9640
         * stateName : 订水送积分
         */

        private List<ListBean> list;

        public int getBonusPoints() {
            return bonusPoints;
        }

        public void setBonusPoints(int bonusPoints) {
            this.bonusPoints = bonusPoints;
        }

        public String getOney() {
            return money;
        }

        public void setOney(String oney) {
            money = oney;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String name;
            private int count;
            private String photo;
            private String date;
            private String amount;
            private String stateName;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }
        }
    }
}
