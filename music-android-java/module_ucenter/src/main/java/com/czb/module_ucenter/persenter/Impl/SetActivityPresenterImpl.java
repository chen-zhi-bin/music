package com.czb.module_ucenter.persenter.Impl;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_ucenter.callback.IHistoryActivityCallback;
import com.czb.module_ucenter.callback.ISetActivityCallback;
import com.czb.module_ucenter.model.UserApi;
import com.czb.module_ucenter.model.bean.LogoutBean;
import com.czb.module_ucenter.model.bean.UserInfoBean;
import com.czb.module_ucenter.persenter.ISetActivityPresenter;
import com.czb.module_ucenter.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SetActivityPresenterImpl implements ISetActivityPresenter {

    private final UserApi mApi;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private List<ISetActivityCallback> mCallbacks = new ArrayList<>();
    private ISetActivityCallback mCallback;

    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_LOGOUT = 1;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ERROR:
                case RETURN_LOGOUT:
                    mSharedPreferencesUtils.remove(SharedPreferencesUtils.USER_ID);
                    mSharedPreferencesUtils.remove(SharedPreferencesUtils.USER_TOKEN_COOKIE);
                    mCallback.setReturnMessage((LogoutBean)msg.obj);
                    break;
            }
        }
    };

    public SetActivityPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
    }

    @Override
    public void registerViewCallback(ISetActivityCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(ISetActivityCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void doLogout() {
        String token = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE, null);
        mApi.doLogout(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .compose(mCallback.TobindToLifecycle())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Message message = new Message();
                        message.what=RETURN_LOGOUT;
                        message.obj = o;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
