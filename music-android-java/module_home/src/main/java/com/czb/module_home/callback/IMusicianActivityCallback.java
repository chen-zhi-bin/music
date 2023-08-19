package com.czb.module_home.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_home.model.bean.MusicianBean;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IMusicianActivityCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setErrorMessage(String s);

    void setMusicBean(MusicianMusicBean data);

    void setMusicBeanMore(MusicianMusicBean data);
}
