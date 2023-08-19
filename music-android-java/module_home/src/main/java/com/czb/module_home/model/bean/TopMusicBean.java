package com.czb.module_home.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.common.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopMusicBean implements Serializable {
    /**
     * success : true
     * code : 20000
     * message : 获取推荐成功
     * data : {"hasMore":false,"list":[{"musicId":"1137786643277676544","musicName":"火星人来过-薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null,"createTime":"2023-08-06 16:37:55","singerName":null}],"currentPage":1,"maxpage":1}
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
        return "TopMusicBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * hasMore : false
         * list : [{"musicId":"1137786643277676544","musicName":"火星人来过-薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null,"createTime":"2023-08-06 16:37:55","singerName":null}]
         * currentPage : 1
         * maxpage : 1
         */

        @SerializedName("hasMore")
        private Boolean hasMore;
        @SerializedName("currentPage")
        private Integer currentPage;
        @SerializedName("maxpage")
        private Integer maxpage;
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

        public Integer getMaxpage() {
            return maxpage;
        }

        public void setMaxpage(Integer maxpage) {
            this.maxpage = maxpage;
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
                    ", maxpage=" + maxpage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable , MultiItemEntity {
            /**
             * musicId : 1137786643277676544
             * musicName : 火星人来过-薛之谦
             * url : 1691311074811_1137786643277676544.mp3
             * fileHighUrl : null
             * createTime : 2023-08-06 16:37:55
             * singerName : null
             */

            @SerializedName("musicId")
            private String musicId;
            @SerializedName("musicName")
            private String musicName;
            @SerializedName("url")
            private String url;
            @SerializedName("fileHighUrl")
            private String fileHighUrl;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("singerName")
            private String singerName;
            @SerializedName("picId")
            private String picId;
            @SerializedName("duration")
            private Long duration;

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getMusicId() {
                return musicId;
            }

            public void setMusicId(String musicId) {
                this.musicId = musicId;
            }

            public String getMusicName() {
                return musicName;
            }

            public void setMusicName(String musicName) {
                this.musicName = musicName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getFileHighUrl() {
                return fileHighUrl;
            }

            public void setFileHighUrl(String fileHighUrl) {
                this.fileHighUrl = fileHighUrl;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getSingerName() {
                return singerName;
            }

            public void setSingerName(String singerName) {
                this.singerName = singerName;
            }

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "musicId='" + musicId + '\'' +
                        ", musicName='" + musicName + '\'' +
                        ", url='" + url + '\'' +
                        ", fileHighUrl='" + fileHighUrl + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", singerName='" + singerName + '\'' +
                        ", picId='" + picId + '\'' +
                        ", duration=" + duration +
                        '}';
            }

            @Override
            public int getItemType() {
                return Constants.MultiItemType.TYPE_TOP;
            }
        }
    }
}
