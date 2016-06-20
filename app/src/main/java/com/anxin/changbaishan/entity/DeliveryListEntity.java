package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/25.
 */
public class DeliveryListEntity extends BaseEntity {

    /**
     * totalCount : 5
     * list : [{"ID":23,"CTime":"2016-06-16 10:09","State":0,"StateName":"等待确认","orderDetail":{"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"8","cupCount":"4"},"hasComment":0},{"ID":18,"CTime":"2016-06-12 11:46","State":0,"StateName":"等待确认","orderDetail":{"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"14","cupCount":"6"},"hasComment":0},{"ID":3,"CTime":"2016-06-06 13:41","State":0,"StateName":"等待确认","orderDetail":{"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"8","cupCount":"4"},"hasComment":1},{"ID":2,"CTime":"2016-06-03 17:44","State":0,"StateName":"等待确认","orderDetail":{"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"8","cupCount":"4"},"hasComment":0},{"ID":1,"CTime":"2016-06-03 17:36","State":0,"StateName":"等待确认","orderDetail":{"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"8","cupCount":"4"},"hasComment":0}]
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
         * ID : 23
         * CTime : 2016-06-16 10:09
         * State : 0
         * StateName : 等待确认
         * orderDetail : {"name":"经典箱装水","Standard":"每箱10瓶","Photo":"http://static.anxin.com/changbaishan/m/images/home/temp-product2.png","waterCount":"8","cupCount":"4"}
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
            /**
             * name : 经典箱装水
             * Standard : 每箱10瓶
             * Photo : http://static.anxin.com/changbaishan/m/images/home/temp-product2.png
             * waterCount : 8
             * cupCount : 4
             */

            private OrderDetailBean orderDetail;
            private int hasComment;

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

            public OrderDetailBean getOrderDetail() {
                return orderDetail;
            }

            public void setOrderDetail(OrderDetailBean orderDetail) {
                this.orderDetail = orderDetail;
            }

            public int getHasComment() {
                return hasComment;
            }

            public void setHasComment(int hasComment) {
                this.hasComment = hasComment;
            }

            public static class OrderDetailBean {
                private String name;
                private String Standard;
                private String Photo;
                private String waterCount;
                private String cupCount;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
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

                public String getWaterCount() {
                    return waterCount;
                }

                public void setWaterCount(String waterCount) {
                    this.waterCount = waterCount;
                }

                public String getCupCount() {
                    return cupCount;
                }

                public void setCupCount(String cupCount) {
                    this.cupCount = cupCount;
                }
            }
        }
    }
}
