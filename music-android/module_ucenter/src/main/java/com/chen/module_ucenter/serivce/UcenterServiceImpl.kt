package com.chen.module_ucenter.serivce

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.ucenter.IUcenterService
import com.chen.module_ucenter.fragment.UcenterMainFragment


@Route(path = RoutePath.Ucenter.SERVICE_UCENTER)
class UcenterServiceImpl: IUcenterService {
    override fun getFragment(): Fragment {
        return UcenterMainFragment()
    }

    override fun init(context: Context?) {
    }
}