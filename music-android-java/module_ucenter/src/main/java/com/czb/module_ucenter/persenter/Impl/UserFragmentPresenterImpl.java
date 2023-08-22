package com.czb.module_ucenter.persenter.Impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_ucenter.callback.IUserFragmentCallback;
import com.czb.module_ucenter.model.UserApi;
import com.czb.module_ucenter.model.bean.UserInfoBean;
import com.czb.module_ucenter.persenter.IUserFragmentPresenter;
import com.czb.module_ucenter.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserFragmentPresenterImpl implements IUserFragmentPresenter {

    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private List<IUserFragmentCallback> mCallbacks = new ArrayList<>();
    private IUserFragmentCallback mCallback;
    private final UserApi mApi;
    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_USER_INFO = 1;  //请求错误

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_USER_INFO:
                    mCallback.setUserInfo((UserInfoBean)msg.obj);
                    break;
            }
        }
    };


    public UserFragmentPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
    }

    @Override
    public void registerViewCallback(IUserFragmentCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(IUserFragmentCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getUserInfo() {
        String userId = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_ID, null);
        if (userId == null) {
            mCallback.setNotLogin();
        }
        mApi.getUserInfo(userId)
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
                        message.what=((UserInfoBean)o).getCode()== Constants.SUCCESS?RETURN_USER_INFO:ERROR;
                        message.obj = o;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        requestFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void requestFailed() {
        Message message = new Message();
        message.what = RETURN_ERROR;
        mHandler.sendMessage(message);
    }
}
