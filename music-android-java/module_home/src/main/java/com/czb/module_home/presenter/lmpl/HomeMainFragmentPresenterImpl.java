package com.czb.module_home.presenter.lmpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.czb.module_base.common.Constants;
import com.czb.module_home.callback.IHomeMainFragmentCallback;
import com.czb.module_home.model.HomeApi;
import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.czb.module_home.presenter.IHomeMainFragmentPresenter;
import com.czb.module_home.ui.fragment.HomeMainFragment;
import com.czb.module_home.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeMainFragmentPresenterImpl implements IHomeMainFragmentPresenter {
    private List<IHomeMainFragmentCallback> mCallbacks = new ArrayList<>();
    private IHomeMainFragmentCallback mCallback;

    private int defaultPage =1;
    private int currentPage = defaultPage;
    private int currentCommendPage = defaultPage;
    private final HomeApi mApi;
    private static final int ERROR = -1;         //能请求，但是有错误
    private static final int RETURN_ERROR = 0;  //请求错误
    private static final int RETURN_BANNER = 1;  //请求错误
    private static final int RETURN_TOP_MUSIC = 2;  //请求错误
    private static final int RETURN_MUSIC_RECOMMEND = 3;
    private static final int RETURN_MUSIC_RECOMMEND_MORE = 4;

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@androidx.annotation.NonNull Message msg) {
            super.handleMessage(msg);
            String key = msg.getData().getString("key");
            for (IHomeMainFragmentCallback callback : mCallbacks) {
                if (callback.getKey().equals(key)) {
                    mCallback = callback;
                }
            }
            switch (msg.what){
                case ERROR:
                    mCallback.setRequestError("网络错误，请稍后重试");
                    break;
                case RETURN_BANNER:
                    mCallback.setBanner((BannerBean)msg.obj);
                    break;
                case RETURN_TOP_MUSIC:
                    mCallback.setTopMusic((TopMusicBean)msg.obj);
                    break;
                case RETURN_MUSIC_RECOMMEND:
                    mCallback.setRecommendMusic((RecommendMusicBean)msg.obj);
                    break;
                case RETURN_MUSIC_RECOMMEND_MORE:
                    mCallback.setRecommendMusicMore((RecommendMusicBean)msg.obj);
                    break;
            }
        }
    };

    public HomeMainFragmentPresenterImpl() {
        mApi = RetrofitManager.getInstence().getApi();
    }
    @Override
    public void registerViewCallback(IHomeMainFragmentCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        mCallback =callback;
    }

    @Override
    public void unregisterViewCallback(IHomeMainFragmentCallback callback) {
        if (mCallbacks.contains(callback)) {
            mCallbacks.remove(callback);
        }
        mCallback = null;
    }

    @Override
    public void getBanner() {
        for (IHomeMainFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getBanner()
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
                        message.what=((BannerBean)o).getCode()== Constants.SUCCESS?RETURN_BANNER:ERROR;
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
    public void getTopMusic(int page) {
        for (IHomeMainFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        currentPage=page;
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getTop(currentPage)
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
                        message.what=((TopMusicBean)o).getCode()== Constants.SUCCESS?RETURN_TOP_MUSIC:ERROR;
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
    public void getRecommend(int page) {
        for (IHomeMainFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        currentCommendPage=page;
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getRecommend(currentCommendPage)
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
                        message.what=((RecommendMusicBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_RECOMMEND:ERROR;
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
    public void getRecommendMore() {
        currentCommendPage++;
        for (IHomeMainFragmentCallback callback : mCallbacks) {
            if (callback.getKey().equals(HomeMainFragment.key)) {
                mCallback = callback;
                break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("key",HomeMainFragment.key);
        mApi.getRecommend(currentCommendPage)
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
                        message.what=((RecommendMusicBean)o).getCode()== Constants.SUCCESS?RETURN_MUSIC_RECOMMEND_MORE:ERROR;
                        message.obj = o;
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        requestFailed();
                        currentCommendPage--;
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
