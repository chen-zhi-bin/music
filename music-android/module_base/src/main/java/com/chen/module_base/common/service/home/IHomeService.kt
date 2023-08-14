package com.chen.module_base.common.service.home

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IHomeService: IProvider {
    fun getFragment():Fragment

}