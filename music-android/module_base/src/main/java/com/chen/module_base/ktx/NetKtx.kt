package com.chen.module_base.ktx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chen.module_base.mvvm.vm.BaseViewModel

import com.chen.module_base.net.BaseResponse
import com.chen.module_base.net.NetExceptionHandle
import com.chen.module_base.net.State
import com.chen.module_base.net.StateType

import kotlinx.coroutines.launch

/**
 * Description:数据解析扩展函数
 */




fun BaseViewModel.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>,
    urlKey: String = ""
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState, urlKey)
        }
    }
}
