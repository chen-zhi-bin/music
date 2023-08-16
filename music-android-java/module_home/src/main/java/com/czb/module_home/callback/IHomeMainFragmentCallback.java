package com.czb.module_home.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IHomeMainFragmentCallback extends IBaseCallback {

    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setBanner(BannerBean bean);

    void setTopMusic(TopMusicBean data);

    void setRecommendMusic(RecommendMusicBean data);

    void setRecommendMusicMore(RecommendMusicBean data);
}
