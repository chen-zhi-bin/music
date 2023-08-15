package com.czb.module_login.uitls;


import com.czb.module_login.presenter.IForgetPresenter;
import com.czb.module_login.presenter.ILoginPresenter;
import com.czb.module_login.presenter.IRegisterPresenter;
import com.czb.module_login.presenter.Impl.ForgetPresenterImpl;
import com.czb.module_login.presenter.Impl.LoginPresenterImpl;
import com.czb.module_login.presenter.Impl.RegisterPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
        private final IRegisterPresenter mRegisterPresenter;
    private final IForgetPresenter mForgetPresenter;
    private final ILoginPresenter mLoginPresenter;

    public static PresenterManager getInstance() {
        return ourInstance;
    }

    private PresenterManager() {
        mLoginPresenter = new LoginPresenterImpl();
        mRegisterPresenter = new RegisterPresenterImpl();
        mForgetPresenter = new ForgetPresenterImpl();
    }

    public IForgetPresenter getForgetPresenter() {
        return mForgetPresenter;
    }

    public IRegisterPresenter getRegisterPresenter() {
        return mRegisterPresenter;
    }


    public ILoginPresenter getLoginPresenter() {
        return mLoginPresenter;
    }
}
