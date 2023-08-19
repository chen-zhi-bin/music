package com.czb.module_home.ui.activity;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_home.R;
import com.czb.module_home.adapter.MusicListAdapter;
import com.czb.module_home.callback.IMusicListActivityCallback;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.czb.module_home.presenter.IMusicListActivityPresenter;
import com.czb.module_home.utils.PresenterManager;
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

@Route(path = RoutePath.Home.DETAIL_MUSIC_MORE)
public class MusicListActivity extends BaseActivity implements IMusicListActivityCallback {

    @Autowired(name = Constants.MusicType.TYPE)
    public String type;
    private static final String key = "MusicListActivity";
    private RelativeLayout mIncludeBar;
    private TextView mTvTitle;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mMusicRv;
    private MusicListAdapter mMusicListAdapter;
    private IMusicListActivityPresenter mMusicListActivityPresenter;
    private PlayerControl mControl;

    @Override
    protected void initPresenter() {
        mMusicListActivityPresenter = PresenterManager.getInstance().getMusicListActivityPresenter();
        mMusicListActivityPresenter.registerViewCallback(this);
        mMusicListActivityPresenter.getTopMusicList();
    }

    @Override
    protected void initEvent() {
        mMusicListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                TopMusicBean.DataBean.ListBean item = (TopMusicBean.DataBean.ListBean) adapter.getItem(position);
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(item.getMusicId());
                songInfo.setSongUrl(Constants.BASE_URL_MUSIC+item.getUrl());
                songInfo.setArtist(item.getSingerName());
                songInfo.setSongName(item.getMusicName());
                songInfo.setDuration(item.getDuration());
                String picId = item.getPicId();
                if (picId==null||picId.equals("")||item.equals("null")){

                }else {
                    songInfo.setSongCover(Constants.BASE_URL_IMAGE+picId);
                }
                mControl.addSongInfo(songInfo);
                mControl.playMusicByInfo(songInfo);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.setEnableLoadMore(true);
                mMusicListActivityPresenter.getTopMusicList();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mMusicListActivityPresenter.getTopMusicListMore();
            }
        });

    }

    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);         //不添加会收不到信息
        mIncludeBar = this.findViewById(R.id.include_bar);
        mIncludeBar.findViewById(R.id.tvSearch).setVisibility(View.GONE);
        mIncludeBar.findViewById(R.id.ivBack).setOnClickListener(view -> finish());
        mTvTitle = mIncludeBar.findViewById(R.id.tv_title);
        setStatusBar();
        mRefreshLayout = this.findViewById(R.id.refresh_layout);

        if (type.equals(Constants.MusicType.TYPE_TOP)){
            mTvTitle.setText("热门音乐");
        }
        mMusicRv = this.findViewById(R.id.music_rv);
        mMusicRv.setLayoutManager(new LinearLayoutManager(this));
        mMusicListAdapter = new MusicListAdapter();
        mMusicRv.setAdapter(mMusicListAdapter);
        mControl = StarrySky.with();
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mIncludeBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.home_activity_music_list;
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
    public void setErrorMessage(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void setTopMusicList(TopMusicBean data) {
        finishRefresh();
        List<TopMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mMusicListAdapter.getData().clear();
            mMusicListAdapter.addData(list);
        }
    }

    @Override
    public void setTopMusicListMore(TopMusicBean data) {
        finishLoadMore();
        List<TopMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mMusicListAdapter.addData(list);
            mMusicListAdapter.notifyDataSetChanged();
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

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}