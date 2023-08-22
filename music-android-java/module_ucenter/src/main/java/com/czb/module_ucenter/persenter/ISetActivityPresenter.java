package com.czb.module_ucenter.persenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_ucenter.callback.ISetActivityCallback;

public interface ISetActivityPresenter extends IBasePresenter<ISetActivityCallback> {
    void doLogout();

}
