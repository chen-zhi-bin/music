package com.czb.module_community.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_community.callback.IMoyuMainFragmentCallback;

public interface IMoyuMainFragmentPresenter extends IBasePresenter<IMoyuMainFragmentCallback> {

    void getCommentList();

    void getCommentListMore();
}
