package com.chen.module_base.mvvm.v

import androidx.viewbinding.ViewBinding

interface FrameView<VB : ViewBinding> {

    /**
     * 初始化View
     */
    fun VB.initView()

    /**
     * 订阅LiveData
     */
    fun initObserve()

    /**
     * 用于在页面创建时进行请求接口
     */
    fun initRequestData()
}