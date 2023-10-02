package com.czb.module_home.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_home.callback.IMusicPlayActivityCallback;

public interface IMusicPlayActivityPresenter extends IBasePresenter<IMusicPlayActivityCallback> {
    void getMusicInfo(String songId);

    void postCollectMusicById(String currentMusicId);
}
