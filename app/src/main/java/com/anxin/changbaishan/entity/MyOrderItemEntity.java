package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/18.
 */
public class MyOrderItemEntity extends BaseEntity{
    /**
     * totalCount : 61
     * list : [{"orderId":100109,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100108,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100107,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100106,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100105,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100104,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100103,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100102,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100101,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]},{"orderId":100100,"orderTime":"2016-05-17","state":0,"stateName":"未付款","needMoney":"5600.00","productList":[{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int totalCount;
        /**
         * orderId : 100109
         * orderTime : 2016-05-17
         * state : 0
         * stateName : 未付款
         * needMoney : 5600.00
         * productList : [{"Count":5,"Name":"北京超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"Count":2,"Name":"上海超值10箱套餐","Standard":"十箱，原件","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]
         */

        private List<ListBean> list;

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
            private int orderId;
            private String orderTime;
            private int state;
            private String stateName;
            private String needMoney;
            /**
             * Count : 5
             * Name : 北京超值10箱套餐
             * Standard : 十箱，原件
             * Photo : http://static.anxin.com/changbaishan/m/images/home/temp-product.png
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

            public String getNeedMoney() {
                return needMoney;
            }

            public void setNeedMoney(String needMoney) {
                this.needMoney = needMoney;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                private int Count;
                private String Name;
                private String Standard;
                private String Photo;

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
        }
    }
}
