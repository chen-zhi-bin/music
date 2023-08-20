package com.czb.module_search.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_search.model.bean.MusicInfoByLyricsBean;
import com.czb.module_search.model.bean.MusicInfoByNameBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface ISearchListFragmentCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setMusicByNameData(MusicInfoByNameBean data);

    void setMusicByNameDataMore(MusicInfoByNameBean data);

    void setMusicByLyricData(MusicInfoByLyricsBean data);

    void setMusicByLyricDataMore(MusicInfoByLyricsBean data);
}
