package com.czb.module_ucenter.persenter.Impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.bean.db.Music;
import com.czb.module_base.bean.db.dao.MusicDao;
import com.czb.module_base.utils.RoomUtils;
import com.czb.module_ucenter.callback.IHistoryActivityCallback;
import com.czb.module_ucenter.callback.IUserFragmentCallback;
import com.czb.module_ucenter.persenter.IHistoryActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryActivityPresenterImpl implements IHistoryActivityPresenter {

    private final MusicDao mMusicDao;
    private List<IHistoryActivityCallback> mCallbacks = new ArrayList<>();
    private IHistoryActivityCallback mCallback;
    private static final int DEFAULT_PAGE=0;
    private int page = DEFAULT_PAGE;
    private int size = 30;

    private static final int MUSIC_LIST = 1;
    private static final int MUSIC_LIST_MORE = 2;
    private final Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MUSIC_LIST:
                    page++;
                    mCallback.setMusicList((List<Music>)msg.obj);
                    break;
                case MUSIC_LIST_MORE:
                    page++;
                    mCallback.setMusicListMore((List<Music>)msg.obj);
                    break;
            }
        }
    };


    public HistoryActivityPresenterImpl(){
        mMusicDao = RoomUtils.getInstance(BaseApplication.getAppContext()).getMusicDao();
    }

    @Override
    public void registerViewCallback(IHistoryActivityCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(IHistoryActivityCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getMusicList() {
        page=DEFAULT_PAGE;
        int off = page*size;
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                List<Music> musicAll = mMusicDao.getMusicAll(off, size);
                emitter.onNext(musicAll);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Message message = new Message();
                        message.what=MUSIC_LIST;
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
    public void getMusicListMore() {
        int off = page*size;
        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        List<Music> musicAll = mMusicDao.getMusicAll(off, size);
                        emitter.onNext(musicAll);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Message message = new Message();
                        message.what=MUSIC_LIST_MORE;
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
