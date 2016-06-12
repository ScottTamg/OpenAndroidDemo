package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/5.
 */
public class ProductEntity extends BaseEntity {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 5
         * Name : 上海超值10箱套餐
         * Standard : 十箱，原件1200，现价800
         * SellPrice : 800.00
         * Photo : http://static.anxin.com/m/images/controls/floatapp/logo.png
         * Summary : 优惠800元，并送饮水机
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String ID;
            private String Name;
            private String Standard;
            private String SellPrice;
            private String Photo;
            private String Summary;
            private int Count = 1;
            private boolean IsChecked = false;

            public String getID() {
                return ID;
            }

            public void setID(String iD) {
                ID = iD;
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

            public String getSellPrice() {
                return SellPrice;
            }

            public void setSellPrice(String sellPrice) {
                SellPrice = sellPrice;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String photo) {
                Photo = photo;
            }

            public String getSummary() {
                return Summary;
            }

            public void setSummary(String summary) {
                Summary = summary;
            }

            public int getCount() {
                return Count;
            }

            public void setCount(int count) {
                Count = count;
            }

            public boolean getChecked() {
                return IsChecked;
            }

            public void setChecked(boolean checked) {
                IsChecked = checked;
            }
        }
    }
}
