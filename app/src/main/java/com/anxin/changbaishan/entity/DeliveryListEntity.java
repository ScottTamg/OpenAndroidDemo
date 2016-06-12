package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/25.
 */
public class DeliveryListEntity extends BaseEntity {

    /**
     * totalCount : 50
     * list : [{"ID":85,"CTime":"2016-05-25 13:45","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":84,"CTime":"2016-05-25 11:50","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":83,"CTime":"2016-05-25 11:50","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":82,"CTime":"2016-05-24 13:36","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":8,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":1},{"ID":81,"CTime":"2016-05-23 14:37","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":8,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":80,"CTime":"2016-05-19 21:21","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":8,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":1},{"ID":79,"CTime":"2016-05-19 21:20","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":5,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":11,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":78,"CTime":"2016-05-19 21:20","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":5,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":11,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":77,"CTime":"2016-05-19 21:20","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":5,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":11,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0},{"ID":76,"CTime":"2016-05-19 21:20","State":0,"StateName":"申请配送","orderList":[{"ProductID":1,"Count":5,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"},{"ProductID":2,"Count":11,"Name":"长白山箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}],"hasComment":0}]
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
         * ID : 85
         * CTime : 2016-05-25 13:45
         * State : 0
         * StateName : 申请配送
         * orderList : [{"ProductID":1,"Count":4,"Name":"水桶","Standard":"20厘米高","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product.png"}]
         * hasComment : 0
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
            private int ID;
            private String CTime;
            private int State;
            private String StateName;
            private int hasComment;
            /**
             * ProductID : 1
             * Count : 4
             * Name : 水桶
             * Standard : 20厘米高
             * Photo : http://static.anxin.com/changbaishan/m/images/home/temp-product.png
             */

            private List<OrderListBean> orderList;

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

            public int getHasComment() {
                return hasComment;
            }

            public void setHasComment(int hasComment) {
                this.hasComment = hasComment;
            }

            public List<OrderListBean> getOrderList() {
                return orderList;
            }

            public void setOrderList(List<OrderListBean> orderList) {
                this.orderList = orderList;
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
        }
    }
}
