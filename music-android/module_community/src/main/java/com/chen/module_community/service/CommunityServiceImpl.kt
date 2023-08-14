package com.chen.module_community.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.service.community.ICommunityService
import com.chen.module_base.common.service.ucenter.IUcenterService
import com.chen.module_community.fragment.CommunityMainFragment

@Route(path = RoutePath.Community.SERVICE_COMMUNITY)
class CommunityServiceImpl : ICommunityService {
    override fun getFragment(): Fragment {
        return CommunityMainFragment()
    }

    override fun init(context: Context?) {
    }
}
