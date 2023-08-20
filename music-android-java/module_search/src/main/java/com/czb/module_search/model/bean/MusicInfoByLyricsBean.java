package com.czb.module_search.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.common.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MusicInfoByLyricsBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"list":[{"id":"1137786643277676544","singer_id":"1138081182076895232","name":"火星人来过-薛之谦","pic_id":"1691379429028_1138073341643456512.jpeg","lyric":"[00:00.000] 作词 : 薛之谦\n[00:01.000] 作曲 : 韩星洲\n[00:12.210]我在听新闻里面说 他们曾来过\n[00:19.020]<span style=\"color:#0071E0;\">火星<\/span>人的心脏靠左\n[00:23.170","url":"1691311074811_1137786643277676544.mp3","state":"3","path":null,"file_high_url":null,"singer_name":"薛之谦","count":"0","duration":"216"},{"id":"1137795736264507392","singer_id":"1139627669973893120","name":"浮夸-陈奕迅","pic_id":"1691379600355_1138074060240977920.jpeg","lyric":"]任何地方也像开四面台\n[01:03.563]着最闪的衫 扮十分感慨\n[01:07.496]有人来拍照要记住插袋\n[01:11.979]你当我是<span style=\"color:#0071E0;\">浮夸<\/span>吧 夸张只因我很怕\n[01:19.133]似木头 似石头的话","url":"1691313242748_1137795736264507392.mp3","state":"3","path":null,"file_high_url":"1691379052950_1138071764257996800.flac","singer_name":"陈奕迅","count":"0","duration":"283"}],"currentPage":1,"totalPage":1}
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
        return "MusicInfoByLyricsBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * list : [{"id":"1137786643277676544","singer_id":"1138081182076895232","name":"火星人来过-薛之谦","pic_id":"1691379429028_1138073341643456512.jpeg","lyric":"[00:00.000] 作词 : 薛之谦\n[00:01.000] 作曲 : 韩星洲\n[00:12.210]我在听新闻里面说 他们曾来过\n[00:19.020]<span style=\"color:#0071E0;\">火星<\/span>人的心脏靠左\n[00:23.170","url":"1691311074811_1137786643277676544.mp3","state":"3","path":null,"file_high_url":null,"singer_name":"薛之谦","count":"0","duration":"216"},{"id":"1137795736264507392","singer_id":"1139627669973893120","name":"浮夸-陈奕迅","pic_id":"1691379600355_1138074060240977920.jpeg","lyric":"]任何地方也像开四面台\n[01:03.563]着最闪的衫 扮十分感慨\n[01:07.496]有人来拍照要记住插袋\n[01:11.979]你当我是<span style=\"color:#0071E0;\">浮夸<\/span>吧 夸张只因我很怕\n[01:19.133]似木头 似石头的话","url":"1691313242748_1137795736264507392.mp3","state":"3","path":null,"file_high_url":"1691379052950_1138071764257996800.flac","singer_name":"陈奕迅","count":"0","duration":"283"}]
         * currentPage : 1
         * totalPage : 1
         */

        @SerializedName("currentPage")
        private Integer currentPage;
        @SerializedName("totalPage")
        private Integer totalPage;
        @SerializedName("list")
        private List<ListBean> list;

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
                    "currentPage=" + currentPage +
                    ", totalPage=" + totalPage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable , MultiItemEntity {
            /**
             * id : 1137786643277676544
             * singer_id : 1138081182076895232
             * name : 火星人来过-薛之谦
             * pic_id : 1691379429028_1138073341643456512.jpeg
             * lyric : [00:00.000] 作词 : 薛之谦
             [00:01.000] 作曲 : 韩星洲
             [00:12.210]我在听新闻里面说 他们曾来过
             [00:19.020]<span style="color:#0071E0;">火星</span>人的心脏靠左
             * url : 1691311074811_1137786643277676544.mp3
             * state : 3
             * path : null
             * file_high_url : null
             * singer_name : 薛之谦
             * count : 0
             * duration : 216
             */

            @SerializedName("id")
            private String id;
            @SerializedName("singer_id")
            private String singerId;
            @SerializedName("name")
            private String name;
            @SerializedName("pic_id")
            private String picId;
            @SerializedName("lyric")
            private String lyric;
            @SerializedName("url")
            private String url;
            @SerializedName("state")
            private String state;
            @SerializedName("path")
            private Object path;
            @SerializedName("file_high_url")
            private Object fileHighUrl;
            @SerializedName("singer_name")
            private String singerName;
            @SerializedName("count")
            private String count;
            @SerializedName("duration")
            private String duration;

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", singerId='" + singerId + '\'' +
                        ", name='" + name + '\'' +
                        ", picId='" + picId + '\'' +
                        ", lyric='" + lyric + '\'' +
                        ", url='" + url + '\'' +
                        ", state='" + state + '\'' +
                        ", path=" + path +
                        ", fileHighUrl=" + fileHighUrl +
                        ", singerName='" + singerName + '\'' +
                        ", count='" + count + '\'' +
                        ", duration='" + duration + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSingerId() {
                return singerId;
            }

            public void setSingerId(String singerId) {
                this.singerId = singerId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getLyric() {
                return lyric;
            }

            public void setLyric(String lyric) {
                this.lyric = lyric;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public Object getPath() {
                return path;
            }

            public void setPath(Object path) {
                this.path = path;
            }

            public Object getFileHighUrl() {
                return fileHighUrl;
            }

            public void setFileHighUrl(Object fileHighUrl) {
                this.fileHighUrl = fileHighUrl;
            }

            public String getSingerName() {
                return singerName;
            }

            public void setSingerName(String singerName) {
                this.singerName = singerName;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            @Override
            public int getItemType() {
                return Constants.SearchType.SEARCH_BY_LYRIC;
            }
        }
    }
}
