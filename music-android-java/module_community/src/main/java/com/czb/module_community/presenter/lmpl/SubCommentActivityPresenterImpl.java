package com.czb.module_community.presenter.lmpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_community.callback.ICommentListActivityCallback;
import com.czb.module_community.callback.ISubCommentActivityCallback;
import com.czb.module_community.model.CommunityApi;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.MomentSubComment;
import com.czb.module_community.model.bean.SubCommentBean;
import com.czb.module_community.presenter.ISubCommentActivityPresenter;
import com.czb.module_community.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubCommentActivityPresenterImpl implements ISubCommentActivityPresenter {

    private final CommunityApi mApi;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private List<ISubCommentActivityCallback> mCallbacks = new ArrayList<>();
    private ISubCommentActivityCallback mCallback;
    private static final int DEFAULT_PAGE = 1;
    private int currentCommentPage = DEFAULT_PAGE;

    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_SUB_COMMENT = 1;  //请求错误
    private static final int RETURN_SUB_COMMENT_MORE = 2;
    private static final int RETURN_SUB_COMMENT_SUCCESS = 3;
    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            String key = msg.getData().getString("key");
            for (ISubCommentActivityCallback callback : mCallbacks) {
                if (callback.getKey().equals(key)) {
                    mCallback = callback;
                }
            }
            switch (msg.what){
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_SUB_COMMENT:
                    currentCommentPage++;
                    mCallback.setSubCommentList((SubCommentBean)msg.obj);
                    break;
                case RETURN_SUB_COMMENT_MORE:
                    mCallback.setSubCommentListMore((SubCommentBean)msg.obj);
                    break;
                case RETURN_SUB_COMMENT_SUCCESS:
                    mCallback.returnComment((CommentReturnBean)msg.obj);
                    break;
            }
        }
    };

    public SubCommentActivityPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
    }

    @Override
    public void registerViewCallback(ISubCommentActivityCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(ISubCommentActivityCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getSubCommentList(String id) {
        for (ISubCommentActivityCallback callback : mCallbacks) {
            if (callback.getKey().equals(id)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",id);
        currentCommentPage = DEFAULT_PAGE;
        mApi.getCommentByCommentId(id,currentCommentPage)
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
                        message.what=((SubCommentBean)o).getCode()== Constants.SUCCESS?RETURN_SUB_COMMENT:ERROR;
                        message.obj = o;
                        message.setData(bundle);
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

    @Override
    public void getSubCommentListMore(String id) {
        for (ISubCommentActivityCallback callback : mCallbacks) {
            if (callback.getKey().equals(id)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",id);
        mApi.getCommentByCommentId(id,currentCommentPage)
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
                        message.what=((SubCommentBean)o).getCode()== Constants.SUCCESS?RETURN_SUB_COMMENT_MORE:ERROR;
                        message.obj = o;
                        message.setData(bundle);
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

    @Override
    public void postSubComment(MomentSubComment momentSubComment) {
        String token = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE, null);
        if (token==null){
            mCallback.setRequestError("尚未登录");
        }
        mApi.postSubComment(token,momentSubComment)
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
                        message.what=((CommentReturnBean)o).getCode()== Constants.SUCCESS?RETURN_SUB_COMMENT_SUCCESS:ERROR;
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
