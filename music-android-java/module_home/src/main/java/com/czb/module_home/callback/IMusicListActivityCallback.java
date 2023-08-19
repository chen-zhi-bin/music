package com.czb.module_home.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_home.model.bean.TopMusicBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IMusicListActivityCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setErrorMessage(String message);

    void setTopMusicList(TopMusicBean data);

    void setTopMusicListMore(TopMusicBean data);
}
