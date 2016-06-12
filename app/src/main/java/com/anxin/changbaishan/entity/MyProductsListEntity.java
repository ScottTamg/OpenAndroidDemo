package com.anxin.changbaishan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Txw on 2016/5/24.
 */
public class MyProductsListEntity extends BaseEntity{
    /**
     * myWaterCount : 0
     * myCupCount : 0
     * cupCount : 0.5
     * waterProductId : 2
     * minDeliveryCount : 8
     * addressId : 58
     * addressMobile : 15901115671
     * addressUserName : ces
     * address : 北京东城区ceshidiz
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int myWaterCount;
        private int myCupCount;
        private double cupCount;
        private int waterProductId;
        private int minDeliveryCount;
        private int addressId;
        private String addressMobile;
        private String addressUserName;
        private String address;

        public int getYWaterCount() {
            return myWaterCount;
        }

        public void setYWaterCount(int yWaterCount) {
            myWaterCount = yWaterCount;
        }

        public int getYCupCount() {
            return myCupCount;
        }

        public void setYCupCount(int yCupCount) {
            myCupCount = yCupCount;
        }

        public double getCupCount() {
            return cupCount;
        }

        public void setCupCount(double cupCount) {
            this.cupCount = cupCount;
        }

        public int getWaterProductId() {
            return waterProductId;
        }

        public void setWaterProductId(int waterProductId) {
            this.waterProductId = waterProductId;
        }

        public int getInDeliveryCount() {
            return minDeliveryCount;
        }

        public void setInDeliveryCount(int inDeliveryCount) {
            minDeliveryCount = inDeliveryCount;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getAddressMobile() {
            return addressMobile;
        }

        public void setAddressMobile(String addressMobile) {
            this.addressMobile = addressMobile;
        }

        public String getAddressUserName() {
            return addressUserName;
        }

        public void setAddressUserName(String addressUserName) {
            this.addressUserName = addressUserName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.myWaterCount);
            dest.writeInt(this.myCupCount);
            dest.writeDouble(this.cupCount);
            dest.writeInt(this.waterProductId);
            dest.writeInt(this.minDeliveryCount);
            dest.writeInt(this.addressId);
            dest.writeString(this.addressMobile);
            dest.writeString(this.addressUserName);
            dest.writeString(this.address);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.myWaterCount = in.readInt();
            this.myCupCount = in.readInt();
            this.cupCount = in.readDouble();
            this.waterProductId = in.readInt();
            this.minDeliveryCount = in.readInt();
            this.addressId = in.readInt();
            this.addressMobile = in.readString();
            this.addressUserName = in.readString();
            this.address = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
