package com.czb.module_ucenter.persenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_ucenter.callback.IUserFragmentCallback;

public interface IUserFragmentPresenter extends IBasePresenter<IUserFragmentCallback> {

    void getUserInfo();
}
