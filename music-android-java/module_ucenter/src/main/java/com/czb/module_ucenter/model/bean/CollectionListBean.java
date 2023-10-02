package com.czb.module_ucenter.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class CollectionListBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"hasMore":false,"list":[{"musicId":"1137786643277676544","name":"火星人来过","singerName":"薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null,"picId":"1691379429028_1138073341643456512.jpeg","duration":"216"},{"musicId":"1137874427266990080","name":"最伟大的作品","singerName":"周杰伦","url":"1691332004145_1137874427266990080.mp3","fileHighUrl":"1691379667734_1138074342848987136.flac","picId":null,"duration":"244"},{"musicId":"1139639357628481536","name":"野心","singerName":"薛之谦","url":"1691752796333_1139639357628481536.mp3","fileHighUrl":"1691752812927_1139639427224567808.flac","picId":null,"duration":"220"}],"currentPage":1,"totalPage":1}
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBean data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CollectionListBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * hasMore : false
         * list : [{"musicId":"1137786643277676544","name":"火星人来过","singerName":"薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null,"picId":"1691379429028_1138073341643456512.jpeg","duration":"216"},{"musicId":"1137874427266990080","name":"最伟大的作品","singerName":"周杰伦","url":"1691332004145_1137874427266990080.mp3","fileHighUrl":"1691379667734_1138074342848987136.flac","picId":null,"duration":"244"},{"musicId":"1139639357628481536","name":"野心","singerName":"薛之谦","url":"1691752796333_1139639357628481536.mp3","fileHighUrl":"1691752812927_1139639427224567808.flac","picId":null,"duration":"220"}]
         * currentPage : 1
         * totalPage : 1
         */

        @SerializedName("hasMore")
        private Boolean hasMore;
        @SerializedName("currentPage")
        private Integer currentPage;
        @SerializedName("totalPage")
        private Integer totalPage;
        @SerializedName("list")
        private List<ListBean> list;

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "hasMore=" + hasMore +
                    ", currentPage=" + currentPage +
                    ", totalPage=" + totalPage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable {
            /**
             * musicId : 1137786643277676544
             * name : 火星人来过
             * singerName : 薛之谦
             * url : 1691311074811_1137786643277676544.mp3
             * fileHighUrl : null
             * picId : 1691379429028_1138073341643456512.jpeg
             * duration : 216
             */

            @SerializedName("musicId")
            private String musicId;
            @SerializedName("name")
            private String name;
            @SerializedName("singerName")
            private String singerName;
            @SerializedName("url")
            private String url;
            @SerializedName("fileHighUrl")
            private Object fileHighUrl;
            @SerializedName("picId")
            private String picId;
            @SerializedName("duration")
            private String duration;

            public String getMusicId() {
                return musicId;
            }

            public void setMusicId(String musicId) {
                this.musicId = musicId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSingerName() {
                return singerName;
            }

            public void setSingerName(String singerName) {
                this.singerName = singerName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getFileHighUrl() {
                return fileHighUrl;
            }

            public void setFileHighUrl(Object fileHighUrl) {
                this.fileHighUrl = fileHighUrl;
            }

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "musicId='" + musicId + '\'' +
                        ", name='" + name + '\'' +
                        ", singerName='" + singerName + '\'' +
                        ", url='" + url + '\'' +
                        ", fileHighUrl=" + fileHighUrl +
                        ", picId='" + picId + '\'' +
                        ", duration='" + duration + '\'' +
                        '}';
            }
        }
    }
}
