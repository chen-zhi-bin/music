package com.chen.module_login

import com.chen.module_base.net.BaseResponse
import com.chen.module_login.bean.LoginBean
import com.chen.module_login.bean.LoginInBean
import com.chen.module_login.bean.ResultData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApi {
    companion object{
        const val BASE_URL = "http://10.0.2.2:8090"
        // 登录
        const val LOGIN_URL = "${BASE_URL}/user/login"
    }

    @POST("/user/login/{captcha}/{captcha_key}")
    suspend fun login(
        @Path("captcha") captcha: String,
        @Path("captcha_key") captchaKey: String,
        @Body query: LoginInBean
    ): BaseResponse<String?>
}