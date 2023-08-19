package com.czb.module_home.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_home.callback.IMusicListActivityCallback;

public interface IMusicListActivityPresenter extends IBasePresenter<IMusicListActivityCallback> {

    void getTopMusicList();

    void getTopMusicListMore();
}
