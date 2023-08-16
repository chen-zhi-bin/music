package com.czb.module_home.utils;


import com.czb.module_home.presenter.IHomeMainFragmentPresenter;
import com.czb.module_home.presenter.lmpl.HomeMainFragmentPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final IHomeMainFragmentPresenter mHomeMainFragmentPresenter;

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public IHomeMainFragmentPresenter getHomeMainFragmentPresenter() {
        return mHomeMainFragmentPresenter;
    }

    private PresenterManager(){
        mHomeMainFragmentPresenter = new HomeMainFragmentPresenterImpl();
    }
}
