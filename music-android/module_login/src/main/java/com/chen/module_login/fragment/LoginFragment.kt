package com.chen.module_login.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.chen.module_base.common.constant.SpKey
import com.chen.module_base.mvvm.v.BaseFragment
import com.chen.module_base.utils.SpUtils
import com.chen.module_login.LoginViewModel
import com.chen.module_login.activity.LoginActivity
import com.chen.module_login.bean.LoginInBean
import com.chen.module_login.databinding.LoginFragmentBinding
import com.zwb.lib_base.utils.EventBusUtils

class LoginFragment: BaseFragment<LoginFragmentBinding, LoginViewModel>() {
    override val mViewModel by viewModels<LoginViewModel>()

//    override val mViewModel by lazy {
//        ViewModelProvider(this).get(LoginViewModel::class.java)
//    }

    override fun LoginFragmentBinding.initView() {
        this.btnLogin.setOnClickListener {
            login()
        }
//        this.tvRegister.setOnClickListener {
//            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_REGISTER)
//        }
//        this.tvForget.setOnClickListener {
//            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_FORGET)
//        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            mBinding.editTuringCode.initTuringCode()
        }
    }

    private fun login() {
        if(mBinding.editPassword.getValue().isEmpty()){
            toast("请输入密码")
            return
        }
        if(mBinding.editTuringCode.getValue().isEmpty()){
            toast("请输入验证码")
            return
        }
        showLoading()
        val loginInBean = LoginInBean(mBinding.editPhone.getValue(),mBinding.editPassword.getValue())
//        mViewModel.login(mBinding.editTuringCode.getValue(),mBinding.editTuringCode.getKey(),loginInBean)
        mViewModel.login(mBinding.editTuringCode.getValue(),mBinding.editTuringCode.getKey(),loginInBean)
            .observe(viewLifecycleOwner,{
                mBinding.editTuringCode.initTuringCode()
                dismissLoading()
                if(it.success){
                    SpUtils.putBoolean(SpKey.IS_LOGIN, true)
                    EventBusUtils.postEvent(LoginActivity.PageType.LOGIN_SUCCESS)
                }else{
                    SpUtils.putBoolean(SpKey.IS_LOGIN, false)
                    toast(it.message)
                }
            })
    }
}