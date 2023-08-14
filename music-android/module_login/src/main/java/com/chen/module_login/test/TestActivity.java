package com.chen.module_login.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chen.module_base.utils.LogUtils;
import com.chen.module_login.R;
import com.chen.module_login.bean.LoginInBean;
import com.google.gson.annotations.SerializedName;

import org.checkerframework.checker.units.qual.C;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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

        api = retrofit.create(LoginApi.class);

        String se;

        Call<Bean> task = api.login("wuesz", "12345678901234567", new LoginInBean("super_admin", "123456"));
        task.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Log.d("tet","resasdasd  ==>"+response.body());
//                Toast.makeText(TestActivity.this,"res ="+response.body().toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(TestActivity.this,"res ="+response.headers().get("Set-Cookie"),Toast.LENGTH_LONG).show();
                String s = response.headers().get("Set-Cookie");
                String[] split = s.split(";");
                String[] split1 = split[0].split("=");
                Log.d("test","s == "+s);
                testS=split1[1];
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {

            }
        });

        this.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCollection(testS);
            }
        });

    }

    private void getCollection(String s) {
        Call<CollectionBean> collectionBeanCall = api.collectionList(s);
        collectionBeanCall.enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                Toast.makeText(TestActivity.this,"res ="+response.body().toString(),Toast.LENGTH_LONG).show();
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

        @POST("/collection/list/1")
        Call<CollectionBean> collectionList(@Header("Set-Cookie")String s);


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