package com.czb.module_home.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_home.model.bean.CollectedMusicBean;
import com.czb.module_home.model.bean.MusicAndMusicianInfoBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IMusicPlayActivityCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setErrorMessage(String message);

    void setMusicInfo(MusicAndMusicianInfoBean data);

    void setMusicCollectionMsg(CollectedMusicBean data);
}
