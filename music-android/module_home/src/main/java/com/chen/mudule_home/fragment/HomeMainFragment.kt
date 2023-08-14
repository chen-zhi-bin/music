package com.chen.mudule_home.fragment

import androidx.fragment.app.viewModels
import com.chen.module_base.mvvm.v.BaseFragment
import com.chen.mudule_home.HomeViewModel
import com.chen.mudule_home.databinding.HomeFragmentMainBinding

class HomeMainFragment:BaseFragment<HomeFragmentMainBinding, HomeViewModel>() {

    override val mViewModel by viewModels<HomeViewModel>()
    override fun initObserve() {
//        TODO("Not yet implemented")
    }

    override fun HomeFragmentMainBinding.initView() {

    }

    override fun initRequestData() {
//        TODO("Not yet implemented")
    }


}