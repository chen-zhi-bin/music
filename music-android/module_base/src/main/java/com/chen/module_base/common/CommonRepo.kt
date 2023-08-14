package com.chen.module_base.common

import androidx.lifecycle.MutableLiveData
import com.chen.module_base.mvvm.m.BaseRepository
import com.chen.module_base.net.RetrofitFactory
import com.chen.module_base.net.State


open class CommonRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {

    private val apiService by lazy {
        RetrofitFactory.instance.getService(CommonApi::class.java, CommonApi.BASE_URL)
    }







}