package com.czb.module_login.presenter.Impl;

import android.util.Log;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_login.callback.ILoginCallback;
import com.czb.module_login.model.LoginApi;
import com.czb.module_login.model.bean.LoginBean;
import com.czb.module_login.model.bean.User;
import com.czb.module_login.presenter.ILoginPresenter;
import com.czb.module_login.uitls.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements ILoginPresenter {
    private final LoginApi mApi;
    private ILoginCallback mLoginCallback = null;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    public LoginPresenterImpl() {
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils =SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
    }
    @Override
    public void registerViewCallback(ILoginCallback callback)  {
        mLoginCallback = callback;
    }

    @Override
    public void unregisterViewCallback(ILoginCallback callback) {
        mLoginCallback = null;
    }

    @Override
    public void getLogin(String code, User user, String key) {
        LogUtils.d("test","code =>"+code);
        LogUtils.d("test","user =>"+user);
        LogUtils.d("test","key =>"+key);
        Call<LoginBean> task = mApi.login(code, key, user);
        task.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body = response.body();
                if (body.getCode()==20000){
                    String s = response.headers().get("Set-Cookie");
                    String[] split = s.split(";");
                    String[] split1 = split[0].split("=");
                    mSharedPreferencesUtils.putString(SharedPreferencesUtils.USER_TOKEN_COOKIE,split1[1]);
                    mSharedPreferencesUtils.putString(SharedPreferencesUtils.USER_ID,body.getData().getId());
                    mLoginCallback.onLoginSuccess();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                mLoginCallback.onLoginError();
            }
        });
    }
}
