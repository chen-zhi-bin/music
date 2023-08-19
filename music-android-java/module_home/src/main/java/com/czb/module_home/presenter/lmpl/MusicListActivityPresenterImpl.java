package com.czb.module_home.presenter.lmpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.common.Constants;
import com.czb.module_home.callback.IMusicListActivityCallback;
import com.czb.module_home.callback.IMusicianActivityCallback;
import com.czb.module_home.model.HomeApi;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.czb.module_home.presenter.IMusicListActivityPresenter;
import com.czb.module_home.ui.fragment.HomeMainFragment;
import com.czb.module_home.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MusicListActivityPresenterImpl implements IMusicListActivityPresenter {

    private List<IMusicListActivityCallback> mCallbacks = new ArrayList<>();
    private IMusicListActivityCallback mCallback;
    private final HomeApi mApi;
    private final static int DEFAULT_PAGE=1;
    private int currentTopMusicPage = DEFAULT_PAGE;

    private static final int ERROR = 0;
    private static final int RETURN_ERROR = 1;
    private static final int RETURN_MUSIC_TOP = 2;
    private static final int RETURN_MUSIC_TOP_MORE = 3;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            String key = msg.getData().getString("key");
            for (IMusicListActivityCallback callback : mCallbacks) {
                if (callback.getKey().equals(key)) {
                    mCallback = callback;
                }
            }
            switch (msg.what){
                case ERROR:
                    mCallback.setErrorMessage("网络错误，请稍后重试");
                    break;
                case RETURN_ERROR:
                    mCallback.setErrorMessage("网络错误，请稍后重试");
                    break;
                case RETURN_MUSIC_TOP:
                    currentTopMusicPage++;
                    mCallback.setTopMusicList((TopMusicBean)msg.obj);
                    break;
                case RETURN_MUSIC_TOP_MORE:
                    currentTopMusicPage++;
                    mCallback.setTopMusicListMore((TopMusicBean)msg.obj);
                    break;
            }
        }
    };

    public MusicListActivityPresenterImpl() {
        mApi = RetrofitManager.getInstence().getApi();
    }

    @Override
    public void registerViewCallback(IMusicListActivityCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(IMusicListActivityCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getTopMusicList() {
        for (IMusicListActivityCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getTopList(currentTopMusicPage)
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
                        message.what=((TopMusicBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_TOP:ERROR;
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
    public void getTopMusicListMore() {
        for (IMusicListActivityCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getTopList(currentTopMusicPage)
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
                        message.what=((TopMusicBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_TOP_MORE:ERROR;
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

    private void requestFailed() {
        Message message = new Message();
        message.what = RETURN_ERROR;
        mHandler.sendMessage(message);
    }
}
