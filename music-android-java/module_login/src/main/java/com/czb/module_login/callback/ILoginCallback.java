package com.czb.module_login.callback;


import com.czb.module_base.base.IBaseCallback;

public interface ILoginCallback extends IBaseCallback {

    void onLoginError();

    void onLoginSuccess();
}
