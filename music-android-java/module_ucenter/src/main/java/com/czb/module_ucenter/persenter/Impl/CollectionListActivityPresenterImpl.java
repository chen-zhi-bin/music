package com.czb.module_ucenter.persenter.Impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_ucenter.callback.ICollectionActivityCallback;
import com.czb.module_ucenter.model.UserApi;
import com.czb.module_ucenter.model.bean.CollectionListBean;
import com.czb.module_ucenter.persenter.ICollectionListActivityPresenter;
import com.czb.module_ucenter.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectionListActivityPresenterImpl implements ICollectionListActivityPresenter {
    private final UserApi mApi;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private final String mToken;
//    private List<ICollectionActivityCallback> mCallbacks = new ArrayList<>();
    private ICollectionActivityCallback mCallback = null;
    private static final int DEFAULT_PAGE=1;
    private int page = DEFAULT_PAGE;

    private static final int ERROR = 0;
    private static final int RETURN_ERROR = 1;
    private static final int RETURN_MUSIC_COLLECTION_LIST = 2;
    private static final int RETURN_MUSIC_COLLECTION_LIST_MORE = 3;
    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ERROR:
                    mCallback.setErrorMessage("网络错误，请稍后重试");
                    break;
                case RETURN_ERROR:
                    mCallback.setErrorMessage("网络错误，请稍后重试");
                    break;
                case RETURN_MUSIC_COLLECTION_LIST:
                    page++;
                    mCallback.setCollectionListData((CollectionListBean) msg.obj);
                    break;
                case RETURN_MUSIC_COLLECTION_LIST_MORE:
                    page++;
                    mCallback.setCollectionListDataMore((CollectionListBean) msg.obj);
                    break;
            }
        }
    };


    public CollectionListActivityPresenterImpl() {
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
        mToken = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE);
    }

    @Override
    public void registerViewCallback(ICollectionActivityCallback callback) {
//        if (!mCallbacks.contains(callback)) {
//            mCallbacks.add(callback);
//        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(ICollectionActivityCallback callback) {
//        if (mCallbacks.contains(callback)) {
//            mCallbacks.remove(callback);
//        }
        mCallback = null;
    }

    @Override
    public void getCollectionList() {
        page = DEFAULT_PAGE;
        mApi.getCollectionListByPage(mToken,page)
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
                        message.what=((CollectionListBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_COLLECTION_LIST:ERROR;
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

    @Override
    public void getCollectionListMore() {
        mApi.getCollectionListByPage(mToken,page)
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
                        message.what=((CollectionListBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_COLLECTION_LIST_MORE:ERROR;
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
