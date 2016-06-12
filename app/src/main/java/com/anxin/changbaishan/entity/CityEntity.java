package com.anxin.changbaishan.entity;

import java.util.List;

/**
 * Created by Txw on 2016/5/5.
 */
public class CityEntity extends BaseEntity {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 1
         * CityName : 北京
         * CityLevel : 1
         * ParentId : 0
         * State : 0
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int ID;
            private String CityName;
            private int CityLevel;
            private int ParentId;
            private int State;

            public int getID() {
                return ID;
            }

            public void setID(int iD) {
                ID = iD;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String cityName) {
                CityName = cityName;
            }

            public int getCityLevel() {
                return CityLevel;
            }

            public void setCityLevel(int cityLevel) {
                CityLevel = cityLevel;
            }

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int parentId) {
                ParentId = parentId;
            }

            public int getState() {
                return State;
            }

            public void setState(int state) {
                State = state;
            }
        }
    }
}
