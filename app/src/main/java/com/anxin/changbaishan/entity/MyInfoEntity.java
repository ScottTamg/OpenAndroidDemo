package com.anxin.changbaishan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Txw on 2016/5/30.
 */
public class MyInfoEntity extends BaseEntity {

    /**
     * myWaterCount : 213
     * myCupCount : 58
     * myPoints : 24800
     * cupProductId : 1
     * cupCount : 0.5
     * waterProductId : 2
     * minDeliveryCount : 8
     * needToPay : 67
     * friendGiveToMe : 3
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String myWaterCount;
        private String myCupCount;
        private int myPoints;
        private int cupProductId;
        private double cupCount;
        private int waterProductId;
        private int minDeliveryCount;
        private int needToPay;
        private int friendGiveToMe;

        public String getYWaterCount() {
            return myWaterCount;
        }

        public void setYWaterCount(String yWaterCount) {
            myWaterCount = yWaterCount;
        }

        public String getYCupCount() {
            return myCupCount;
        }

        public void setYCupCount(String yCupCount) {
            myCupCount = yCupCount;
        }

        public int getYPoints() {
            return myPoints;
        }

        public void setYPoints(int yPoints) {
            myPoints = yPoints;
        }

        public int getCupProductId() {
            return cupProductId;
        }

        public void setCupProductId(int cupProductId) {
            this.cupProductId = cupProductId;
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

        public int getNeedToPay() {
            return needToPay;
        }

        public void setNeedToPay(int needToPay) {
            this.needToPay = needToPay;
        }

        public int getFriendGiveToMe() {
            return friendGiveToMe;
        }

        public void setFriendGiveToMe(int friendGiveToMe) {
            this.friendGiveToMe = friendGiveToMe;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.myWaterCount);
            dest.writeString(this.myCupCount);
            dest.writeInt(this.myPoints);
            dest.writeInt(this.cupProductId);
            dest.writeDouble(this.cupCount);
            dest.writeInt(this.waterProductId);
            dest.writeInt(this.minDeliveryCount);
            dest.writeInt(this.needToPay);
            dest.writeInt(this.friendGiveToMe);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.myWaterCount = in.readString();
            this.myCupCount = in.readString();
            this.myPoints = in.readInt();
            this.cupProductId = in.readInt();
            this.cupCount = in.readDouble();
            this.waterProductId = in.readInt();
            this.minDeliveryCount = in.readInt();
            this.needToPay = in.readInt();
            this.friendGiveToMe = in.readInt();
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
