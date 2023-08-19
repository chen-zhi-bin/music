package com.czb.module_home.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.common.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class RecommendMusicBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取推荐成功
     * data : {"hasMore":false,"list":[{"musicId":"1139641397154938880","musicName":"演员-薛之谦","url":"1691753282595_1139641397154938880.mp3","fileHighUrl":"1691753299299_1139641467216592896.flac","createTime":"2023-08-11 19:28:03","singerName":"薛之谦","picId":null},{"musicId":"1139640955964489728","musicName":"狐狸-薛之谦","url":"1691753177407_1139640955964489728.mp3","fileHighUrl":"1691753191220_1139641013900410880.flac","createTime":"2023-08-11 19:26:17","singerName":"薛之谦","picId":null},{"musicId":"1139639874941353984","musicName":"花儿和少年-薛之谦","url":"1691752919671_1139639874941353984.mp3","fileHighUrl":"1691752929262_1139639915168923648.flac","createTime":"2023-08-11 19:22:00","singerName":"薛之谦","picId":null},{"musicId":"1139639357628481536","musicName":"野心-薛之谦","url":"1691752796333_1139639357628481536.mp3","fileHighUrl":"1691752812927_1139639427224567808.flac","createTime":"2023-08-11 19:19:56","singerName":"薛之谦","picId":null},{"musicId":"1139637379733127168","musicName":"耗尽-薛之谦,郭聪明","url":"1691752324767_1139637379733127168.mp3","fileHighUrl":"1691752411954_1139637745426104320.flac","createTime":"2023-08-11 19:12:05","singerName":null,"picId":null},{"musicId":"1139629777351606272","musicName":"纸船-薛之谦,郁可唯","url":"1691750512218_1139629777351606272.mp3","fileHighUrl":"1691750576251_1139630045925474304.flac","createTime":"2023-08-11 18:41:52","singerName":null,"picId":null},{"musicId":"1138149983254478848","musicName":"还在流浪-周杰伦","url":"1691397701812_1138149983254478848.mp3","fileHighUrl":null,"createTime":"2023-08-07 16:41:42","singerName":"周杰伦","picId":null},{"musicId":"1138068896973062144","musicName":"雅俗共赏-许嵩","url":"1691378369336_1138068896973062144.mp3","fileHighUrl":null,"createTime":"2023-08-07 11:19:29","singerName":"许嵩","picId":null}],"currentPage":1,"maxpage":1}
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
        return "RecommendMusicBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


    public static class DataBean implements Serializable {
        /**
         * hasMore : false
         * list : [{"musicId":"1139641397154938880","musicName":"演员-薛之谦","url":"1691753282595_1139641397154938880.mp3","fileHighUrl":"1691753299299_1139641467216592896.flac","createTime":"2023-08-11 19:28:03","singerName":"薛之谦","picId":null},{"musicId":"1139640955964489728","musicName":"狐狸-薛之谦","url":"1691753177407_1139640955964489728.mp3","fileHighUrl":"1691753191220_1139641013900410880.flac","createTime":"2023-08-11 19:26:17","singerName":"薛之谦","picId":null},{"musicId":"1139639874941353984","musicName":"花儿和少年-薛之谦","url":"1691752919671_1139639874941353984.mp3","fileHighUrl":"1691752929262_1139639915168923648.flac","createTime":"2023-08-11 19:22:00","singerName":"薛之谦","picId":null},{"musicId":"1139639357628481536","musicName":"野心-薛之谦","url":"1691752796333_1139639357628481536.mp3","fileHighUrl":"1691752812927_1139639427224567808.flac","createTime":"2023-08-11 19:19:56","singerName":"薛之谦","picId":null},{"musicId":"1139637379733127168","musicName":"耗尽-薛之谦,郭聪明","url":"1691752324767_1139637379733127168.mp3","fileHighUrl":"1691752411954_1139637745426104320.flac","createTime":"2023-08-11 19:12:05","singerName":null,"picId":null},{"musicId":"1139629777351606272","musicName":"纸船-薛之谦,郁可唯","url":"1691750512218_1139629777351606272.mp3","fileHighUrl":"1691750576251_1139630045925474304.flac","createTime":"2023-08-11 18:41:52","singerName":null,"picId":null},{"musicId":"1138149983254478848","musicName":"还在流浪-周杰伦","url":"1691397701812_1138149983254478848.mp3","fileHighUrl":null,"createTime":"2023-08-07 16:41:42","singerName":"周杰伦","picId":null},{"musicId":"1138068896973062144","musicName":"雅俗共赏-许嵩","url":"1691378369336_1138068896973062144.mp3","fileHighUrl":null,"createTime":"2023-08-07 11:19:29","singerName":"许嵩","picId":null}]
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
             * musicId : 1139641397154938880
             * musicName : 演员-薛之谦
             * url : 1691753282595_1139641397154938880.mp3
             * fileHighUrl : 1691753299299_1139641467216592896.flac
             * createTime : 2023-08-11 19:28:03
             * singerName : 薛之谦
             * picId : null
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

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
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
                        ", picId=" + picId +
                        ", duration=" + duration +
                        '}';
            }

            @Override
            public int getItemType() {
                return Constants.MultiItemType.TYPE_RECOMMEND;
            }
        }
    }
}
