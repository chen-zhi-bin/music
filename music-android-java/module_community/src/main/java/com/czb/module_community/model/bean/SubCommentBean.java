package com.czb.module_community.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubCommentBean implements Serializable {
    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"hasMore":false,"list":[{"id":"1143278522358300672","avatar":"string","userName":"user","userId":"1137189005566148608","songId":"1137786643277676544","comment":"多评论测试","createTime":"2023-08-21 20:20:41","updateTime":"2023-08-21 20:20:41","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"},{"id":"1143278429911646208","avatar":"string","userName":"user","userId":"1137189005566148608","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-21 20:20:19","updateTime":"2023-08-21 20:20:19","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"},{"id":"1143273103669854208","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-21 19:59:09","updateTime":"2023-08-21 19:59:09","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"}],"curentPage":1,"titalPage":1}
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
        return "SubCommentBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * hasMore : false
         * list : [{"id":"1143278522358300672","avatar":"string","userName":"user","userId":"1137189005566148608","songId":"1137786643277676544","comment":"多评论测试","createTime":"2023-08-21 20:20:41","updateTime":"2023-08-21 20:20:41","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"},{"id":"1143278429911646208","avatar":"string","userName":"user","userId":"1137189005566148608","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-21 20:20:19","updateTime":"2023-08-21 20:20:19","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"},{"id":"1143273103669854208","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-21 19:59:09","updateTime":"2023-08-21 19:59:09","state":"1","parentId":"1143268972066504704","parentUserId":"1137189005566148608","parentUserName":"user"}]
         * curentPage : 1
         * titalPage : 1
         */

        @SerializedName("hasMore")
        private Boolean hasMore;
        @SerializedName("curentPage")
        private Integer curentPage;
        @SerializedName("titalPage")
        private Integer titalPage;
        @SerializedName("list")
        private List<ListBean> list;

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Integer getCurentPage() {
            return curentPage;
        }

        public void setCurentPage(Integer curentPage) {
            this.curentPage = curentPage;
        }

        public Integer getTitalPage() {
            return titalPage;
        }

        public void setTitalPage(Integer titalPage) {
            this.titalPage = titalPage;
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
                    ", curentPage=" + curentPage +
                    ", titalPage=" + titalPage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable {
            /**
             * id : 1143278522358300672
             * avatar : string
             * userName : user
             * userId : 1137189005566148608
             * songId : 1137786643277676544
             * comment : 多评论测试
             * createTime : 2023-08-21 20:20:41
             * updateTime : 2023-08-21 20:20:41
             * state : 1
             * parentId : 1143268972066504704
             * parentUserId : 1137189005566148608
             * parentUserName : user
             */

            @SerializedName("id")
            private String id;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("userName")
            private String userName;
            @SerializedName("userId")
            private String userId;
            @SerializedName("songId")
            private String songId;
            @SerializedName("comment")
            private String comment;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("updateTime")
            private String updateTime;
            @SerializedName("state")
            private String state;
            @SerializedName("parentId")
            private String parentId;
            @SerializedName("parentUserId")
            private String parentUserId;
            @SerializedName("parentUserName")
            private String parentUserName;

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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getParentUserId() {
                return parentUserId;
            }

            public void setParentUserId(String parentUserId) {
                this.parentUserId = parentUserId;
            }

            public String getParentUserName() {
                return parentUserName;
            }

            public void setParentUserName(String parentUserName) {
                this.parentUserName = parentUserName;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userId='" + userId + '\'' +
                        ", songId='" + songId + '\'' +
                        ", comment='" + comment + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", updateTime='" + updateTime + '\'' +
                        ", state='" + state + '\'' +
                        ", parentId='" + parentId + '\'' +
                        ", parentUserId='" + parentUserId + '\'' +
                        ", parentUserName='" + parentUserName + '\'' +
                        '}';
            }
        }
    }
}
