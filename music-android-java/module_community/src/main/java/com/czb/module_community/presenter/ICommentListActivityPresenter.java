package com.czb.module_community.presenter;

import com.czb.module_base.base.IBasePresenter;
import com.czb.module_community.callback.ICommentListActivityCallback;
import com.czb.module_community.model.bean.MomentComment;
import com.czb.module_community.model.bean.MomentSubComment;

public interface ICommentListActivityPresenter extends IBasePresenter<ICommentListActivityCallback> {
    void getCommentList(String musicId);

    void getCommentListMore(String musicId);

    void postComment(MomentComment momentComment);

    void postSubComment(MomentSubComment momentSubComment);
}
