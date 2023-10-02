package com.czb.module_ucenter.ui.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_ucenter.R;
import com.czb.module_ucenter.adapter.CollectionListAdapter;
import com.czb.module_ucenter.callback.ICollectionActivityCallback;
import com.czb.module_ucenter.model.bean.CollectionListBean;
import com.czb.module_ucenter.persenter.ICollectionListActivityPresenter;
import com.czb.module_ucenter.utils.PresenterManager;
import com.hjq.bar.OnTitleBarListener;
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

@Route(path = RoutePath.Ucenter.PAGE_USER_COLLECTION)
public class CollectionListActivity extends BaseActivity implements ICollectionActivityCallback {

    @Autowired(name = RoutePath.Ucenter.PARAMS_USER_ID)
    public String mUserId;
    private TitleBar mTitleBar;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mListRv;
    private PlayerControl mControl;
    private ICollectionListActivityPresenter mCollectionListActivityPresenter;
    private CollectionListAdapter mCollectionListAdapter;

    @Override
    protected void initPresenter() {
        mCollectionListActivityPresenter = PresenterManager.getInstance().getCollectionListActivityPresenter();
        mCollectionListActivityPresenter.registerViewCallback(this);
        mCollectionListActivityPresenter.getCollectionList();
    }

    @Override
    protected void initEvent() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                OnTitleBarListener.super.onLeftClick(titleBar);
                finish();
            }
        });
        mCollectionListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CollectionListBean.DataBean.ListBean item = (CollectionListBean.DataBean.ListBean) adapter.getItem(position);
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(item.getMusicId());
                songInfo.setSongName(item.getName());
                songInfo.setArtist(item.getSingerName());
                songInfo.setSongCover(item.getPicId());
                songInfo.setDuration(Long.parseLong(item.getDuration()));
                songInfo.setSongUrl(Constants.BASE_URL_MUSIC+item.getUrl());
                mControl.playMusicByInfo(songInfo);
                ToastUtils.showToast("正在播放:"+item.getName());
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                mCollectionListActivityPresenter.getCollectionList();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mCollectionListActivityPresenter.getCollectionListMore();
            }
        });
    }

    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar = this.findViewById(R.id.collection_title_bar);
        mRefreshLayout = this.findViewById(R.id.refresh_layout);
        mListRv = this.findViewById(R.id.music_rv);
        mListRv.setLayoutManager(new LinearLayoutManager(this));
        mCollectionListAdapter = new CollectionListAdapter();
        mListRv.setAdapter(mCollectionListAdapter);
        mControl = StarrySky.with();
        setStatusBar();
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mTitleBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.ucenter_activity_collection_list;
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
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setCollectionListData(CollectionListBean data) {
        finishRefresh();
        List<CollectionListBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null && list.size()>0) {
            mCollectionListAdapter.getData().clear();
            mCollectionListAdapter.addData(data.getData().getList());
        }else {
            ToastUtils.showToast("暂无更多");
        }
    }

    @Override
    public void setCollectionListDataMore(CollectionListBean data) {
        finishLoadMore();
        List<CollectionListBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mCollectionListAdapter.addData(list);
        }else {
            ToastUtils.showToast("暂无更多");
        }
    }

    @Override
    public void setErrorMessage(String s) {
        ToastUtils.showToast(s);
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