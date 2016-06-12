package com.anxin.changbaishan.entity;

/**
 * Created by Txw on 2016/5/4.
 */
public class BaseEntity {

    /**
     * errorNo : 0
     * message : 111
     * data : {}
     */

    private int errorNo;
    private String message;

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String essage) {
        message = essage;
    }
}
