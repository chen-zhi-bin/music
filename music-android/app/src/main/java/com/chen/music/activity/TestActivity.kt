package com.chen.music.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.utils.LogUtils
import com.chen.music.R


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        LogUtils.d("test","init")
        this.findViewById<TextView>(R.id.test).setOnClickListener {
            ARouter.getInstance().build("/test/home").navigation()

//            ARouter.getInstance()
//                .build(RoutePath.Login.PAGE_LOGIN)
//                .withString(RoutePath.PATH, RoutePath.Ucenter.FRAGMENT_UCENTER)
//                .navigation()
            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show()
        }

//        ARouter.getInstance().build("/test/home").navigation()
    }
}