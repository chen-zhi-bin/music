package com.czb.module_ucenter.persenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_ucenter.callback.ICollectionActivityCallback;

public interface ICollectionListActivityPresenter extends IBasePresenter<ICollectionActivityCallback> {

    void getCollectionList();

    void getCollectionListMore();
}
