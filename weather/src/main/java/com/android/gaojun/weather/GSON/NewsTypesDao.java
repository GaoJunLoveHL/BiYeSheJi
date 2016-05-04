package com.android.gaojun.weather.GSON;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class NewsTypesDao {
    private Body showapi_res_body;

    public Body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public class Body{
        private List<ChannelList> channelList;

        public List<ChannelList> getChannelList() {
            return channelList;
        }

        public void setChannelList(List<ChannelList> channelList) {
            this.channelList = channelList;
        }
    }

    public class ChannelList{
        private String channelId;
        private String name;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
