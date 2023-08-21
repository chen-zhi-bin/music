package com.czb.module_home.ui.fragment;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.bean.TitleMultiBean;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.home.wrap.HomeServiceWrap;
import com.czb.module_base.common.service.search.SearchServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.MusicBottomSheetDialog;
import com.czb.module_home.R;
import com.czb.module_home.adapter.HomeAdapter;
import com.czb.module_home.adapter.HomeBannerAdapter;
import com.czb.module_home.adapter.SingerAdapter;
import com.czb.module_home.callback.IHomeMainFragmentCallback;
import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.MusicianBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.czb.module_home.presenter.IHomeMainFragmentPresenter;
import com.czb.module_home.utils.PresenterManager;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.lzx.starrysky.OnPlayerEventListener;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.lzx.starrysky.manager.PlaybackStage;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import kotlin.collections.CollectionsKt;

public class HomeMainFragment extends BaseFragment implements IHomeMainFragmentCallback {

    public static final String key = "HomeMainFragment";
    private HomeAdapter mAdapter;
    private RecyclerView mRvList;
    private View lastRvView = null;
//    private HomeListFragmentPresenterImpl mHomeListFragmentPresenter;
    private SmartRefreshLayout mRefreshLayout;

    private Banner mBanner;
    private IHomeMainFragmentPresenter mHomeMainFragmentPresenter;
    private List<MultiItemEntity> mTopMusicList = CollectionsKt.mutableListOf();
    private List<MultiItemEntity> mRecommendMusicList = CollectionsKt.mutableListOf();
    private int currentRecommendPage = 1;
    private ImageView mBroadcastMusicIv;
    private ImageView mPlayOrPauseIv;
    private ImageView mMusicBroadcastListIv;
    private TextView mMusicBroadcastName;
    private PlayerControl mControl;
    private List<SongInfo> controlMusicList = new ArrayList<>();

    private MusicBottomSheetDialog mMusicBottomSheetDialog;
    private RecyclerView mSingerRv;
    private SingerAdapter mSingerAdapter;
    private LinearLayout mLinearLayout;
    private TextView mSearchTv;
    private ImageView mSearchIv;

    @Override
    protected int getRootViewResId() {
        return R.layout.home_main_fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mSearchIv = rootView.findViewById(R.id.iv_search);
        mSearchTv = rootView.findViewById(R.id.homeSearchTv);
        mLinearLayout = rootView.findViewById(R.id.layout);
        mBroadcastMusicIv = rootView.findViewById(R.id.broadcast_iv);
        mPlayOrPauseIv = rootView.findViewById(R.id.broadcast_play_iv);
        mMusicBroadcastListIv = rootView.findViewById(R.id.broadcast_list_iv);
        mMusicBroadcastName = rootView.findViewById(R.id.broadcast_music_name);
        mRvList = rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(BaseApplication.getAppContext()));
        mAdapter = new HomeAdapter();
        mRvList.setAdapter(mAdapter);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        LayoutInflater from = LayoutInflater.from(BaseApplication.getAppContext());
        View inflate = from.inflate(R.layout.modulehome_home_banner_layout, null);
        mAdapter.addHeaderView(inflate);
        mBanner = inflate.findViewById(R.id.banner);

        View inflateSinger = from.inflate(R.layout.home_adapter_singer, null);
        mAdapter.addHeaderView(inflateSinger);
        mSingerRv = inflateSinger.findViewById(R.id.singer_rv);
        mSingerRv.setLayoutManager(new GridLayoutManager(BaseApplication.getAppContext(),1,LinearLayoutManager.HORIZONTAL,false));
        mSingerAdapter = new SingerAdapter();
        mSingerRv.setAdapter(mSingerAdapter);

