package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/5/11.
 */
public class UploadProduct {
    private String productId;
    private int count;

    public UploadProduct(String id, int count) {
        this.productId = id;
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
