package com.czb.module_base.utils;

import android.util.Log;

public class LogUtils {
    //等级控制
    public static int currentLev = 5;

    public static final int DEBUG_LEV = 4;
    public static final int INFO_LEV = 3;
    public static final int WARNING_LEV = 2;
    public static final int ERROR_LEV = 1;

    public static void d(Object object,String log){
        if (currentLev> DEBUG_LEV){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }
    public static void i(Object object,String log){
        if (currentLev> INFO_LEV){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

    public static void w(Object object,String log){
        if (currentLev> WARNING_LEV){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

    public static void e(Object object,String log){
        if (currentLev> ERROR_LEV){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }

}
