package com.czb.module_search.presenter.lmpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_search.callback.ISearchListFragmentCallback;
import com.czb.module_search.model.SearchApi;
import com.czb.module_search.model.bean.MusicInfoByLyricsBean;
import com.czb.module_search.model.bean.MusicInfoByNameBean;
import com.czb.module_search.presenter.ISearchListFragmentPresenter;
import com.czb.module_search.ui.fragment.SearchListFragment;
import com.czb.module_search.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchListFragmentPresenterImpl implements ISearchListFragmentPresenter {

    private List<ISearchListFragmentCallback> mCallbacks = new ArrayList<>();
    private ISearchListFragmentCallback mCallback;
    private final SearchApi mApi;
    private final static int DEFAULT_PAGE=1;
    private int currentPageByName = DEFAULT_PAGE;
    private int currentPageByLyric = DEFAULT_PAGE;

    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_MUSIC_BY_NAME = 1;
    private static final int RETURN_MUSIC_BY_NAME_MORE = 2;
    private static final int RETURN_MUSIC_BY_LYRIC = 3;
    private static final int RETURN_MUSIC_BY_LYRIC_MORE = 4;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            String key = msg.getData().getString("key");
            for (ISearchListFragmentCallback callback : mCallbacks) {
                if (callback.getKey().equals(key)) {
                    mCallback = callback;
                }
            }
            switch (msg.what){
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_MUSIC_BY_NAME:
                    currentPageByName++;
                    mCallback.setMusicByNameData((MusicInfoByNameBean)msg.obj);
                    break;
                case RETURN_MUSIC_BY_NAME_MORE:
                    currentPageByName++;
                    mCallback.setMusicByNameDataMore((MusicInfoByNameBean)msg.obj);
                    break;
                case RETURN_MUSIC_BY_LYRIC:
                    currentPageByLyric++;
                    mCallback.setMusicByLyricData((MusicInfoByLyricsBean)msg.obj);
                    break;
                case RETURN_MUSIC_BY_LYRIC_MORE:
                    currentPageByLyric++;
                    mCallback.setMusicByLyricDataMore((MusicInfoByLyricsBean)msg.obj);
                    break;
            }
        }
    };

    public SearchListFragmentPresenterImpl(){
        mApi = RetrofitManager.getInstence().getApi();
    }
    @Override
    public void registerViewCallback(ISearchListFragmentCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(ISearchListFragmentCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getSearchList(String keyword, String type) {
        for (ISearchListFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(type)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",type);
        if (type.equals(Constants.SearchType.TYPE_MUSIC_NAME)) {
            currentPageByName = DEFAULT_PAGE;
            mApi.doSearchByName(currentPageByName,keyword)
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
                            message.what=((MusicInfoByNameBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_BY_NAME:ERROR;
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
        }else if (type.equals(Constants.SearchType.TYPE_MUSIC_LYRICS)){
            currentPageByLyric = DEFAULT_PAGE;
            mApi.doSearchByLyric(currentPageByLyric,keyword)
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
                            message.what=((MusicInfoByLyricsBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_BY_LYRIC:ERROR;
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
    }

    @Override
    public void getSearchListMore(String keyword, String type) {
        for (ISearchListFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(type)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",type);
        if (type.equals(Constants.SearchType.TYPE_MUSIC_NAME)) {
            mApi.doSearchByName(currentPageByName,keyword)
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
                            message.what=((MusicInfoByNameBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_BY_NAME_MORE:ERROR;
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
        }else if (type.equals(Constants.SearchType.TYPE_MUSIC_LYRICS)){
            mApi.doSearchByLyric(currentPageByLyric,keyword)
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
                            message.what=((MusicInfoByLyricsBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_BY_LYRIC_MORE:ERROR;
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
    }

    private void requestFailed() {
        Message message = new Message();
        message.what = RETURN_ERROR;
        mHandler.sendMessage(message);
    }
}
