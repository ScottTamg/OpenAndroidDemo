package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/27.
 */
public class DeliveryModelEntity extends BaseEntity {

    /**
     * ID : 82
     * CTime : 2016-05-24 13:36
     * State : 0
     * StateName : 等待确认
     * orderList : [{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":8,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]
     * recordList : [{"CTime":"2016-05-27 10:22","message":"已完成"},{"CTime":"2016-05-24 13:36","message":"配送员已取货，正在向您奔去，配送员：葛学荣，联系电话：13260452984"},{"CTime":"2016-05-23 14:37","message":"预约成功，订单号：82"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int ID;
        private String CTime;
        private int State;
        private String StateName;
        /**
         * ProductID : 1
         * Count : 4
         * Name : 水桶
         * Standard : 20厘米高
         * Photo : http://static.anxin.com/changbaishan/m/images/home/temp-product.png
         */

        private List<OrderListBean> orderList;
        /**
         * CTime : 2016-05-27 10:22
         * message : 已完成
         */

        private List<RecordListBean> recordList;

        public int getID() {
            return ID;
        }

        public void setID(int iD) {
            ID = iD;
        }

        public String getCTime() {
            return CTime;
        }

        public void setCTime(String cTime) {
            CTime = cTime;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String stateName) {
            StateName = stateName;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public List<RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<RecordListBean> recordList) {
            this.recordList = recordList;
        }

        public static class OrderListBean {
            private int ProductID;
            private int Count;
            private String Name;
            private String Standard;
            private String Photo;

            public int getProductID() {
                return ProductID;
            }

            public void setProductID(int productID) {
                ProductID = productID;
            }

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
        }

        public static class RecordListBean {
            private String CTime;
            private String message;

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String cTime) {
                CTime = cTime;
            }

            public String getEssage() {
                return message;
            }

            public void setEssage(String essage) {
                message = essage;
            }
        }
    }
}
