package com.chen.module_login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chen.module_base.common.constant.SpKey
import com.chen.module_base.ktx.initiateRequest
import com.chen.module_base.mvvm.vm.BaseViewModel
import com.chen.module_base.net.BaseResponse
import com.chen.module_base.net.State
import com.chen.module_base.net.StateType
import com.chen.module_base.utils.LogUtils
import com.chen.module_base.utils.SpUtils
import com.chen.module_login.bean.LoginBean
import com.chen.module_login.bean.LoginInBean
import com.chen.module_login.bean.ResultData
import kotlinx.coroutines.launch

class LoginViewModel :BaseViewModel(){
    private val repository by lazy {
        LoginRepo(loadState)
    }

//    fun login(captche:String,key:String,query:LoginInBean):MutableLiveData<BaseResponse<String?>>{
//        var response : MutableLiveData<BaseResponse<String?>> = MutableLiveData()
//        viewModelScope.launch {
//            val res = repository.login(captche,key, query)
//            LogUtils.d("login",res.toString())
//        }
//        return response
//    }

    fun login(captcha:String,key:String,query:LoginInBean):MutableLiveData<BaseResponse<String?>>{
        var response : MutableLiveData<BaseResponse<String?>> = MutableLiveData()

        initiateRequest({
//            val res = repository.login(captcha,key, query)
//            LogUtils.d("login",res.toString())
//            if (res.success){
////                loadState.value = State(StateType.SUCCESS, LoginApi.LOGIN_URL)
////                SpUtils.putString(SpKey.USER_ID, res.id)
////                SpUtils.putString(SpKey.USER_AVATAR, if(TextUtils.isEmpty(token.avatar))"" else token.avatar!! )
////                SpUtils.putString(SpKey.USER_NICKNAME, if(TextUtils.isEmpty(token.nickname))"" else token.nickname!! )
//            }
//            response.value = res
        },loadState,LoginApi.LOGIN_URL)
        return response
    }
}