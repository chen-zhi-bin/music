package com.czb.module_community.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface ICommentListActivityCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setCommentList(CommentBean data);

    void setCommentListMore(CommentBean data);

    void returnComment(CommentReturnBean data);
}
