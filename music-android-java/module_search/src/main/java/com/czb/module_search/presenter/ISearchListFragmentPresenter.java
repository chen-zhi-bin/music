package com.czb.module_search.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_search.callback.ISearchListFragmentCallback;

public interface ISearchListFragmentPresenter extends IBasePresenter<ISearchListFragmentCallback> {
    void getSearchList(String keyword, String type);

    void getSearchListMore(String keyword, String type);
}
