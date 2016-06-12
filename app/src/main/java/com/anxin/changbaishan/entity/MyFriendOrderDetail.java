package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/6/2.
 */
public class MyFriendOrderDetail extends BaseEntity {
    /**
     * orderId : 100010
     * orderTime : 2016-06-03
     * state : 0
     * stateName : 待领取
     * productList : [{"Name":"水桶","ProductID":1,"Count":4},{"Name":"长白山箱装水","ProductID":2,"Count":8}]
     * friendMobile : 15122208134
     * friendName : null
     * friendToken : 98d28bedd5db4f77aa3dc8e882e55b6a72021655
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
        private String orderTime;
        private int state;
        private String stateName;
        private String friendMobile;
        private String friendName;
        private String friendToken;
        /**
         * Name : 水桶
         * ProductID : 1
         * Count : 4
         */

        private List<ProductListBean> productList;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getFriendMobile() {
            return friendMobile;
        }

        public void setFriendMobile(String friendMobile) {
            this.friendMobile = friendMobile;
        }

        public String getFriendName() {
            return friendName;
        }

        public void setFriendName(String friendName) {
            this.friendName = friendName;
        }

        public String getFriendToken() {
            return friendToken;
        }

        public void setFriendToken(String friendToken) {
            this.friendToken = friendToken;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            private String Name;
            private int ProductID;
            private int Count;

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

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
        }
    }
}
