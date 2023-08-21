package com.czb.module_community.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.common.service.moyu.wrap.MoyuServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.ReplyBottomSheetDialog;
import com.czb.module_community.R;
import com.czb.module_community.adapter.CommentAdapter;
import com.czb.module_community.callback.ICommentListActivityCallback;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.MomentComment;
import com.czb.module_community.model.bean.MomentSubComment;
import com.czb.module_community.presenter.ICommentListActivityPresenter;
import com.czb.module_community.utils.PresenterManager;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Route(path = RoutePath.Moyu.DETAIL_MUSIC_COMMENT)
public class CommentListActivity extends BaseActivity implements ICommentListActivityCallback {

    @Autowired(name = RoutePath.Moyu.MUSIC_ID)
    public String mMusicId;
    private TitleBar mTitleBar;
    private ICommentListActivityPresenter mCommentListActivityPresenter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mCommentRv;
    private CommentAdapter mCommentAdapter;
    private ShapeLinearLayout mBottomReply;
    private ReplyBottomSheetDialog mReplyBottomSheetDialog;

    @Override
    protected void initPresenter() {
        mCommentListActivityPresenter = PresenterManager.getInstance().getCommentListActivityPresenter();
        mCommentListActivityPresenter.registerViewCallback(this);
        LogUtils.d("Test","mm  =  "+mMusicId);
        mCommentListActivityPresenter.getCommentList(mMusicId);
    }

    @Override
    protected void initEvent() {
        mCommentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int id = view.getId();
                Object item = adapter.getItem(position);
                CommentBean.DataBean.ListBean commentParent = (CommentBean.DataBean.ListBean) item;
                if (id==R.id.iv_fish_pond_comment){
                    LogUtils.d("test",item.toString());
                    showReplyDialog(commentParent);
                }else if (id==R.id.tv_child_reply_msg_all){
                    MoyuServiceWrap.Singletion.INSTANCE.getHolder().launchSubComment(commentParent);
                }
            }
        });
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                OnTitleBarListener.super.onTitleClick(titleBar);
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                OnTitleBarListener.super.onRightClick(titleBar);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                mCommentListActivityPresenter.getCommentList(mMusicId);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mCommentListActivityPresenter.getCommentListMore(mMusicId);
            }
        });
        mBottomReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyDialog(null);
            }
        });
    }

    private void showReplyDialog(CommentBean.DataBean.ListBean commentParent) {
        mReplyBottomSheetDialog.show();
        mReplyBottomSheetDialog.sendListener(new ReplyBottomSheetDialog.OnSendListener() {
            @Override
            public void onSend(String v) {
                if (commentParent ==null){
                    MomentComment momentComment = new MomentComment();
                    momentComment.setSongId(mMusicId);
                    momentComment.setComment(v);
                    mCommentListActivityPresenter.postComment(momentComment);
                }else {
                    MomentSubComment momentSubComment = new MomentSubComment();
                    momentSubComment.setComment(v);
                    momentSubComment.setParentId(commentParent.getId());
                    momentSubComment.setParentUserAvatar(commentParent.getAvatar());
                    momentSubComment.setParentUserId(commentParent.getUserId());
                    momentSubComment.setParentUserName(commentParent.getUserName());
                    momentSubComment.setSongId(commentParent.getSongId());
                    mCommentListActivityPresenter.postSubComment(momentSubComment);
                }

            }
        });
    }

    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar = this.findViewById(R.id.title_bar);
        mTitleBar.setTitle("评论");
        setStatusBar();
        mRefreshLayout = this.findViewById(R.id.refresh_layout);
        mCommentRv = this.findViewById(R.id.comment_rv);
        mCommentRv.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter();
        mCommentRv.setAdapter(mCommentAdapter);

        mBottomReply = this.findViewById(R.id.comment_container);
        mReplyBottomSheetDialog = new ReplyBottomSheetDialog(this, R.style.BottomSheetDialog);
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mTitleBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.community_activity_comment_list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommentListActivityPresenter.unregisterViewCallback(this);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public String getKey() {
        return mMusicId;
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return  RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setRequestError(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void setCommentList(CommentBean data) {
        finishRefresh();
        LogUtils.d("test",data.toString());
        mCommentAdapter.getData().clear();
        mCommentAdapter.addData(data.getData().getList());
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCommentListMore(CommentBean data) {
//        LogUtils.d("test",data.toString());
        finishLoadMore();
        List<CommentBean.DataBean.ListBean> list = data.getData().getList();
        if (list!=null&&list.size()>0){
            mCommentAdapter.addData(list);
            mCommentAdapter.notifyDataSetChanged();
        }else {
            ToastUtils.showToast("暂无更多评论");
            mRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void returnComment(CommentReturnBean data) {
        ToastUtils.showToast(data.getMessage());
        mReplyBottomSheetDialog.dismiss();
    }

    private void finishRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
    }

    private void finishLoadMore() {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}