        setStatusBar();
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            initData();
            mRefreshLayout.setEnableLoadMore(true);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mHomeMainFragmentPresenter.getRecommendMore());
        mControl = StarrySky.with();
        mControl.prepare();

        mMusicBottomSheetDialog = new MusicBottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(requireActivity());
        StatusBarUtil.darkMode(requireActivity(),true);
        StatusBarUtil.setPaddingSmart(requireContext(),mRefreshLayout);
        StatusBarUtil.setPaddingSmart(requireContext(),mLinearLayout);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mMusicBroadcastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeServiceWrap.Singletion.INSTANCE.getHolder().launchMusic();
            }
        });
        MutableLiveData<PlaybackStage> playbackStageMutableLiveData = mControl.playbackState();
        playbackStageMutableLiveData.observe(this, new Observer<PlaybackStage>() {
            @Override
            public void onChanged(PlaybackStage playbackStage) {
                switch (playbackStage.getStage()){
                    case PlaybackStage.PLAYING:
                        LogUtils.d("test","正在播放");
                        mPlayOrPauseIv.setImageResource(R.mipmap.pause);
                        mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                        break;
                    case PlaybackStage.PAUSE:
                        LogUtils.d("test","暂停播放");
                        mPlayOrPauseIv.setImageResource(R.mipmap.play);
                        mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
                        break;
                    case PlaybackStage.ERROR:

                        break;

                }
            }
        });

        mSearchIv.setOnClickListener(v -> SearchServiceWrap.Singletion.INSTANCE.getHolder().launchSearch());
        mSearchTv.setOnClickListener(v -> SearchServiceWrap.Singletion.INSTANCE.getHolder().launchSearch());
        mLinearLayout.setOnClickListener(v -> SearchServiceWrap.Singletion.INSTANCE.getHolder().launchSearch());
        mSingerAdapter.setOnItemClickListener((adapter, view, position) -> HomeServiceWrap.Singletion.INSTANCE.getHolder().launchMusician((MusicianBean.DataBean.ListBean)adapter.getItem(position)));
        mMusicBroadcastListIv.setOnClickListener(v -> showMusicListDialog());
        mControl.addPlayerEventListener(new OnPlayerEventListener() {
            @Override
            public void onPlaybackStageChange(@NotNull PlaybackStage playbackStage) {
                if (playbackStage.isStop()) {
                    //播放停止

                }else {
                    //播放完
                    int nowPlayingIndex = mControl.getNowPlayingIndex();
                    SongInfo songInfo = mControl.getPlayList().get(nowPlayingIndex);
                    mMusicBroadcastName.setText(songInfo.getSongName());
                    String songCover = songInfo.getSongCover();
                    if (songCover ==null|| songCover.equals("")|| songCover.equals("")) {
                        mBroadcastMusicIvSetDefaultIv();
                    }else {
                        Glide.with(mBroadcastMusicIv.getContext())
                                .load(songCover)
                                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                                .circleCrop()       //圆角
                                .into(mBroadcastMusicIv);
                    }
//                    if (lastRvView != null) {
//                        lastRvView.setBackground(
//                                ContextCompat.getDrawable(BaseApplication.getAppContext(), com.czb.module_base.R.drawable.shape_linearlayout_white)
//                        );
//                        lastRvView = null;
//                    }
                }
            }
        },"1");
        mPlayOrPauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag()==null||v.getTag().equals("")||v.getTag().equals("null")) {
                    return;
                }
                if (v.getTag().equals(Constants.MusicState.PLAY)) {
                    mPlayOrPauseIv.setImageResource(R.mipmap.play);
                    mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
                    mControl.pauseMusic();
                }else {
                    mPlayOrPauseIv.setImageResource(R.mipmap.pause);
                    mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                    mControl.restoreMusic();
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                LogUtils.d("test","total ==> "+adapter.getItemCount()+"pos =="+position);
                Object item = adapter.getItem(position);
                int id = view.getId();

//                if (lastRvView!=null) {
//
//                    lastRvView.setBackground(
//                            ContextCompat.getDrawable(BaseApplication.getAppContext(), R.drawable.shape_linearlayout_white)
//                    );
//                }
                if (id==R.id.iv_more){
                    TitleMultiBean titleItem = (TitleMultiBean) item;
                    HomeServiceWrap.Singletion.INSTANCE.getHolder().launchMoreMusic(titleItem.getType());
                    return;
                } else if (id ==R.id.tv_title_right){
                    TitleMultiBean titleItem = (TitleMultiBean) item;

                    List<SongInfo> songInfoList = new ArrayList<>();
                    if (titleItem.getType().equals(Constants.MusicType.TYPE_TOP)){
                        for (int i=1;i<mTopMusicList.size();i++){
                            TopMusicBean.DataBean.ListBean topMusicItem = (TopMusicBean.DataBean.ListBean) mTopMusicList.get(i);
                            SongInfo temp = new SongInfo();
                            temp.setSongName(topMusicItem.getMusicName());
                            String singerName = topMusicItem.getSingerName();
                            if (singerName.equals("")||singerName.equals("null")) {
                                singerName="未知歌手";
                            }
                            temp.setArtist(singerName);
                            temp.setSongUrl(Constants.BASE_URL_MUSIC+topMusicItem.getUrl());
                            String picId = topMusicItem.getPicId();
                            if (picId == null||picId.equals("")||picId.equals("null")) {

                            }else {
                                temp.setSongCover(Constants.BASE_URL_IMAGE+picId);
                            }
                            temp.setDuration(topMusicItem.getDuration());
                            temp.setSongId(topMusicItem.getMusicId());
                            songInfoList.add(temp);
                        }
                        mControl.playMusic(songInfoList,0);
                    }else if (titleItem.getType().equals(Constants.MusicType.TYPE_RECOMMEND)){
                        for (int i=1;i<mRecommendMusicList.size();i++){
                            RecommendMusicBean.DataBean.ListBean recommendMusicItem = (RecommendMusicBean.DataBean.ListBean) mRecommendMusicList.get(i);
                            SongInfo temp = new SongInfo();
                            temp.setSongName(recommendMusicItem.getMusicName());
                            String singerName = recommendMusicItem.getSingerName();
                            if (singerName.equals("")||singerName.equals("null")) {
                                singerName="未知歌手";
                            }
                            temp.setArtist(singerName);
                            temp.setSongUrl(Constants.BASE_URL_MUSIC+recommendMusicItem.getUrl());
                            String picId = recommendMusicItem.getPicId();
                            if (picId == null||picId.equals("")||picId.equals("null")) {

                            }else {
                                temp.setSongCover(Constants.BASE_URL_IMAGE+picId);
                            }
                            temp.setDuration(recommendMusicItem.getDuration());
                            temp.setSongId(recommendMusicItem.getMusicId());
                            songInfoList.add(temp);
                        }
                        mControl.playMusic(songInfoList,0);
                    }


                    mPlayOrPauseIv.setImageResource(R.mipmap.pause);
                    mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                    return;
                }
//                view.setBackground(
//                        ContextCompat.getDrawable(BaseApplication.getAppContext(), com.czb.module_base.R.drawable.shape_linearlayout_blue)
//                );
                lastRvView = view;
                SongInfo songInfo = new SongInfo();
                if (id==R.id.top_layout_item) {
                    TopMusicBean.DataBean.ListBean temp = (TopMusicBean.DataBean.ListBean)item;
                    songInfo.setSongId(temp.getMusicId());
                    songInfo.setSongUrl(Constants.BASE_URL_MUSIC+temp.getUrl());
                    songInfo.setSongName(temp.getMusicName());
                    String singerName = temp.getSingerName();
                    if (singerName == null||singerName.equals("")||singerName.equals("null")) {
                        songInfo.setArtist("未知歌手");
                    }else {
                        songInfo.setArtist(temp.getSingerName());
                    }
                    String picId = temp.getPicId();
                    if (picId == null||picId.equals("")||picId.equals("null")) {
                        mBroadcastMusicIvSetDefaultIv();
                    }else {
                        songInfo.setSongCover(Constants.BASE_URL_IMAGE+picId);
                        Glide.with(mBroadcastMusicIv.getContext())
                                .load(Constants.BASE_URL_IMAGE+picId)
                                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                                .circleCrop()       //圆角
                                .into(mBroadcastMusicIv);
                    }
                    songInfo.setDuration(temp.getDuration());
                }else {
                    RecommendMusicBean.DataBean.ListBean temp = (RecommendMusicBean.DataBean.ListBean)item;
                    LogUtils.d("Test","info ==>"+temp.toString());
                    songInfo.setSongId(temp.getMusicId());
                    songInfo.setSongUrl(Constants.BASE_URL_MUSIC+temp.getUrl());
                    songInfo.setSongName(temp.getMusicName());
                    String singerName = temp.getSingerName();
                    if (singerName == null||singerName.equals("")||singerName.equals("null")) {
                        songInfo.setArtist("未知歌手");
                    }else {
                        songInfo.setArtist(temp.getSingerName());
                    }
                    String picId = temp.getPicId();
                    if (picId == null||picId.equals("")||picId.equals("null")) {
                        mBroadcastMusicIvSetDefaultIv();
                    }else {
                        songInfo.setSongCover(Constants.BASE_URL_IMAGE+picId);
                        Glide.with(mBroadcastMusicIv.getContext())
                                .load(Constants.BASE_URL_IMAGE+picId)
                                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                                .circleCrop()       //圆角
                                .into(mBroadcastMusicIv);
                    }
                    songInfo.setDuration(temp.getDuration());
                }

                mControl.addSongInfo(songInfo);
                mControl.playMusicById(songInfo.getSongId());
                mMusicBroadcastName.setText(songInfo.getSongName());
                mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                mPlayOrPauseIv.setImageResource(R.mipmap.pause);
            }
        });
        mMusicBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mControl.isPlaying()) {
                    mPlayOrPauseIv.setImageResource(R.mipmap.pause);
                    mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                    int nowPlayingIndex = mControl.getNowPlayingIndex();
                    List<SongInfo> playList = mControl.getPlayList();
//                    SongInfo songInfo = mControl.getNowPlayingSongInfo();
                    SongInfo songInfo = playList.get(nowPlayingIndex);
//                    LogUtils.d("test","diss  ==>"+playList.size());
//                    LogUtils.d("test","diss  ==>"+nowPlayingIndex);
//                    LogUtils.d("test","diss  ==>"+songInfo.getSongCover());
//                    LogUtils.d("test","diss  ==>"+songInfo.getSongName());
//                    LogUtils.d("test","diss  ==>"+songInfo.getArtist());
                    mMusicBroadcastName.setText(songInfo.getSongName());
                    String songCover = songInfo.getSongCover();
                    if (songCover==null||songCover.equals("")||songCover.equals("null")) {
                        Glide.with(mBroadcastMusicIv.getContext())
                                .load(R.mipmap.iv_default_music)
                                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                                .circleCrop()       //圆角
                                .into(mBroadcastMusicIv);
                    }else {
                        Glide.with(mBroadcastMusicIv.getContext())
                                .load(songCover)
                                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                                .circleCrop()       //圆角
                                .into(mBroadcastMusicIv);
                    }
                }else {
                    mPlayOrPauseIv.setImageResource(R.mipmap.play);
                    mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
                }

            }
        });
    }

    private void showMusicListDialog() {
        mMusicBottomSheetDialog.show();
//        mMusicBottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,1200);
        mMusicBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void mBroadcastMusicIvSetDefaultIv() {
        Glide.with(mBroadcastMusicIv.getContext())
                .load(R.mipmap.iv_default_music)
                .placeholder(R.mipmap.iv_default_music)        //加载中显示的图片（加载成功前）
                .circleCrop()       //圆角
                .into(mBroadcastMusicIv);
    }

    @Override
    protected void initPresenter() {
        mHomeMainFragmentPresenter = PresenterManager.getInstance().getHomeMainFragmentPresenter();
        mHomeMainFragmentPresenter.registerViewCallback(this);
        initData();
    }

    private void initData() {
        mHomeMainFragmentPresenter.getBanner();
//        mHomeMainFragmentPresenter.getTopMusic(1);
        mHomeMainFragmentPresenter.getRecommendSingList();
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
    }

    @Override
    public void setBanner(BannerBean data) {
        LogUtils.d("test","banner == "+data.toString());
        if (data != null) {
            HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(data.getData().getList());
            mBanner.setAdapter(bannerAdapter);
            mBanner.addBannerLifecycleObserver(this);
            //画廊效果
            mBanner.setBannerGalleryEffect(16,6,0.8f);

            mBanner.setIndicator(new CircleIndicator(requireContext()));
            mBanner.setIndicatorSelectedColor(
                    ContextCompat.getColor(
                            getContext(),
                            R.color.colorPrimary
                    )
            );
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    //点击事件，目前没有
                }
            });
        }
    }

    @Override
    public void setTopMusic(TopMusicBean data) {
        mTopMusicList.clear();
        mTopMusicList.add(new TitleMultiBean("顶置音乐",Constants.MusicType.TYPE_TOP));
        List<TopMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (TopMusicBean.DataBean.ListBean listBean : topList) {
            mTopMusicList.add(listBean);
        }
        mAdapter.addData(mTopMusicList);
        mHomeMainFragmentPresenter.getRecommend(currentRecommendPage);
    }

    @Override
    public void setRecommendMusic(RecommendMusicBean data) {
        finishRefresh();
        mRecommendMusicList.clear();
        mRecommendMusicList.add(new TitleMultiBean("推荐音乐",Constants.MusicType.TYPE_RECOMMEND));
        List<RecommendMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (RecommendMusicBean.DataBean.ListBean listBean : topList) {
            mRecommendMusicList.add(listBean);
        }
        mAdapter.addData(mRecommendMusicList);
    }

    private void finishRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void setRecommendMusicMore(RecommendMusicBean data) {
        finishLoadMore();
        if (data.getData().getList()==null||data.getData().getList().size()==0){
            ToastUtils.showToast("暂无更多");
            mRefreshLayout.setEnableLoadMore(false);
            return;
        }
        List<RecommendMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (RecommendMusicBean.DataBean.ListBean listBean : topList) {
            mRecommendMusicList.add(listBean);
        }
        mAdapter.addData(mRecommendMusicList);
    }

    @Override
    public void setRecommendSinger(MusicianBean data) {
        mSingerAdapter.addData(data.getData().getList());
        mHomeMainFragmentPresenter.getTopMusic(1);
    }

    private void finishLoadMore() {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mControl.isPlaying()) {
            SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
            mMusicBroadcastName.setText(nowPlayingSongInfo.getSongName());
            mPlayOrPauseIv.setImageResource(R.mipmap.pause);
            mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
        }else {
            mPlayOrPauseIv.setImageResource(R.mipmap.play);
            mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
        }
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
}
