package com.chen.module_base.common.service.ucenter

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IUcenterService: IProvider {
    fun getFragment(): Fragment
}