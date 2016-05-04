package com.android.gaojun.weather.GSON;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class CityListDao {
    private List<RetData> retData;

    public List<RetData> getRetData() {
        return retData;
    }

    public void setRetData(List<RetData> retData) {
        this.retData = retData;
    }

    public class RetData{
        private String name_cn;
        private String area_id;

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }
}
