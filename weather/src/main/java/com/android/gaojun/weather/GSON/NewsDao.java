package com.android.gaojun.weather.GSON;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class NewsDao {
    private Body showapi_res_body;

    public Body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public class Body{
        private Pagebean pagebean;

        public Pagebean getPagebean() {
            return pagebean;
        }

        public void setPagebean(Pagebean pagebean) {
            this.pagebean = pagebean;
        }
    }

    public class Pagebean{
        private List<ContentList> contentlist;

        public List<ContentList> getContentlist() {
            return contentlist;
        }

        public void setContentlist(List<ContentList> contentlist) {
            this.contentlist = contentlist;
        }
    }

    public class ContentList{
        private String channelId;
        private String channelName;
        private String title;
        private String link;
        private List<ImageUrls> imageurls;
        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<ImageUrls> getImageurls() {
            return imageurls;
        }

        public void setImageurls(List<ImageUrls> imageurls) {
            this.imageurls = imageurls;
        }
    }

    public class ImageUrls{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
