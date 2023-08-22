package com.czb.module_ucenter.utils;

import com.czb.module_ucenter.persenter.IHistoryActivityPresenter;
import com.czb.module_ucenter.persenter.ISetActivityPresenter;
import com.czb.module_ucenter.persenter.IUserFragmentPresenter;
import com.czb.module_ucenter.persenter.Impl.HistoryActivityPresenterImpl;
import com.czb.module_ucenter.persenter.Impl.SetActivityPresenterImpl;
import com.czb.module_ucenter.persenter.Impl.UserFragmentPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final IUserFragmentPresenter mUserFragmentPresenter;
    private final IHistoryActivityPresenter mHistoryActivityPresenter;
    private final ISetActivityPresenter mSetActivityPresenter;

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public IUserFragmentPresenter getUserFragmentPresenter() {
        return mUserFragmentPresenter;
    }

    public IHistoryActivityPresenter getHistoryActivityPresenter() {
        return mHistoryActivityPresenter;
    }

    public ISetActivityPresenter getSetActivityPresenter() {
        return mSetActivityPresenter;
    }

    private PresenterManager(){
        mUserFragmentPresenter = new UserFragmentPresenterImpl();
        mHistoryActivityPresenter = new HistoryActivityPresenterImpl();
        mSetActivityPresenter = new SetActivityPresenterImpl();
    }
}
