package com.chen.module_base.common.service.community;

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface ICommunityService : IProvider {
    fun getFragment(): Fragment

}