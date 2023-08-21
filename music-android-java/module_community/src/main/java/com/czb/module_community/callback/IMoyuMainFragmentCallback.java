package com.czb.module_community.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_community.model.bean.CommentAndMusicBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IMoyuMainFragmentCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setCommentList(CommentAndMusicBean data);

    void setCommentListMore(CommentAndMusicBean data);
}
