package com.czb.module_home.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MusicianBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取音乐人列表成功
     * data : {"hasMore":false,"current":1,"list":[{"id":"1139633669795741696","name":"郭聪明","sex":1,"picId":"1691751357851_1139633324197675008.jpeg","location":null,"introduction":"","createTime":"2023-08-11 18:57:20","state":"1"},{"id":"1139630706033426432","name":"郁可唯","sex":0,"picId":"1691750642035_1139630321843568640.jpeg","location":null,"introduction":"Yisa","createTime":"2023-08-11 18:45:34","state":"1"},{"id":"1139628192840024064","name":"许嵩","sex":1,"picId":"1691750075313_1139627944839217152.jpeg","location":null,"introduction":"Vae","createTime":"2023-08-11 18:35:34","state":"1"},{"id":"1139627669973893120","name":"陈奕迅","sex":1,"picId":"1691749961282_1139627466558537728.jpeg","location":null,"introduction":"Eason Chan","createTime":"2023-08-11 18:33:30","state":"1"},{"id":"1139625013528231936","name":"周杰伦","sex":1,"picId":"1691749251145_1139624488036466688.jpeg","location":null,"introduction":"Jay Chou","createTime":"2023-08-11 18:22:56","state":"1"},{"id":"1138081182076895232","name":"薛之谦","sex":1,"picId":"1691380931915_1138079645208412160.png","location":null,"introduction":"薛之谦(Joker Xue)","createTime":"2023-08-07 12:08:18","state":"1"}],"maxPage":1}
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
        return "SingerBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


    public static class DataBean implements Serializable {
        /**
         * hasMore : false
         * current : 1
         * list : [{"id":"1139633669795741696","name":"郭聪明","sex":1,"picId":"1691751357851_1139633324197675008.jpeg","location":null,"introduction":"","createTime":"2023-08-11 18:57:20","state":"1"},{"id":"1139630706033426432","name":"郁可唯","sex":0,"picId":"1691750642035_1139630321843568640.jpeg","location":null,"introduction":"Yisa","createTime":"2023-08-11 18:45:34","state":"1"},{"id":"1139628192840024064","name":"许嵩","sex":1,"picId":"1691750075313_1139627944839217152.jpeg","location":null,"introduction":"Vae","createTime":"2023-08-11 18:35:34","state":"1"},{"id":"1139627669973893120","name":"陈奕迅","sex":1,"picId":"1691749961282_1139627466558537728.jpeg","location":null,"introduction":"Eason Chan","createTime":"2023-08-11 18:33:30","state":"1"},{"id":"1139625013528231936","name":"周杰伦","sex":1,"picId":"1691749251145_1139624488036466688.jpeg","location":null,"introduction":"Jay Chou","createTime":"2023-08-11 18:22:56","state":"1"},{"id":"1138081182076895232","name":"薛之谦","sex":1,"picId":"1691380931915_1138079645208412160.png","location":null,"introduction":"薛之谦(Joker Xue)","createTime":"2023-08-07 12:08:18","state":"1"}]
         * maxPage : 1
         */

        @SerializedName("hasMore")
        private Boolean hasMore;
        @SerializedName("current")
        private Integer current;
        @SerializedName("maxPage")
        private Integer maxPage;
        @SerializedName("list")
        private List<ListBean> list;

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Integer getCurrent() {
            return current;
        }

        public void setCurrent(Integer current) {
            this.current = current;
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
                    "hasMore=" + hasMore +
                    ", current=" + current +
                    ", maxPage=" + maxPage +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean implements Serializable , Parcelable {
            /**
             * id : 1139633669795741696
             * name : 郭聪明
             * sex : 1
             * picId : 1691751357851_1139633324197675008.jpeg
             * location : null
             * introduction :
             * createTime : 2023-08-11 18:57:20
             * state : 1
             */

            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("sex")
            private Integer sex;
            @SerializedName("picId")
            private String picId;
            @SerializedName("location")
            private String location;
            @SerializedName("introduction")
            private String introduction;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("state")
            private String state;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSex() {
                return sex;
            }

            public void setSex(Integer sex) {
                this.sex = sex;
            }

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", sex=" + sex +
                        ", picId='" + picId + '\'' +
                        ", location=" + location +
                        ", introduction='" + introduction + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", state='" + state + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeValue(this.sex);
                dest.writeString(this.picId);
                dest.writeString(this.location);
                dest.writeString(this.introduction);
                dest.writeString(this.createTime);
                dest.writeString(this.state);
            }

            public void readFromParcel(Parcel source) {
                this.id = source.readString();
                this.name = source.readString();
                this.sex = (Integer) source.readValue(Integer.class.getClassLoader());
                this.picId = source.readString();
                this.location = source.readString();
                this.introduction = source.readString();
                this.createTime = source.readString();
                this.state = source.readString();
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.sex = (Integer) in.readValue(Integer.class.getClassLoader());
                this.picId = in.readString();
                this.location = in.readString();
                this.introduction = in.readString();
                this.createTime = in.readString();
                this.state = in.readString();
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
