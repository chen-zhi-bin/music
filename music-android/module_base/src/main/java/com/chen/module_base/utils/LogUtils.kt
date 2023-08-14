package com.chen.module_base.utils

import android.util.Log
import com.chen.module_base.BuildConfig


object LogUtils {
    private const val TAG = "com.chan.module_base"

    private var DEBUG = BuildConfig.DEBUG

    fun d(tag:String = TAG, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun e(tag:String = TAG,msg: String) {
        if (DEBUG) {
            Log.e(tag, msg)
        }
    }

    fun i(tag:String = TAG,msg: String) {
        if (DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun v(tag:String = TAG,msg: String) {
        if (DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun w(tag:String = TAG,msg: String) {
        if (DEBUG) {
            Log.w(tag, msg)
        }
    }
}