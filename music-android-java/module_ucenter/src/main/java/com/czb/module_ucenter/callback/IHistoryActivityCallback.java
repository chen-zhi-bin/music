package com.czb.module_ucenter.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_base.bean.db.Music;

import java.util.List;

public interface IHistoryActivityCallback extends IBaseCallback {

    void setMusicList(List<Music> data);

    void setMusicListMore(List<Music> data);
}
