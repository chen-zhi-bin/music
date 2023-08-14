package com.chen.module_base.common.service.ucenter.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.ucenter.IUcenterService

class UcenterServiceWrap private constructor() {

    @Autowired(name = RoutePath.Ucenter.SERVICE_UCENTER)
    lateinit var service: IUcenterService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }



    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = UcenterServiceWrap()
        }
    }
}