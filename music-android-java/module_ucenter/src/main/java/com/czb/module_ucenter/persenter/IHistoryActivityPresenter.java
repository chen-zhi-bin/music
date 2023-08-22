package com.czb.module_ucenter.persenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_ucenter.callback.IHistoryActivityCallback;

public interface IHistoryActivityPresenter extends IBasePresenter<IHistoryActivityCallback> {

    void getMusicList();

    void getMusicListMore();
}
