package com.czb.module_ucenter.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.bean.db.Music;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_ucenter.R;
import com.czb.module_ucenter.adapter.HistoryAdapter;
import com.czb.module_ucenter.callback.IHistoryActivityCallback;
import com.czb.module_ucenter.persenter.IHistoryActivityPresenter;
import com.czb.module_ucenter.utils.PresenterManager;
import com.hjq.bar.TitleBar;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

@Route(path = RoutePath.Ucenter.PAGE_USER_HISTORY)
public class HistoryActivity extends BaseActivity implements IHistoryActivityCallback {

    private TitleBar mTitleBar;
    private IHistoryActivityPresenter mHistoryActivityPresenter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mHistoryRv;
    private HistoryAdapter mHistoryAdapter;
    private PlayerControl mControl;

    @Override
    protected void initPresenter() {
        mHistoryActivityPresenter = PresenterManager.getInstance().getHistoryActivityPresenter();
        mHistoryActivityPresenter.registerViewCallback(this);
        mHistoryActivityPresenter.getMusicList();
    }

    @Override
    protected void initEvent() {
        mHistoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Music item = (Music)adapter.getItem(position);
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(item.getId());
                songInfo.setSongName(item.getSongName());
                songInfo.setArtist(item.getArtist());
                songInfo.setSongCover(item.getSongCover());
                songInfo.setDuration(item.getDuration());
                songInfo.setSongUrl(item.getSongUrl());
                mControl.playMusicByInfo(songInfo);
                ToastUtils.showToast("正在播放:"+item.getSongName());
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                mHistoryActivityPresenter.getMusicList();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHistoryActivityPresenter.getMusicListMore();
            }
        });
    }

    @Override
    protected void initView() {
        mTitleBar = this.findViewById(R.id.history_title_bar);
        setStatusBar();
        mRefreshLayout = this.findViewById(R.id.refresh_layout);
        mHistoryRv = this.findViewById(R.id.music_rv);
        mHistoryRv.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter();
        mHistoryRv.setAdapter(mHistoryAdapter);
        mControl = StarrySky.with();
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mTitleBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.ucenter_activity_history;
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
    public void setMusicList(List<Music> data) {
        finishRefresh();
        if (data != null&&data.size()>0) {
            mHistoryAdapter.getData().clear();
            mHistoryAdapter.addData(data);
        }else {
            ToastUtils.showToast("暂无历史");
        }
    }

    @Override
    public void setMusicListMore(List<Music> data) {
        finishLoadMore();
        if (data != null&&data.size()>0) {
            mHistoryAdapter.addData(data);
        }else {
            ToastUtils.showToast("暂无更多");
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