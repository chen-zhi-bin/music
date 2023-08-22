package com.czb.module_ucenter.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_ucenter.model.bean.LogoutBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface ISetActivityCallback extends IBaseCallback {
    LifecycleTransformer<Object> TobindToLifecycle();

    void setReturnMessage(LogoutBean message);
}
