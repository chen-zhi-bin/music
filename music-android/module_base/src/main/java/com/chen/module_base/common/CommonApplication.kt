package com.chen.module_base.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.BaseApplication
import com.chen.module_base.BuildConfig
import com.chen.module_base.app.ApplicationLifecycle
import com.chen.module_base.callback.EmptyCallback
import com.chen.module_base.callback.ErrorCallback
import com.chen.module_base.callback.LoadingCallback
import com.chen.module_base.utils.ProcessUtils
import com.chen.module_base.utils.SpUtils
import com.chen.module_base.utils.VersionStatus
import com.chen.module_base.utils.network.NetworkStateClient
import com.google.auto.service.AutoService
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadSir
import okhttp3.OkHttpClient

@AutoService(ApplicationLifecycle::class)
class CommonApplication :ApplicationLifecycle {
    companion object {
        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mCommonApplication: CommonApplication
    }

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    override fun onAttachBaseContext(context: Context) {
        mCommonApplication = this
    }

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    override fun onCreate(application: Application) {}

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */
    override fun onTerminate(application: Application) {}

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(BaseApplication.context)) {
            list.add { initMMKV() }
            list.add { initARouter() }
            list.add { initNetworkStateClient() }
            list.add { initLoadSir() }
            list.add { initMojito() }
            list.add { initToast() }
        }
//        list.add { initTencentBugly() }
        return list
    }

    private fun initToast(): String {
        ToastUtils.init(BaseApplication.application)
        return "ToastUtils -->> init complete"
    }

    private fun initMojito(): String {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(initIntercept())
//            .build()
//        Mojito.initialize(
//            GlideImageLoader.with(BaseApplication.context, okHttpClient),
//            SketchImageLoadFactory()
//        )
        return "Mojito -->> init complete"
    }

    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    private fun initLoadSir(): String {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
        return "LoadSir -->> init complete"
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {

    }

    /**
     * 腾讯 MMKV 初始化
     */
    private fun initMMKV(): String {
        val result = SpUtils.initMMKV(BaseApplication.context)
        return "MMKV -->> $result"
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(BaseApplication.application)
        return "ARouter -->> init complete"
    }
    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    private fun initNetworkStateClient(): String {
        NetworkStateClient.register()
        return "NetworkStateClient -->> init complete"
    }
}