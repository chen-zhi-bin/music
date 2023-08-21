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
import com.czb.module_community.model.CommunityApi;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.MomentComment;
import com.czb.module_community.model.bean.MomentSubComment;
import com.czb.module_community.presenter.ICommentListActivityPresenter;
import com.czb.module_community.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentListActivityPresenterImpl implements ICommentListActivityPresenter {

    private final CommunityApi mApi;
    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private List<ICommentListActivityCallback> mCallbacks = new ArrayList<>();
    private ICommentListActivityCallback mCallback;
    private static final int DEFAULT_PAGE = 1;
    private int currentCommentPage = DEFAULT_PAGE;

    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_COMMENT = 1;
    private static final int RETURN_COMMENT_MORE = 2;
    private static final int RETURN_COMMENT_SUCCESS = 3;
    private static final int RETURN_SUB_COMMENT_SUCCESS = 4;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_COMMENT:
                    currentCommentPage++;
                    mCallback.setCommentList((CommentBean)msg.obj);
                    break;
                case RETURN_COMMENT_MORE:
                    currentCommentPage++;
                    mCallback.setCommentListMore((CommentBean)msg.obj);
                    break;
                case RETURN_COMMENT_SUCCESS:
                    mCallback.returnComment((CommentReturnBean)msg.obj);
                    break;
                case RETURN_SUB_COMMENT_SUCCESS:
                    mCallback.returnComment((CommentReturnBean)msg.obj);
                    break;
            }
        }
    };

    public CommentListActivityPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
    }

    @Override
    public void registerViewCallback(ICommentListActivityCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(ICommentListActivityCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getCommentList(String musicId) {
        currentCommentPage = DEFAULT_PAGE;
        mApi.getComment(musicId,currentCommentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .compose(mCallback.TobindToLifecycle())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        LogUtils.d("test","result == "+((CommentBean)o).toString());
                        Message message = new Message();
                        message.what=((CommentBean)o).getCode()== Constants.SUCCESS?RETURN_COMMENT:ERROR;
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

    @Override
    public void getCommentListMore(String musicId) {
        mApi.getComment(musicId,currentCommentPage)
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
                        message.what=((CommentBean)o).getCode()== Constants.SUCCESS?RETURN_COMMENT_MORE:ERROR;
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

    @Override
    public void postComment(MomentComment momentComment) {
        String token = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE, null);
        if (token==null){
            mCallback.setRequestError("尚未登录");
        }
        mApi.postComment(token,momentComment)
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
                        message.what=((CommentReturnBean)o).getCode()== Constants.SUCCESS?RETURN_COMMENT_SUCCESS:ERROR;
                        message.obj = o;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.d("test",e.getMessage());
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
