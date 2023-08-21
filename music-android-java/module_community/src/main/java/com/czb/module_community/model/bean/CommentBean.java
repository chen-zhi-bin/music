package com.czb.module_community.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable {
    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"list":[{"id":"1139227408403005440","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:00","updateTime":"2023-08-10 16:03:00","state":"1","parentId":null,"parentUserId":null,"parentUserName":null,"parentUserAvatar":null,"subComments":{"hasMore":false,"subComment":[{"id":"1139232823626956800","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-10 16:24:31","updateTime":"2023-08-10 16:24:31","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"},{"id":"1139227650225602560","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:58","updateTime":"2023-08-10 16:03:58","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"}],"totalCount":2}}],"currentPage":1,"maxPage":1}
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
        return "CommentBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * list : [{"id":"1139227408403005440","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:00","updateTime":"2023-08-10 16:03:00","state":"1","parentId":null,"parentUserId":null,"parentUserName":null,"parentUserAvatar":null,"subComments":{"hasMore":false,"subComment":[{"id":"1139232823626956800","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-10 16:24:31","updateTime":"2023-08-10 16:24:31","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"},{"id":"1139227650225602560","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:58","updateTime":"2023-08-10 16:03:58","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"}],"totalCount":2}}]
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

        public static class ListBean implements Serializable, Parcelable {
            /**
             * id : 1139227408403005440
             * avatar : default_avatar.png
             * userName : super_admin
             * userId : 1136809343182700544
             * songId : 1137786643277676544
             * comment : 写实一般的音乐
             * createTime : 2023-08-10 16:03:00
             * updateTime : 2023-08-10 16:03:00
             * state : 1
             * parentId : null
             * parentUserId : null
             * parentUserName : null
             * parentUserAvatar : null
             * subComments : {"hasMore":false,"subComment":[{"id":"1139232823626956800","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-10 16:24:31","updateTime":"2023-08-10 16:24:31","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"},{"id":"1139227650225602560","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:58","updateTime":"2023-08-10 16:03:58","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"}],"totalCount":2}
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
            @SerializedName("parentUserAvatar")
            private String parentUserAvatar;
            @SerializedName("subComments")
            private SubCommentsBean subComments;

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

            public Object getParentId() {
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

            public String getParentUserAvatar() {
                return parentUserAvatar;
            }

            public void setParentUserAvatar(String parentUserAvatar) {
                this.parentUserAvatar = parentUserAvatar;
            }

            public SubCommentsBean getSubComments() {
                return subComments;
            }

            public void setSubComments(SubCommentsBean subComments) {
                this.subComments = subComments;
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
                        ", parentId=" + parentId +
                        ", parentUserId=" + parentUserId +
                        ", parentUserName=" + parentUserName +
                        ", parentUserAvatar=" + parentUserAvatar +
                        ", subComments=" + subComments +
                        '}';
            }


            public static class SubCommentsBean implements Serializable {
                /**
                 * hasMore : false
                 * subComment : [{"id":"1139232823626956800","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"子评论测试","createTime":"2023-08-10 16:24:31","updateTime":"2023-08-10 16:24:31","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"},{"id":"1139227650225602560","avatar":"default_avatar.png","userName":"super_admin","userId":"1136809343182700544","songId":"1137786643277676544","comment":"写实一般的音乐","createTime":"2023-08-10 16:03:58","updateTime":"2023-08-10 16:03:58","state":"1","parentId":"1139227408403005440","parentUserId":"1136809343182700544","parentUserName":"super_admin"}]
                 * totalCount : 2
                 */

                @SerializedName("hasMore")
                private Boolean hasMore;
                @SerializedName("totalCount")
                private Integer totalCount;
                @SerializedName("subComment")
                private List<SubCommentBean> subComment;

                public Boolean getHasMore() {
                    return hasMore;
                }

                public void setHasMore(Boolean hasMore) {
                    this.hasMore = hasMore;
                }

                public Integer getTotalCount() {
                    return totalCount;
                }

                public void setTotalCount(Integer totalCount) {
                    this.totalCount = totalCount;
                }

                public List<SubCommentBean> getSubComment() {
                    return subComment;
                }

                public void setSubComment(List<SubCommentBean> subComment) {
                    this.subComment = subComment;
                }

                @Override
                public String toString() {
                    return "SubCommentsBean{" +
                            "hasMore=" + hasMore +
                            ", totalCount=" + totalCount +
                            ", subComment=" + subComment +
                            '}';
                }


                public static class SubCommentBean implements Serializable {
                    /**
                     * id : 1139232823626956800
                     * avatar : default_avatar.png
                     * userName : super_admin
                     * userId : 1136809343182700544
                     * songId : 1137786643277676544
                     * comment : 子评论测试
                     * createTime : 2023-08-10 16:24:31
                     * updateTime : 2023-08-10 16:24:31
                     * state : 1
                     * parentId : 1139227408403005440
                     * parentUserId : 1136809343182700544
                     * parentUserName : super_admin
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
                        return "SubCommentBean{" +
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.avatar);
                dest.writeString(this.userName);
                dest.writeString(this.userId);
                dest.writeString(this.songId);
                dest.writeString(this.comment);
                dest.writeString(this.createTime);
                dest.writeString(this.updateTime);
                dest.writeString(this.state);
                dest.writeString(this.parentId);
                dest.writeString(this.parentUserId);
                dest.writeString(this.parentUserName);
                dest.writeString(this.parentUserAvatar);
                dest.writeSerializable(this.subComments);
            }

            public void readFromParcel(Parcel source) {
                this.id = source.readString();
                this.avatar = source.readString();
                this.userName = source.readString();
                this.userId = source.readString();
                this.songId = source.readString();
                this.comment = source.readString();
                this.createTime = source.readString();
                this.updateTime = source.readString();
                this.state = source.readString();
                this.parentId = source.readString();
                this.parentUserId = source.readString();
                this.parentUserName = source.readString();
                this.parentUserAvatar = source.readString();
                this.subComments = (SubCommentsBean) source.readSerializable();
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.avatar = in.readString();
                this.userName = in.readString();
                this.userId = in.readString();
                this.songId = in.readString();
                this.comment = in.readString();
                this.createTime = in.readString();
                this.updateTime = in.readString();
                this.state = in.readString();
                this.parentId = in.readString();
                this.parentUserId = in.readString();
                this.parentUserName = in.readString();
                this.parentUserAvatar = in.readString();
                this.subComments = (SubCommentsBean) in.readSerializable();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
