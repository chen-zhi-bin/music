package com.czb.module_community.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.moyu.wrap.MoyuServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_community.R;
import com.czb.module_community.adapter.CommentListAdapter;
import com.czb.module_community.callback.IMoyuMainFragmentCallback;
import com.czb.module_community.model.bean.CommentAndMusicBean;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.presenter.IMoyuMainFragmentPresenter;
import com.czb.module_community.utils.PresenterManager;
import com.hjq.bar.TitleBar;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MoyuMainFragment extends BaseFragment implements IMoyuMainFragmentCallback {


    private TitleBar mTitleBar;
    private IMoyuMainFragmentPresenter mMoyuMainFragmentPresenter;
    public static final String key = "MoyuMainFragment";
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mCommentRv;
    private CommentListAdapter mCommentListAdapter;
    private PlayerControl mControl;

    @Override
    protected int getRootViewResId() {
        return R.layout.community_main_fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mTitleBar = rootView.findViewById(R.id.community_titlebar);
        mTitleBar.setLeftIcon(null);
        setStatusBar();
        mRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mCommentRv = rootView.findViewById(R.id.comment_rv);
        mCommentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentListAdapter = new CommentListAdapter();
        mCommentRv.setAdapter(mCommentListAdapter);
        mControl = StarrySky.with();
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(getActivity());
        StatusBarUtil.darkMode(getActivity(),true);
        StatusBarUtil.setPaddingSmart(getContext(),mTitleBar);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mMoyuMainFragmentPresenter = PresenterManager.getInstance().getMoyuMainFragmentPresenter();
        mMoyuMainFragmentPresenter.registerViewCallback(this);
        mMoyuMainFragmentPresenter.getCommentList();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mCommentListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CommentAndMusicBean.DataBean.ListBean item = (CommentAndMusicBean.DataBean.ListBean)adapter.getItem(position);
                CommentBean.DataBean.ListBean comment = new CommentBean.DataBean.ListBean();
                comment.setId(item.getId());
                comment.setAvatar(item.getAvatar());
                comment.setUserName(item.getUserName());
                comment.setUserId(item.getUserId());
                comment.setSongId(item.getSongId());
                comment.setComment(item.getComment());
                comment.setCreateTime(item.getCommentTime());
                MoyuServiceWrap.Singletion.INSTANCE.getHolder().launchSubComment(comment);
            }
        });
        mCommentListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int id = view.getId();
                CommentAndMusicBean.DataBean.ListBean item = (CommentAndMusicBean.DataBean.ListBean)adapter.getItem(position);
                if (id==R.id.music_info) {
                    SongInfo songInfo = new SongInfo();
                    songInfo.setSongId(item.getMusicId());
                    songInfo.setSongUrl(Constants.BASE_URL_MUSIC+item.getUrl());
                    songInfo.setArtist(item.getSingerName());
                    songInfo.setDuration(Long.parseLong(item.getDuration()));
                    songInfo.setSongCover(Constants.BASE_URL_IMAGE+item.getPicId());
                    songInfo.setSongName(item.getMusicName());
                    mControl.addSongInfo(songInfo);
                    mControl.playMusicByInfo(songInfo);
                    ToastUtils.showToast("正在播放："+item.getMusicName());
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                mMoyuMainFragmentPresenter.getCommentList();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mMoyuMainFragmentPresenter.getCommentListMore();
            }
        });
    }

    @Override
    public void onError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return  RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setRequestError(String message) {
        ToastUtils.showToast(message);
        setupState(State.ERROR);
    }

    @Override
    public void setCommentList(CommentAndMusicBean data) {
       setupState(State.SUCCESS);
       finishRefresh();
        List<CommentAndMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mCommentListAdapter.getData().clear();
            mCommentListAdapter.addData(list);
        }
    }

    @Override
    public void setCommentListMore(CommentAndMusicBean data) {
        finishLoadMore();
        List<CommentAndMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mCommentListAdapter.getData().clear();
            mCommentListAdapter.addData(list);
        }else {
            ToastUtils.showToast("暂无更多");
            mRefreshLayout.setEnableLoadMore(false);
        }
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
