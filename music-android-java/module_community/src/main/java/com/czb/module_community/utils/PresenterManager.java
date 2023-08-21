package com.czb.module_community.utils;

import com.czb.module_community.presenter.ICommentListActivityPresenter;
import com.czb.module_community.presenter.IMoyuMainFragmentPresenter;
import com.czb.module_community.presenter.ISubCommentActivityPresenter;
import com.czb.module_community.presenter.lmpl.CommentListActivityPresenterImpl;
import com.czb.module_community.presenter.lmpl.MoyuMainFragmentPresenterImpl;
import com.czb.module_community.presenter.lmpl.SubCommentActivityPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final ICommentListActivityPresenter mCommentListActivityPresenter;
    private final ISubCommentActivityPresenter mSubCommentActivityPresenter;
    private final IMoyuMainFragmentPresenter mMoyuMainFragmentPresenter;

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public ICommentListActivityPresenter getCommentListActivityPresenter() {
        return mCommentListActivityPresenter;
    }

    public ISubCommentActivityPresenter getSubCommentActivityPresenter() {
        return mSubCommentActivityPresenter;
    }

    public IMoyuMainFragmentPresenter getMoyuMainFragmentPresenter() {
        return mMoyuMainFragmentPresenter;
    }

    private PresenterManager(){
        mCommentListActivityPresenter = new CommentListActivityPresenterImpl();
        mSubCommentActivityPresenter = new SubCommentActivityPresenterImpl();
        mMoyuMainFragmentPresenter = new MoyuMainFragmentPresenterImpl();
    }
}
