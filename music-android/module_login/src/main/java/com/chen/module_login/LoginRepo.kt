package com.chen.module_login

import androidx.lifecycle.MutableLiveData
import com.chen.module_base.mvvm.m.BaseRepository
import com.chen.module_base.net.BaseResponse
import com.chen.module_base.net.RetrofitFactory
import com.chen.module_base.net.State
import com.chen.module_login.bean.LoginInBean

class LoginRepo(private val loadState:MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(LoginApi::class.java, LoginApi.BASE_URL)
    }

    suspend fun login(captcha:String,key:String,query: LoginInBean):BaseResponse<String?> {
        return apiService.login(captcha,key, query)
    }
//    suspend fun login(captcha:String,key:String,query: LoginInBean)=
//        RetrofitClient.apiService.login(captcha,key,query).apiData()
}