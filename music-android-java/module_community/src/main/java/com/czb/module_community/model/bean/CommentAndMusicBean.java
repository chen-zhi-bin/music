package com.czb.module_community.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommentAndMusicBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取评论成功
     * data : {"list":[{"id":"1143268972066504704","avatar":"1691288795217_1137693195887443968.jpeg","userId":"1137189005566148608","userName":"user","songId":"1137786643277676544","comment":"评论测试","commentTime":"2023-08-21 19:42:44","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null},{"id":"1143229459051053056","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137786643277676544","comment":"真好听","commentTime":"2023-08-21 17:05:43","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null},{"id":"1139233338146422784","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137795736264507392","comment":"评论测试--浮夸","commentTime":"2023-08-10 16:26:34","musicId":"1137795736264507392","singerName":"陈奕迅","singerId":"1139627669973893120","musicName":"浮夸-陈奕迅","picId":"1691379600355_1138074060240977920.jpeg","url":"1691313242748_1137795736264507392.mp3","duration":"283","contentType":"mp3/flac","fileHighUrl":"1691379052950_1138071764257996800.flac"},{"id":"1139227408403005440","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137786643277676544","comment":"写实一般的音乐","commentTime":"2023-08-10 16:03:00","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null}],"currentPage":1,"maxPage":1}
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBean data;

    @Override
    public String toString() {
        return "CommentAndMusicBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

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

    public static class DataBean implements Serializable {
        /**
         * list : [{"id":"1143268972066504704","avatar":"1691288795217_1137693195887443968.jpeg","userId":"1137189005566148608","userName":"user","songId":"1137786643277676544","comment":"评论测试","commentTime":"2023-08-21 19:42:44","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null},{"id":"1143229459051053056","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137786643277676544","comment":"真好听","commentTime":"2023-08-21 17:05:43","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null},{"id":"1139233338146422784","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137795736264507392","comment":"评论测试--浮夸","commentTime":"2023-08-10 16:26:34","musicId":"1137795736264507392","singerName":"陈奕迅","singerId":"1139627669973893120","musicName":"浮夸-陈奕迅","picId":"1691379600355_1138074060240977920.jpeg","url":"1691313242748_1137795736264507392.mp3","duration":"283","contentType":"mp3/flac","fileHighUrl":"1691379052950_1138071764257996800.flac"},{"id":"1139227408403005440","avatar":"default_avatar.png","userId":"1136809343182700544","userName":"super_admin","songId":"1137786643277676544","comment":"写实一般的音乐","commentTime":"2023-08-10 16:03:00","musicId":"1137786643277676544","singerName":"薛之谦","singerId":"1138081182076895232","musicName":"火星人来过-薛之谦","picId":"1691379429028_1138073341643456512.jpeg","url":"1691311074811_1137786643277676544.mp3","duration":"216","contentType":"mp3","fileHighUrl":null}]
         * currentPage : 1
         * maxPage : 1
         */

        @SerializedName("currentPage")
        private Integer currentPage;
        @SerializedName("maxPage")
        private Integer maxPage;
        @SerializedName("list")
        private List<ListBean> list;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getMaxPage() {
            return maxPage;
        }

        public void setMaxPage(Integer maxPage) {
            this.maxPage = maxPage;
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
                    ", maxPage=" + maxPage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable {
            /**
             * id : 1143268972066504704
             * avatar : 1691288795217_1137693195887443968.jpeg
             * userId : 1137189005566148608
             * userName : user
             * songId : 1137786643277676544
             * comment : 评论测试
             * commentTime : 2023-08-21 19:42:44
             * musicId : 1137786643277676544
             * singerName : 薛之谦
             * singerId : 1138081182076895232
             * musicName : 火星人来过-薛之谦
             * picId : 1691379429028_1138073341643456512.jpeg
             * url : 1691311074811_1137786643277676544.mp3
             * duration : 216
             * contentType : mp3
             * fileHighUrl : null
             */

            @SerializedName("id")
            private String id;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("userId")
            private String userId;
            @SerializedName("userName")
            private String userName;
            @SerializedName("songId")
            private String songId;
            @SerializedName("comment")
            private String comment;
            @SerializedName("commentTime")
            private String commentTime;
            @SerializedName("musicId")
            private String musicId;
            @SerializedName("singerName")
            private String singerName;
            @SerializedName("singerId")
            private String singerId;
            @SerializedName("musicName")
            private String musicName;
            @SerializedName("picId")
            private String picId;
            @SerializedName("url")
            private String url;
            @SerializedName("duration")
            private String duration;
            @SerializedName("contentType")
            private String contentType;
            @SerializedName("fileHighUrl")
            private String fileHighUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getSongId() {
                return songId;
            }

            public void setSongId(String songId) {
                this.songId = songId;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public String getMusicId() {
                return musicId;
            }

            public void setMusicId(String musicId) {
                this.musicId = musicId;
            }

            public String getSingerName() {
                return singerName;
            }

            public void setSingerName(String singerName) {
                this.singerName = singerName;
            }

            public String getSingerId() {
                return singerId;
            }

            public void setSingerId(String singerId) {
                this.singerId = singerId;
            }

            public String getMusicName() {
                return musicName;
            }

            public void setMusicName(String musicName) {
                this.musicName = musicName;
            }

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }

            public String getFileHighUrl() {
                return fileHighUrl;
            }

            public void setFileHighUrl(String fileHighUrl) {
                this.fileHighUrl = fileHighUrl;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", songId='" + songId + '\'' +
                        ", comment='" + comment + '\'' +
                        ", commentTime='" + commentTime + '\'' +
                        ", musicId='" + musicId + '\'' +
                        ", singerName='" + singerName + '\'' +
                        ", singerId='" + singerId + '\'' +
                        ", musicName='" + musicName + '\'' +
                        ", picId='" + picId + '\'' +
                        ", url='" + url + '\'' +
                        ", duration='" + duration + '\'' +
                        ", contentType='" + contentType + '\'' +
                        ", fileHighUrl='" + fileHighUrl + '\'' +
                        '}';
            }
        }
    }
}
