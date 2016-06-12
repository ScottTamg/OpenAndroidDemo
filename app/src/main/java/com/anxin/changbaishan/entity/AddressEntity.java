package com.anxin.changbaishan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Txw on 2016/5/16.
 */
public class AddressEntity extends BaseEntity{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 4
         * CityID : 1
         * DistrictID : 10
         * Address : 当代3楼
         * UserID : 100015
         * State : 1
         * CTime : 2016-05-12T11:48:53.72
         * CityName : 北京
         * DistrictName : 石景山区
         * UserName : 大款
         * Mobile : 15122208134
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            private int ID;
            private int CityID;
            private int DistrictID;
            private String Address;
            private int UserID;
            private int State;
            private String CTime;
            private String CityName;
            private String DistrictName;
            private String UserName;
            private String Mobile;

            public int getID() {
                return ID;
            }

            public void setID(int iD) {
                ID = iD;
            }

            public int getCityID() {
                return CityID;
            }

            public void setCityID(int cityID) {
                CityID = cityID;
            }

            public int getDistrictID() {
                return DistrictID;
            }

            public void setDistrictID(int districtID) {
                DistrictID = districtID;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String address) {
                Address = address;
            }

            public int getUserID() {
                return UserID;
            }

            public void setUserID(int userID) {
                UserID = userID;
            }

            public int getState() {
                return State;
            }

            public void setState(int state) {
                State = state;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String cTime) {
                CTime = cTime;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String cityName) {
                CityName = cityName;
            }

            public String getDistrictName() {
                return DistrictName;
            }

            public void setDistrictName(String districtName) {
                DistrictName = districtName;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String userName) {
                UserName = userName;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.ID);
                dest.writeInt(this.CityID);
                dest.writeInt(this.DistrictID);
                dest.writeString(this.Address);
                dest.writeInt(this.UserID);
                dest.writeInt(this.State);
                dest.writeString(this.CTime);
                dest.writeString(this.CityName);
                dest.writeString(this.DistrictName);
                dest.writeString(this.UserName);
                dest.writeString(this.Mobile);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.ID = in.readInt();
                this.CityID = in.readInt();
                this.DistrictID = in.readInt();
                this.Address = in.readString();
                this.UserID = in.readInt();
                this.State = in.readInt();
                this.CTime = in.readString();
                this.CityName = in.readString();
                this.DistrictName = in.readString();
                this.UserName = in.readString();
                this.Mobile = in.readString();
            }

            public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
