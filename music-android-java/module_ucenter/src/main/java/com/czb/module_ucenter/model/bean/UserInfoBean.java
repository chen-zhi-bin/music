package com.czb.module_ucenter.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserInfoBean implements Serializable {
    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"info":{"id":"1137189005566148608","userName":"user","password":"","roleId":"10","avatar":"1691288795217_1137693195887443968.jpeg","email":"","sign":"用户","state":"1","regIp":"","deleted":"0","loginIp":"","createTime":"2023-08-05 01:03:07","updateTime":"2023-08-05 21:45:38"}}
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
        return "UserInfo{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * info : {"id":"1137189005566148608","userName":"user","password":"","roleId":"10","avatar":"1691288795217_1137693195887443968.jpeg","email":"","sign":"用户","state":"1","regIp":"","deleted":"0","loginIp":"","createTime":"2023-08-05 01:03:07","updateTime":"2023-08-05 21:45:38"}
         */

        @SerializedName("info")
        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "info=" + info +
                    '}';
        }

        public static class InfoBean implements Serializable {
            /**
             * id : 1137189005566148608
             * userName : user
             * password :
             * roleId : 10
             * avatar : 1691288795217_1137693195887443968.jpeg
             * email :
             * sign : 用户
             * state : 1
             * regIp :
             * deleted : 0
             * loginIp :
             * createTime : 2023-08-05 01:03:07
             * updateTime : 2023-08-05 21:45:38
             */

            @SerializedName("id")
            private String id;
            @SerializedName("userName")
            private String userName;
            @SerializedName("password")
            private String password;
            @SerializedName("roleId")
            private String roleId;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("email")
            private String email;
            @SerializedName("sign")
            private String sign;
            @SerializedName("state")
            private String state;
            @SerializedName("regIp")
            private String regIp;
            @SerializedName("deleted")
            private String deleted;
            @SerializedName("loginIp")
            private String loginIp;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("updateTime")
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRoleId() {
                return roleId;
            }

            public void setRoleId(String roleId) {
                this.roleId = roleId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getRegIp() {
                return regIp;
            }

            public void setRegIp(String regIp) {
                this.regIp = regIp;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getLoginIp() {
                return loginIp;
            }

            public void setLoginIp(String loginIp) {
                this.loginIp = loginIp;
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

            @Override
            public String toString() {
                return "InfoBean{" +
                        "id='" + id + '\'' +
                        ", userName='" + userName + '\'' +
                        ", password='" + password + '\'' +
                        ", roleId='" + roleId + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", email='" + email + '\'' +
                        ", sign='" + sign + '\'' +
                        ", state='" + state + '\'' +
                        ", regIp='" + regIp + '\'' +
                        ", deleted='" + deleted + '\'' +
                        ", loginIp='" + loginIp + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", updateTime='" + updateTime + '\'' +
                        '}';
            }
        }
    }
}
