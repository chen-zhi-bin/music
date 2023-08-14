package com.chen.module_base.callback

import com.chen.module_base.R
import com.kingja.loadsir.callback.Callback


class ErrorCallback :Callback(){
    override fun onCreateView(): Int = R.layout.base_layout_error
}