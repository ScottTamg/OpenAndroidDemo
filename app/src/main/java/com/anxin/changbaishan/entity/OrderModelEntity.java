package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/11.
 */
public class OrderModelEntity extends BaseEntity{
    /**
     * BonusPoints : 0
     * BonusPointsMessage : 可抵扣0.00元
     * needMoney : 2460.00
     * bonusMoney : 0.0
     * realMoney : 2460.0
     * list : [{"Count":2,"Name":"长白山箱装水","Standard":"每箱10瓶，原价200，现价120","Photo":"http://static.anxin.com/m/images/controls/floatapp/logo.png","SellPrice":120,"State":1},{"Count":3,"Name":"北京超值10箱套餐","Standard":"十箱，原件1200，现价800","Photo":"http://static.anxin.com/m/images/controls/floatapp/logo.png","SellPrice":800,"State":1}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int BonusPoints;
        private String BonusPointsMessage;
        private String needMoney;
        private double bonusMoney;
        private double realMoney;
        /**
         * Count : 2
         * Name : 长白山箱装水
         * Standard : 每箱10瓶，原价200，现价120
         * Photo : http://static.anxin.com/m/images/controls/floatapp/logo.png
         * SellPrice : 120.0
         * State : 1
         */

        private List<ListBean> list;

        public int getBonusPoints() {
            return BonusPoints;
        }

        public void setBonusPoints(int bonusPoints) {
            BonusPoints = bonusPoints;
        }

        public String getBonusPointsMessage() {
            return BonusPointsMessage;
        }

        public void setBonusPointsMessage(String bonusPointsMessage) {
            BonusPointsMessage = bonusPointsMessage;
        }

        public String getNeedMoney() {
            return needMoney;
        }

        public void setNeedMoney(String needMoney) {
            this.needMoney = needMoney;
        }

        public double getBonusMoney() {
            return bonusMoney;
        }

        public void setBonusMoney(double bonusMoney) {
            this.bonusMoney = bonusMoney;
        }

        public double getRealMoney() {
            return realMoney;
        }

        public void setRealMoney(double realMoney) {
            this.realMoney = realMoney;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int Count;
            private String Name;
            private String Standard;
            private String Photo;
            private double SellPrice;
            private int State;

            public int getCount() {
                return Count;
            }

            public void setCount(int count) {
                Count = count;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getStandard() {
                return Standard;
            }

            public void setStandard(String standard) {
                Standard = standard;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String photo) {
                Photo = photo;
            }

            public double getSellPrice() {
                return SellPrice;
            }

            public void setSellPrice(double sellPrice) {
                SellPrice = sellPrice;
            }

            public int getState() {
                return State;
            }

            public void setState(int state) {
                State = state;
            }
        }
    }
}
