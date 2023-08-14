package com.chen.module_base.net

/**
 * Description: 返回数据基类
 */

open class BaseResponse<T>(
    var success: Boolean = false,
    var data: T,
    var code: Int = -1,
    var message: String = ""
)