package com.chen.module_base.common.service.home.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.home.IHomeService
import com.chen.module_base.utils.LogUtils

class HomeServiceWrap private constructor() {

    @Autowired(name = RoutePath.Home.SERVICE_HOME)
    lateinit var service: IHomeService

    init {
        ARouter.getInstance().inject(this)
//        var navigation = ARouter.getInstance().build(RoutePath.Home.SERVICE_HOME).navigation()
//        service = navigation as IHomeService
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }


    companion object {
        val instance = Singleton.holder

        object Singleton {
            val holder = HomeServiceWrap()
        }
    }
}