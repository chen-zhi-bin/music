package com.czb.module_home.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BannerBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"list":[{"id":"1138957432978931712","title":"艺术","order":0,"state":"1","targetUrl":"https://cn.bing.com/images/search?view=detailV2&ccid=ZFYdY8hW&id=16742E107068CA0A81EAFE6A8D58440E25A18A0B&thid=OIP.ZFYdY8hWCj-SLHhy9DlnnQHaFj&mediaurl=https%3a%2f%2fimg.zcool.cn%2fcommunity%2f01604f5d844571a8012060be54119b.jpg%402o.jpg&exph=1920&expw=2560&q=%e8%89%ba%e6%9c%af&simid=608015761603256886&FORM=IRPRST&ck=DEB1CD5282907E03375747E9AFA75532&selectedIndex=3&ajaxhist=0&ajaxserp=0","imageUrl":"http://localhost:8090/admin/image/1691590073659_1138956849261838336.jpeg","createTime":"2023-08-09 22:10:13","updateTime":"2023-08-09 22:10:13","userId":"1136809343182700544"},{"id":"1138955221691531264","title":"绚丽的色彩","order":0,"state":"1","targetUrl":"https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%89%BA%E6%9C%AF&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=3965669525,3039145741&os=3801153853,2016899609&simid=4197468115,582115731&pn=2&rn=1&di=46137345&ln=917&fr=&fmq=1691582902461_R&fm=result&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=11&spn=0&pi=0&gsm=1e&objurl=https%3A%2F%2Fww1.sinaimg.cn%2Fmw690%2F7ff6fc1fly1hgpkmje8t6j20p00itq7m.jpg&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&dyTabStr=MCw2LDEsNCw1LDMsMiw4LDcsOQ%3D%3D&ctd=1691582908268^3_1465X737%1","imageUrl":"http://localhost:8090/image/1691589561711_1138954702000488448.jpeg","createTime":"2023-08-09 22:01:26","updateTime":"2023-08-09 22:01:26","userId":"1136809343182700544"}]}
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
        return "BannerBean{" +
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
        @SerializedName("list")
        private List<ListBean> list;

        @Override
        public String toString() {
            return "DataBean{" +
                    "list=" + list +
                    '}';
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * id : 1138957432978931712
             * title : 艺术
             * order : 0
             * state : 1
             * targetUrl : https://cn.bing.com/images/search?view=detailV2&ccid=ZFYdY8hW&id=16742E107068CA0A81EAFE6A8D58440E25A18A0B&thid=OIP.ZFYdY8hWCj-SLHhy9DlnnQHaFj&mediaurl=https%3a%2f%2fimg.zcool.cn%2fcommunity%2f01604f5d844571a8012060be54119b.jpg%402o.jpg&exph=1920&expw=2560&q=%e8%89%ba%e6%9c%af&simid=608015761603256886&FORM=IRPRST&ck=DEB1CD5282907E03375747E9AFA75532&selectedIndex=3&ajaxhist=0&ajaxserp=0
             * imageUrl : /image/1691590073659_1138956849261838336.jpeg
             * createTime : 2023-08-09 22:10:13
             * updateTime : 2023-08-09 22:10:13
             * userId : 1136809343182700544
             */

            @SerializedName("id")
            private String id;
            @SerializedName("title")
            private String title;
            @SerializedName("order")
            private Integer order;
            @SerializedName("state")
            private String state;
            @SerializedName("targetUrl")
            private String targetUrl;
            @SerializedName("imageUrl")
            private String imageUrl;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("updateTime")
            private String updateTime;
            @SerializedName("userId")
            private String userId;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getOrder() {
                return order;
            }

            public void setOrder(Integer order) {
                this.order = order;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTargetUrl() {
                return targetUrl;
            }

            public void setTargetUrl(String targetUrl) {
                this.targetUrl = targetUrl;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", order=" + order +
                        ", state='" + state + '\'' +
                        ", targetUrl='" + targetUrl + '\'' +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", updateTime='" + updateTime + '\'' +
                        ", userId='" + userId + '\'' +
                        '}';
            }
        }
    }
}
