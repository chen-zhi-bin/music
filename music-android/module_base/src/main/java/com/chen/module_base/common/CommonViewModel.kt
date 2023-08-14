package com.chen.module_base.common

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.chen.module_base.mvvm.vm.BaseViewModel
import com.chen.module_base.utils.SpUtils


open class CommonViewModel : BaseViewModel() {

    private val repository by lazy {
        CommonRepo(loadState)
    }

//    fun checkToken(key: String): MutableLiveData<TokenBean?> {
//        val response: MutableLiveData<TokenBean?> = MutableLiveData()
//        initiateRequest({
//            val token = repository.checkToken(key)
//            token?.let {
//                SpUtils.putString(SpKey.USER_ID, token.id)
//                SpUtils.putString(SpKey.USER_AVATAR, if(TextUtils.isEmpty(token.avatar))"" else token.avatar!! )
//                SpUtils.putString(SpKey.USER_NICKNAME, if(TextUtils.isEmpty(token.nickname))"" else token.nickname!! )
//            }
//            response.value = token
//        }, loadState)
//        return response
//    }





}