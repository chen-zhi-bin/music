package com.chen.module_login.bean

import java.lang.RuntimeException

data class ApiException(val code:Int, override val message: String?):RuntimeException() {

}