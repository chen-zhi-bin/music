package com.czb.module_base.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.czb.module_base.callback.EmptyCallback;
import com.czb.module_base.callback.ErrorCallback;
import com.czb.module_base.callback.LoadingCallback;
import com.kingja.loadsir.core.LoadSir;

public class BaseApplication extends Application {
    private static Context appContext;

    public static Context getAppContext(){
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application","BaseApplication");
        appContext = getBaseContext();
        initARouter();
        initLoadSir();
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }

    private void initARouter() {
        //        if BuildConfig.DEBUG
        //伪代码  上线前必须关闭
        if (true) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
