package com.czb.module_home.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_home.callback.IMusicianActivityCallback;

public interface IMusicianActivityPresenter extends IBasePresenter<IMusicianActivityCallback> {

    void getMusicByMusician(String id, int i);

    void getMusicByMusicianMore(String id);
}
