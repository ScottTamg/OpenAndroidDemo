package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/5/24.
 */
public class CreatDeliveryOrdersEntity extends BaseEntity {

    /**
     * orderId : 100019
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
