package com.czb.module_community.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.ReplyBottomSheetDialog;
import com.czb.module_community.R;
import com.czb.module_community.adapter.CommentAdapter;
import com.czb.module_community.adapter.SubCommentAdapter;
import com.czb.module_community.callback.ISubCommentActivityCallback;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.MomentComment;
import com.czb.module_community.model.bean.MomentSubComment;
import com.czb.module_community.model.bean.SubCommentBean;
import com.czb.module_community.presenter.ISubCommentActivityPresenter;
import com.czb.module_community.utils.PresenterManager;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Route(path = RoutePath.Moyu.DETAIL_MUSIC_SUB_COMMENT)
public class SubCommentActivity extends BaseActivity implements ISubCommentActivityCallback {

    @Autowired(name = RoutePath.Moyu.COMMENT)
    public CommentBean.DataBean.ListBean mComment;
    private TitleBar mTitleBar;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mSubCommentRv;
    private SubCommentAdapter mSubCommentAdapter;
    private ShapeLinearLayout mBottomReply;
    private ReplyBottomSheetDialog mReplyBottomSheetDialog;
    private ISubCommentActivityPresenter mSubCommentActivityPresenter;

    @Override
    protected void initPresenter() {
        mSubCommentActivityPresenter = PresenterManager.getInstance().getSubCommentActivityPresenter();
        mSubCommentActivityPresenter.registerViewCallback(this);
        mSubCommentActivityPresenter.getSubCommentList(mComment.getId());
    }

    @Override
    protected void initEvent() {
        mSubCommentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                SubCommentBean.DataBean.ListBean item = (SubCommentBean.DataBean.ListBean) adapter.getItem(position);
                showReplyDialog(item);
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
                mSubCommentActivityPresenter.getSubCommentList(mComment.getId());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSubCommentActivityPresenter.getSubCommentListMore(mComment.getId());
            }
        });
        mBottomReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyDialog(null);
            }
        });
    }

    private void showReplyDialog(SubCommentBean.DataBean.ListBean subComment) {
        mReplyBottomSheetDialog.show();
        mReplyBottomSheetDialog.sendListener(new ReplyBottomSheetDialog.OnSendListener() {
            @Override
            public void onSend(String v) {
                if (subComment==null){
                    MomentSubComment momentSubComment = new MomentSubComment();
                    momentSubComment.setSongId(mComment.getSongId());
                    momentSubComment.setComment(v);
                    momentSubComment.setParentUserId(mComment.getUserId());
                    momentSubComment.setParentUserName(mComment.getUserName());
                    momentSubComment.setParentUserAvatar(mComment.getAvatar());
                    momentSubComment.setParentId(mComment.getId());
                    mSubCommentActivityPresenter.postSubComment(momentSubComment);
                }else {
                    MomentSubComment momentSubComment = new MomentSubComment();
                    momentSubComment.setSongId(mComment.getSongId());
                    momentSubComment.setComment(v);
                    momentSubComment.setParentUserId(subComment.getUserId());
                    momentSubComment.setParentUserName(subComment.getUserName());
                    momentSubComment.setParentUserAvatar(subComment.getAvatar());
                    momentSubComment.setParentId(mComment.getId());
                    mSubCommentActivityPresenter.postSubComment(momentSubComment);
                }
            }
        });
    }

    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar = this.findViewById(R.id.title_bar);
        mTitleBar.setTitle("子评论");

        setStatusBar();
        mRefreshLayout = this.findViewById(R.id.refresh_layout);
        mSubCommentRv = this.findViewById(R.id.comment_rv);
        mSubCommentRv.setLayoutManager(new LinearLayoutManager(this));
        mSubCommentAdapter = new SubCommentAdapter();
        mSubCommentRv.setAdapter(mSubCommentAdapter);

        mBottomReply = this.findViewById(R.id.comment_container);
        mReplyBottomSheetDialog = new ReplyBottomSheetDialog(this, R.style.BottomSheetDialog);

        LayoutInflater from = LayoutInflater.from(this);
        View inflate = from.inflate(R.layout.community_adapter_sub_comment_item, null);
        mSubCommentAdapter.addHeaderView(inflate);

        inflate.findViewById(R.id.view_line).setVisibility(View.VISIBLE);
        inflate.findViewById(R.id.iv_fish_pond_comment).setVisibility(View.GONE);
        ImageView headUserIv = inflate.findViewById(R.id.iv_fish_pond_avatar);
        Glide.with(headUserIv.getContext())
                .load(Constants.BASE_URL_IMAGE+mComment.getAvatar())
                .placeholder(R.mipmap.ic_default_avatar)
                .circleCrop()       //圆角
                .into(headUserIv);
        TextView userNameTv = inflate.findViewById(R.id.cb_fish_pond_nick_name);
        userNameTv.setText(mComment.getUserName());
        TextView commentTv = inflate.findViewById(R.id.tv_reply_msg);
        commentTv.setText(mComment.getComment());
        TextView replyTimeTv = inflate.findViewById(R.id.tv_fish_pond_desc);
        replyTimeTv.setText(mComment.getCreateTime());

    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mTitleBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.community_activity_sub_comment;
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
        return mComment.getId();
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
    public void setSubCommentList(SubCommentBean data) {
        finishRefresh();
        List<SubCommentBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mSubCommentAdapter.getData().clear();
            mSubCommentAdapter.addData(list);
        }
    }

    @Override
    public void setSubCommentListMore(SubCommentBean data) {
        finishLoadMore();
        List<SubCommentBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mSubCommentAdapter.addData(list);
        }else {
            ToastUtils.showToast("暂无更多");
            mRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void returnComment(CommentReturnBean data) {
        if (data.getSuccess()){
            mReplyBottomSheetDialog.dismiss();
        }
        ToastUtils.showToast(data.getMessage());
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