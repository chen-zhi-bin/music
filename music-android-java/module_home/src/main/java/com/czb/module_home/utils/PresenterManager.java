package com.czb.module_home.utils;


import com.czb.module_home.presenter.IHomeMainFragmentPresenter;
import com.czb.module_home.presenter.IMusicListActivityPresenter;
import com.czb.module_home.presenter.IMusicianActivityPresenter;
import com.czb.module_home.presenter.lmpl.HomeMainFragmentPresenterImpl;
import com.czb.module_home.presenter.lmpl.MusicListActivityPresenterImpl;
import com.czb.module_home.presenter.lmpl.MusicianActivityPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final IHomeMainFragmentPresenter mHomeMainFragmentPresenter;
    private final IMusicianActivityPresenter mMusicianActivityPresenter;
    private final IMusicListActivityPresenter mMusicListActivityPresenter;

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public IHomeMainFragmentPresenter getHomeMainFragmentPresenter() {
        return mHomeMainFragmentPresenter;
    }

    public IMusicianActivityPresenter getMusicianActivityPresenter() {
        return mMusicianActivityPresenter;
    }

    public IMusicListActivityPresenter getMusicListActivityPresenter() {
        return mMusicListActivityPresenter;
    }

    private PresenterManager(){
        mHomeMainFragmentPresenter = new HomeMainFragmentPresenterImpl();
        mMusicianActivityPresenter = new MusicianActivityPresenterImpl();
        mMusicListActivityPresenter = new MusicListActivityPresenterImpl();
    }
}
