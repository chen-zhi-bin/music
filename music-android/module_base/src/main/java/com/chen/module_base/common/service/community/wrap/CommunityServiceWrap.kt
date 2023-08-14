package com.chen.module_base.common.service.community.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.community.ICommunityService

class CommunityServiceWrap private constructor(){
    @Autowired(name = RoutePath.Community.SERVICE_COMMUNITY)
    lateinit var service: ICommunityService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }


    companion object {
        val instance = Singleton.holder

        object Singleton {
            val holder = CommunityServiceWrap()
        }
    }
}