package com.chen.mudule_home.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.home.IHomeService
import com.chen.mudule_home.fragment.HomeMainFragment

@Route(path = RoutePath.Home.SERVICE_HOME)
class HomeServiceImpl : IHomeService {
    override fun getFragment(): Fragment {
        return HomeMainFragment()
    }

    override fun init(context: Context?) {
    }
}
