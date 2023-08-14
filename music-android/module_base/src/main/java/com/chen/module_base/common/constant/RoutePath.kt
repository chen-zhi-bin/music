package com.chen.module_base.common.constant

/**
 * 路由地址
 *
 */
object RoutePath{

    const val PATH = "path"

    const val PAGE_PHONE = "/common/PhotoBrowse"

    object Login{
        private const val LOGIN = "/login"
        const val PAGE_LOGIN = "$LOGIN/LoginActivity"
    }

    object Home{
        private const val HOME = "/home"
        const val SERVICE_HOME = "${HOME}/home_service"
    }

    object Community{
        private const val COMMUNITY = "/community"
        const val FRAGMENT_COMMUNITY = "$COMMUNITY/CommunityMainFragment"
        const val SERVICE_COMMUNITY = "${COMMUNITY}/Community_service"
    }


    object Ucenter{
        private const val UCENTER = "/ucenter"
        const val FRAGMENT_UCENTER = "$UCENTER/UcenterFragment"
        const val SERVICE_UCENTER = "${UCENTER}/ucenter_service"



    }

}