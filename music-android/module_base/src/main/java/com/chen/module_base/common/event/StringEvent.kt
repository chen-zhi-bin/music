package com.chen.module_base.common.event

class StringEvent(var event: String) {

    object Event{
        const val SWITCH_HOME = "switch_home"
        const val SWITCH_UCENTER = "switch_ucenter"

        const val MSG_READ = "msg_read"
        const val UPDATE_HOME_TAB = "update_home_tab"
    }
}