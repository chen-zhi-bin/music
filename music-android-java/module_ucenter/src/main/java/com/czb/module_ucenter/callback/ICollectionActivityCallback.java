package com.czb.module_ucenter.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_ucenter.model.bean.CollectionListBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface ICollectionActivityCallback extends IBaseCallback {

    LifecycleTransformer<Object> TobindToLifecycle();

    void setCollectionListData(CollectionListBean data);

    void setCollectionListDataMore(CollectionListBean data);

    void setErrorMessage(String s);
}
