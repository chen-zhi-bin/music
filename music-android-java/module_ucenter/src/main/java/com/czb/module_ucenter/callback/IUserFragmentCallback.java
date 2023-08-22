package com.czb.module_ucenter.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_ucenter.model.bean.UserInfoBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IUserFragmentCallback extends IBaseCallback {
    void setNotLogin();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setUserInfo(UserInfoBean data);
}
