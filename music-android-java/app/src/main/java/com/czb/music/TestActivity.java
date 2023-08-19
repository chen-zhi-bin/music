package com.czb.music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.czb.module_base.common.Constants;
import com.google.gson.annotations.SerializedName;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;


import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class TestActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private LoginApi api;

    private String testS="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        PlayerControl control = StarrySky.with();
        control.playMusicByUrl(Constants.BASE_URL+"/music/1691750512218_1139629777351606272.mp3");
//        control.playMusicByUrl(Constants.BASE_URL+"/music/1691750576251_1139630045925474304.flac");

        this.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control.isPlaying()) {
                    control.pauseMusic();
                }else{
                    control.pauseMusic();
                }
            }
        });

//        api = retrofit.create(LoginApi.class);
//
//        String se;
//
//        Call<Bean> task = api.login("rzu3n", "12345678901234567", new LoginInBean("super_admin", "123456"));
//        task.enqueue(new Callback<Bean>() {
//            @Override
//            public void onResponse(Call<Bean> call, Response<Bean> response) {
//                Log.d("tet","resasdasd  ==>"+response.body());
//                Toast.makeText(TestActivity.this,"res ="+response.headers().get("Set-Cookie"),Toast.LENGTH_LONG).show();
//                if (response.body().success){
//                    String s = response.headers().get("Set-Cookie");
//                    String[] split = s.split(";");
//                    String[] split1 = split[0].split("=");
//                    Log.d("test","s == "+s);
//                    testS=split1[1];
//                    Log.d("test","s == "+testS);
//                }
//            }
//            @Override
//            public void onFailure(Call<Bean> call, Throwable t) {}
//        });
//        this.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getCollection(testS);
//                getCollection("0715aacf0d01f8a6c70e109e6af0adf2");
//
//            }
//        });

    }

    private void getCollection(String s) {
        Call<CollectionBean> collectionBeanCall = api.collectionList(s);
        collectionBeanCall.enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                CollectionBean body = response.body();
                Log.d("test", response.message());
                Log.d("test", response.code()+"");
                Log.d("test", body.toString());
//                Toast.makeText(TestActivity.this,"res ="+ body.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CollectionBean> call, Throwable t) {

            }
        });
    }


    public interface LoginApi {
        @POST("/user/login/{captcha}/{captcha_key}")
        Call<Bean> login(@Path("captcha")String captcha ,
                         @Path("captcha_key")String captchaKey,
                         @Body LoginInBean data);

        @GET("/collection/list/1")
        Call<CollectionBean> collectionList(@Header("cookie_token")String s);


    }

    public class LoginInBean{
        String userName;
        String password;

        public LoginInBean(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginInBean{" +
                    "userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
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
    }

    public class Bean{
        String message;
        boolean success;
        int code;
        Object data;

        @Override
        public String toString() {
            return "Bean{" +
                    "message='" + message + '\'' +
                    ", success=" + success +
                    ", code=" + code +
                    ", data=" + data +
                    '}';
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }


    public class CollectionBean implements Serializable {

        /**
         * success : true
         * code : 20000
         * message : 获取成功
         * data : {"hasMore":false,"list":[{"musicId":"1137786643277676544","name":"火星人来过-薛之谦","singerName":"薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null},{"musicId":"1137874427266990080","name":"最伟大的作品-周杰伦","singerName":"周杰伦","url":"1691332004145_1137874427266990080.mp3","fileHighUrl":"1691379667734_1138074342848987136.flac"}],"currentPage":1,"totalPage":1}
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
            return "CollectionBean{" +
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

        public  class DataBean implements Serializable {
            /**
             * hasMore : false
             * list : [{"musicId":"1137786643277676544","name":"火星人来过-薛之谦","singerName":"薛之谦","url":"1691311074811_1137786643277676544.mp3","fileHighUrl":null},{"musicId":"1137874427266990080","name":"最伟大的作品-周杰伦","singerName":"周杰伦","url":"1691332004145_1137874427266990080.mp3","fileHighUrl":"1691379667734_1138074342848987136.flac"}]
             * currentPage : 1
             * totalPage : 1
             */

            @SerializedName("hasMore")
            private Boolean hasMore;
            @SerializedName("currentPage")
            private Integer currentPage;
            @SerializedName("totalPage")
            private Integer totalPage;
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
                        "hasMore=" + hasMore +
                        ", currentPage=" + currentPage +
                        ", totalPage=" + totalPage +
                        ", list=" + list +
                        '}';
            }

            public  class ListBean implements Serializable {
                /**
                 * musicId : 1137786643277676544
                 * name : 火星人来过-薛之谦
                 * singerName : 薛之谦
                 * url : 1691311074811_1137786643277676544.mp3
                 * fileHighUrl : null
                 */

                @SerializedName("musicId")
                private String musicId;
                @SerializedName("name")
                private String name;
                @SerializedName("singerName")
                private String singerName;
                @SerializedName("url")
                private String url;
                @SerializedName("fileHighUrl")
                private Object fileHighUrl;

                public String getMusicId() {
                    return musicId;
                }

                public void setMusicId(String musicId) {
                    this.musicId = musicId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSingerName() {
                    return singerName;
                }

                public void setSingerName(String singerName) {
                    this.singerName = singerName;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public Object getFileHighUrl() {
                    return fileHighUrl;
                }

                public void setFileHighUrl(Object fileHighUrl) {
                    this.fileHighUrl = fileHighUrl;
                }

                @Override
                public String toString() {
                    return "ListBean{" +
                            "musicId='" + musicId + '\'' +
                            ", name='" + name + '\'' +
                            ", singerName='" + singerName + '\'' +
                            ", url='" + url + '\'' +
                            ", fileHighUrl=" + fileHighUrl +
                            '}';
                }
            }
        }
    }
}