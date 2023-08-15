package com.czb.module_login.presenter;


import com.czb.module_base.base.IBasePresenter;
import com.czb.module_login.callback.ILoginCallback;
import com.czb.module_login.model.bean.User;

public interface ILoginPresenter extends IBasePresenter<ILoginCallback> {

    /**
     *
     * @param code      验证码
     * @param user
     * @param key       验证码图中的key
     */
    void getLogin(String code, User user, String key);


//    void getTokenMessage(String token);
}
