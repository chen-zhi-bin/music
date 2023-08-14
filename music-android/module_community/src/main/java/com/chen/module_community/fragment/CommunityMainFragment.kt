package com.chen.module_community.fragment

import androidx.fragment.app.viewModels
import com.chen.module_base.mvvm.v.BaseFragment
import com.chen.module_community.CommunityViewModel
import com.chen.module_community.databinding.CommunityFragmentMainBinding

class CommunityMainFragment : BaseFragment<CommunityFragmentMainBinding, CommunityViewModel>() {
    override val mViewModel by viewModels<CommunityViewModel>()
    override fun CommunityFragmentMainBinding.initView() {
//        TODO("Not yet implemented")
    }

    override fun initObserve() {
//        TODO("Not yet implemented")
    }

    override fun initRequestData() {
//        TODO("Not yet implemented")
    }
}