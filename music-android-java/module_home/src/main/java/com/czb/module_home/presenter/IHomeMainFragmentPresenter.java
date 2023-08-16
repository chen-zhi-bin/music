package com.czb.module_home.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_home.callback.IHomeMainFragmentCallback;

public interface IHomeMainFragmentPresenter extends IBasePresenter<IHomeMainFragmentCallback> {

    void getBanner();

    void getTopMusic(int page);

    void getRecommend(int page);

    void getRecommendMore();
}
