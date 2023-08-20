package com.czb.module_search.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.common.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MusicInfoByNameBean implements Serializable {

    /**
     * success : true
     * code : 20000
     * message : 获取成功
     * data : {"list":[{"id":"1137786643277676544","singer_id":"1138081182076895232","name":"<span style=\"color:blue;\">火星<\/span>人来过-薛之谦","pic_id":"1691379429028_1138073341643456512.jpeg","lyric":"[00:00.000] 作词 : 薛之谦\n[00:01.000] 作曲 : 韩星洲\n[00:12.210]我在听新闻里面说 他们曾来过\n[00:19.020]火星人的心脏靠左\n[00:23.170]我们也曾听大人说 他没有管我\n[00:30.270]他们一定看见什么过\n[00:33.820]森林不在 动物减半\n[00:36.510]人们拍照 留念那些 飞机残骸\n[00:39.710]妻离子散 空袭灾难\n[00:42.100]那些不敢 谈政治的 都去避难\n[00:45.090]被遗弃的小孩 搀扶那颗炸弹\n[00:47.560]他父母被人用枪指着头要答案\n[00:50.380]若要停战 先要谈判\n[00:52.710]这一片片 荒凉土地幕后到底谁管\n[00:55.460]如果钢铁都燃起火\n[00:59.750]看城市多折磨\n[01:02.070]请你配合我 一起难过\n[01:06.510]假如猿人没点起火\n[01:10.590]我们回到那生活\n[01:13.390]你是否救得回刚离群的我\n[01:18.070]火星人来过\n[01:20.820]火星人来过\n[01:23.620]火星人救我\n[01:29.230]火星人救我\n[01:31.980]火星人救我\n[01:34.740]火星人爱我\n[01:41.600]其实我们也忏悔过借口都好说\n[01:48.420]可以怪我心脏偏左\n[01:52.500]有些领袖话音刚落也会很难过\n[01:59.580]因为会议迟迟通不过\n[02:03.360]舍利不在象牙贩卖\n[02:05.720]人们认为贫穷可以卖掉小孩\n[02:08.550]钻石太窄富人不爱\n[02:11.380]还在楼顶挥霍那些仿真钱财\n[02:14.220]反正这没战乱也没有什么天灾\n[02:16.780]我管他谁让瘟疫艾滋继续泛滥\n[02:19.800]这种心态还能表态\n[02:22.100]毕竟这是个打字不用负责任的年代\n[02:25.080]如果欲望都燃起火\n[02:28.820]怎么自私怎么活\n[02:31.230]请你举起手假装难过\n[02:35.840]假如猿人没点起火\n[02:39.790]我们回到那生活\n[02:42.860]你是否劝得住开第一枪的我\n[02:48.600]假如有第三次战火\n[02:52.530]让地核接近我\n[02:55.440]你别难过请抱紧我\n[02:59.460]如果你不能说服我\n[03:03.630]就请你瞄准我\n[03:06.520]你听地球刚哭过\n[03:11.150]地球刚哭过\n[03:13.840]地球有话说\n[03:16.620]地球好脆弱\n[03:22.260]火星人救我\n[03:25.020]火星人救我\n[03:27.810]火星人来过...\n[03:30.855] 编曲 : 韩星洲\n[03:33.900] 制作人 : 韩星洲\n[03:36.945] 混音 : 赵靖\n","url":"1691311074811_1137786643277676544.mp3","state":"3","path":null,"file_high_url":null,"singer_name":"薛之谦","count":"0","duration":"216"},{"id":"1137874427266990080","singer_id":"1139625013528231936","name":"最<span style=\"color:blue;\">伟大<\/span>的作品-周杰伦","pic_id":null,"lyric":null,"url":"1691332004145_1137874427266990080.mp3","state":"3","path":null,"file_high_url":"1691379667734_1138074342848987136.flac","singer_name":"周杰伦","count":"0","duration":"244"}],"currentPage":1,"totalPage":1}
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
        return "MusicInfoByNameBean{" +
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
         * list : [{"id":"1137786643277676544","singer_id":"1138081182076895232","name":"<span style=\"color:blue;\">火星<\/span>人来过-薛之谦","pic_id":"1691379429028_1138073341643456512.jpeg","lyric":"[00:00.000] 作词 : 薛之谦\n[00:01.000] 作曲 : 韩星洲\n[00:12.210]我在听新闻里面说 他们曾来过\n[00:19.020]火星人的心脏靠左\n[00:23.170]我们也曾听大人说 他没有管我\n[00:30.270]他们一定看见什么过\n[00:33.820]森林不在 动物减半\n[00:36.510]人们拍照 留念那些 飞机残骸\n[00:39.710]妻离子散 空袭灾难\n[00:42.100]那些不敢 谈政治的 都去避难\n[00:45.090]被遗弃的小孩 搀扶那颗炸弹\n[00:47.560]他父母被人用枪指着头要答案\n[00:50.380]若要停战 先要谈判\n[00:52.710]这一片片 荒凉土地幕后到底谁管\n[00:55.460]如果钢铁都燃起火\n[00:59.750]看城市多折磨\n[01:02.070]请你配合我 一起难过\n[01:06.510]假如猿人没点起火\n[01:10.590]我们回到那生活\n[01:13.390]你是否救得回刚离群的我\n[01:18.070]火星人来过\n[01:20.820]火星人来过\n[01:23.620]火星人救我\n[01:29.230]火星人救我\n[01:31.980]火星人救我\n[01:34.740]火星人爱我\n[01:41.600]其实我们也忏悔过借口都好说\n[01:48.420]可以怪我心脏偏左\n[01:52.500]有些领袖话音刚落也会很难过\n[01:59.580]因为会议迟迟通不过\n[02:03.360]舍利不在象牙贩卖\n[02:05.720]人们认为贫穷可以卖掉小孩\n[02:08.550]钻石太窄富人不爱\n[02:11.380]还在楼顶挥霍那些仿真钱财\n[02:14.220]反正这没战乱也没有什么天灾\n[02:16.780]我管他谁让瘟疫艾滋继续泛滥\n[02:19.800]这种心态还能表态\n[02:22.100]毕竟这是个打字不用负责任的年代\n[02:25.080]如果欲望都燃起火\n[02:28.820]怎么自私怎么活\n[02:31.230]请你举起手假装难过\n[02:35.840]假如猿人没点起火\n[02:39.790]我们回到那生活\n[02:42.860]你是否劝得住开第一枪的我\n[02:48.600]假如有第三次战火\n[02:52.530]让地核接近我\n[02:55.440]你别难过请抱紧我\n[02:59.460]如果你不能说服我\n[03:03.630]就请你瞄准我\n[03:06.520]你听地球刚哭过\n[03:11.150]地球刚哭过\n[03:13.840]地球有话说\n[03:16.620]地球好脆弱\n[03:22.260]火星人救我\n[03:25.020]火星人救我\n[03:27.810]火星人来过...\n[03:30.855] 编曲 : 韩星洲\n[03:33.900] 制作人 : 韩星洲\n[03:36.945] 混音 : 赵靖\n","url":"1691311074811_1137786643277676544.mp3","state":"3","path":null,"file_high_url":null,"singer_name":"薛之谦","count":"0","duration":"216"},{"id":"1137874427266990080","singer_id":"1139625013528231936","name":"最<span style=\"color:blue;\">伟大<\/span>的作品-周杰伦","pic_id":null,"lyric":null,"url":"1691332004145_1137874427266990080.mp3","state":"3","path":null,"file_high_url":"1691379667734_1138074342848987136.flac","singer_name":"周杰伦","count":"0","duration":"244"}]
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
             * name : <span style="color:blue;">火星</span>人来过-薛之谦
             * pic_id : 1691379429028_1138073341643456512.jpeg
             * lyric : [00:00.000] 作词 : 薛之谦
             [00:01.000] 作曲 : 韩星洲
             [00:12.210]我在听新闻里面说 他们曾来过
             [00:19.020]火星人的心脏靠左
             [00:23.170]我们也曾听大人说 他没有管我
             [00:30.270]他们一定看见什么过
             [00:33.820]森林不在 动物减半
             [00:36.510]人们拍照 留念那些 飞机残骸
             [00:39.710]妻离子散 空袭灾难
             [00:42.100]那些不敢 谈政治的 都去避难
             [00:45.090]被遗弃的小孩 搀扶那颗炸弹
             [00:47.560]他父母被人用枪指着头要答案
             [00:50.380]若要停战 先要谈判
             [00:52.710]这一片片 荒凉土地幕后到底谁管
             [00:55.460]如果钢铁都燃起火
             [00:59.750]看城市多折磨
             [01:02.070]请你配合我 一起难过
             [01:06.510]假如猿人没点起火
             [01:10.590]我们回到那生活
             [01:13.390]你是否救得回刚离群的我
             [01:18.070]火星人来过
             [01:20.820]火星人来过
             [01:23.620]火星人救我
             [01:29.230]火星人救我
             [01:31.980]火星人救我
             [01:34.740]火星人爱我
             [01:41.600]其实我们也忏悔过借口都好说
             [01:48.420]可以怪我心脏偏左
             [01:52.500]有些领袖话音刚落也会很难过
             [01:59.580]因为会议迟迟通不过
             [02:03.360]舍利不在象牙贩卖
             [02:05.720]人们认为贫穷可以卖掉小孩
             [02:08.550]钻石太窄富人不爱
             [02:11.380]还在楼顶挥霍那些仿真钱财
             [02:14.220]反正这没战乱也没有什么天灾
             [02:16.780]我管他谁让瘟疫艾滋继续泛滥
             [02:19.800]这种心态还能表态
             [02:22.100]毕竟这是个打字不用负责任的年代
             [02:25.080]如果欲望都燃起火
             [02:28.820]怎么自私怎么活
             [02:31.230]请你举起手假装难过
             [02:35.840]假如猿人没点起火
             [02:39.790]我们回到那生活
             [02:42.860]你是否劝得住开第一枪的我
             [02:48.600]假如有第三次战火
             [02:52.530]让地核接近我
             [02:55.440]你别难过请抱紧我
             [02:59.460]如果你不能说服我
             [03:03.630]就请你瞄准我
             [03:06.520]你听地球刚哭过
             [03:11.150]地球刚哭过
             [03:13.840]地球有话说
             [03:16.620]地球好脆弱
             [03:22.260]火星人救我
             [03:25.020]火星人救我
             [03:27.810]火星人来过...
             [03:30.855] 编曲 : 韩星洲
             [03:33.900] 制作人 : 韩星洲
             [03:36.945] 混音 : 赵靖
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
            private String path;
            @SerializedName("file_high_url")
            private String fileHighUrl;
            @SerializedName("singer_name")
            private String singerName;
            @SerializedName("count")
            private String count;
            @SerializedName("duration")
            private String duration;

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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getFileHighUrl() {
                return fileHighUrl;
            }

            public void setFileHighUrl(String fileHighUrl) {
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
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", singerId='" + singerId + '\'' +
                        ", name='" + name + '\'' +
                        ", picId='" + picId + '\'' +
                        ", lyric='" + lyric + '\'' +
                        ", url='" + url + '\'' +
                        ", state='" + state + '\'' +
                        ", path='" + path + '\'' +
                        ", fileHighUrl='" + fileHighUrl + '\'' +
                        ", singerName='" + singerName + '\'' +
                        ", count='" + count + '\'' +
                        ", duration='" + duration + '\'' +
                        '}';
            }

            @Override
            public int getItemType() {
                return Constants.SearchType.SEARCH_BY_NAME;
            }
        }
    }
}
