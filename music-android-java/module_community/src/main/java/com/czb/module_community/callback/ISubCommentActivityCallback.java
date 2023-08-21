package com.czb.module_community.callback;

import com.czb.module_base.base.IBaseCallback;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.SubCommentBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

public interface ISubCommentActivityCallback extends IBaseCallback {
    String getKey();

    LifecycleTransformer<Object> TobindToLifecycle();

    void setRequestError(String message);

    void setSubCommentList(SubCommentBean data);

    void setSubCommentListMore(SubCommentBean data);

    void returnComment(CommentReturnBean data);
}
