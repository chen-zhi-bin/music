package com.czb.module_community.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_community.callback.ISubCommentActivityCallback;
import com.czb.module_community.model.bean.MomentSubComment;

public interface ISubCommentActivityPresenter extends IBasePresenter<ISubCommentActivityCallback> {
    void getSubCommentList(String id);

    void getSubCommentListMore(String id);

    void postSubComment(MomentSubComment momentSubComment);
}
