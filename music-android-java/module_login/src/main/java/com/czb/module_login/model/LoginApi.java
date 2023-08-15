package com.czb.module_login.model;

import com.czb.module_login.model.bean.LoginBean;
import com.czb.module_login.model.bean.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginApi {

    @POST("/user/login/{captcha}/{captcha_key}")
    Call<LoginBean> login(@Path("captcha")String captcha ,
                          @Path("captcha_key")String captchaKey,
                          @Body User data);


}
