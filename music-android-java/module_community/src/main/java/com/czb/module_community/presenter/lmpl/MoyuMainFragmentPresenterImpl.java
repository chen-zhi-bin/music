package com.czb.module_community.presenter.lmpl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_community.callback.ICommentListActivityCallback;
import com.czb.module_community.callback.IMoyuMainFragmentCallback;
import com.czb.module_community.model.CommunityApi;
import com.czb.module_community.model.bean.CommentAndMusicBean;
import com.czb.module_community.model.bean.SubCommentBean;
import com.czb.module_community.presenter.IMoyuMainFragmentPresenter;
import com.czb.module_community.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoyuMainFragmentPresenterImpl implements IMoyuMainFragmentPresenter {

    private final CommunityApi mApi;
    private List<IMoyuMainFragmentCallback> mCallbacks = new ArrayList<>();
    private IMoyuMainFragmentCallback mCallback;
    private static final int DEFAULT_PAGE = 1;
    private int currentCommentPage = DEFAULT_PAGE;

    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_COMMENT = 1;
    private static final int RETURN_COMMENT_MORE = 2;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RETURN_ERROR:
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_COMMENT:
                    currentCommentPage++;
                    mCallback.setCommentList((CommentAndMusicBean)msg.obj);
                    break;
                case RETURN_COMMENT_MORE:
                    currentCommentPage++;
                    mCallback.setCommentListMore((CommentAndMusicBean)msg.obj);
                    break;
            }
        }
    };

    public MoyuMainFragmentPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
    }

    @Override
    public void registerViewCallback(IMoyuMainFragmentCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(IMoyuMainFragmentCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getCommentList() {
        currentCommentPage = DEFAULT_PAGE;
        mApi.getCommentList(currentCommentPage)
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
                        message.what=((CommentAndMusicBean)o).getCode()== Constants.SUCCESS?RETURN_COMMENT:ERROR;
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
    public void getCommentListMore() {
        mApi.getCommentList(currentCommentPage)
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
                        message.what=((CommentAndMusicBean)o).getCode()== Constants.SUCCESS?RETURN_COMMENT_MORE:ERROR;
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
