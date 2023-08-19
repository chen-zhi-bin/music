package com.czb.module_home.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.callback.LoadingCallback;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_home.R;
import com.czb.module_home.adapter.MusicianAdapter;
import com.czb.module_home.callback.IMusicianActivityCallback;
import com.czb.module_home.model.bean.MusicianBean;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.czb.module_home.presenter.IMusicianActivityPresenter;
import com.czb.module_home.utils.PresenterManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
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

@Route(path = RoutePath.Home.DETAIL_MUSICIAN_HOME)
public class MusicianActivity extends BaseActivity implements IMusicianActivityCallback {

    private static final String key = "MusicianActivity";

    @Autowired(name = RoutePath.Home.MUSICIAN)
    public MusicianBean.DataBean.ListBean mMusician;

    private RelativeLayout mIncludeBar;
    private TextView mTvTitle;
    private SmartRefreshLayout mRefreshLayout;
    private MusicianAdapter mMusicianAdapter;
    private RecyclerView mMusicianRv;
    private IMusicianActivityPresenter mMusicianActivityPresenter;
    private PlayerControl mControl;

    @Override
    protected void initPresenter() {
        mControl = StarrySky.with();
        mMusicianActivityPresenter = PresenterManager.getInstance().getMusicianActivityPresenter();
        mMusicianActivityPresenter.registerViewCallback(this);
        mMusicianActivityPresenter.getMusicByMusician(mMusician.getId(),1);
    }

    @Override
    protected void initEvent() {
        initListener();
    }

    private void initListener() {
        mMusicianAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MusicianMusicBean.DataBean.ListBean item = (MusicianMusicBean.DataBean.ListBean) adapter.getItem(position);
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
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mMusicianActivityPresenter.getMusicByMusicianMore(mMusician.getId());
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mMusicianActivityPresenter.getMusicByMusician(mMusician.getId(),1);
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
        mTvTitle.setText(mMusician.getName());

        mRefreshLayout = this.findViewById(R.id.refresh_layout);
        mMusicianRv = this.findViewById(R.id.musician_rv);
        mMusicianAdapter = new MusicianAdapter();
        mMusicianRv.setLayoutManager(new LinearLayoutManager(this));
        mMusicianRv.setAdapter(mMusicianAdapter);
        setStatusBar();
        LayoutInflater from = LayoutInflater.from(BaseApplication.getAppContext());
        View inflate = from.inflate(R.layout.home_musician_info, null);
        mMusicianAdapter.addHeaderView(inflate);

        ImageView musicianAvatarIv = inflate.findViewById(R.id.musician_avatar_iv);
        TextView musicianSex = inflate.findViewById(R.id.sex);
        TextView musicianIntroduction = inflate.findViewById(R.id.musician_introduction);

//设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options= RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(musicianAvatarIv.getContext())
                .load(Constants.BASE_URL_IMAGE+mMusician.getPicId())
                .placeholder(R.mipmap.ic_launcher_round)        //加载中显示的图片（加载成功前）
                .apply(options)
                .into(musicianAvatarIv);
        String sex = "";
        switch (mMusician.getSex()){
            case Constants.MusicianSex.WOMEN:
                sex = "女";
                break;
            case Constants.MusicianSex.MAN:
                sex="男";
                break;
            case Constants.MusicianSex.COMBINATION:
                sex = "组合";
                break;
            case Constants.MusicianSex.UNKNOWN:
                sex = "未知";
                break;
        }
        musicianSex.setText(sex.trim());
        String introduction = mMusician.getIntroduction();
        if (introduction.equals("")|| introduction.equals("null")|| introduction ==null){
            introduction = "";
        }
        musicianIntroduction.setText(introduction.trim());

    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mIncludeBar);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.home_activity_musician;
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
    public void setErrorMessage(String s) {
        ToastUtils.showToast(s);
    }

    @Override
    public void setMusicBean(MusicianMusicBean data) {
        finishRefresh();
        List<MusicianMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mMusicianAdapter.getData().clear();
            mMusicianAdapter.addData(list);
        }
    }

    @Override
    public void setMusicBeanMore(MusicianMusicBean data) {
        finishLoadMore();
        List<MusicianMusicBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null&&list.size()>0) {
            mMusicianAdapter.addData(list);
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