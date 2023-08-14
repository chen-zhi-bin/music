package com.chen.module_base.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chen.module_base.net.State

/**
 * ViewModel 基类
 *
 */
open class BaseViewModel: ViewModel() {

    val loadState by lazy {
        MutableLiveData<State>()
    }

    override fun onCleared() {
        super.onCleared()
    }

}