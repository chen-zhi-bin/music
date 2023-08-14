package com.chen.module_ucenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chen.module_base.mvvm.v.BaseFragment
import com.chen.module_base.utils.EventBusRegister
import com.chen.module_ucenter.UcenterViewModel
import com.chen.module_ucenter.databinding.UcenterFragmentMainBinding

@EventBusRegister
class UcenterMainFragment :BaseFragment<UcenterFragmentMainBinding, UcenterViewModel>(){
    override val mViewModel by viewModels<UcenterViewModel>()
    override fun UcenterFragmentMainBinding.initView() {
//        TODO("Not yet implemented")
    }

    override fun initObserve() {
//        TODO("Not yet implemented")
    }

    override fun initRequestData() {
//        TODO("Not yet implemented")
    }
}