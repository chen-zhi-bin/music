package com.czb.module_home.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.callback.LoadingCallback;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.moyu.wrap.MoyuServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_home.R;
import com.czb.module_home.callback.IMusicPlayActivityCallback;
import com.czb.module_home.model.bean.MusicAndMusicianInfoBean;
import com.czb.module_home.presenter.IMusicPlayActivityPresenter;
import com.czb.module_home.utils.PresenterManager;
import com.example.viewlibrary.util.AudioVisualConverter;
import com.example.viewlibrary.util.BlurUtil;
import com.example.viewlibrary.util.ImageUtil;
import com.example.viewlibrary.view.JinyunView;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.lzx.starrysky.OnPlayProgressListener;
import com.lzx.starrysky.OnPlayerEventListener;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.lzx.starrysky.manager.PlaybackStage;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.wcy.lrcview.LrcView;
import permison.PermissonUtil;
import permison.listener.PermissionListener;

@Route(path = RoutePath.Home.DETAIL_MUSIC)
public class MusicPlayActivity extends BaseActivity implements IMusicPlayActivityCallback {

    public static final String key = "MusicPlayActivity";
    private SimpleDateFormat mMinFormt = new SimpleDateFormat("mm:ss");
    private ImageView mPlayOrPauseIv;
    private ImageView mLastMusicIv;
    private ImageView mNextMusicIv;
    private SeekBar mDurationBar;
    private TextView mTotalDuration;
    private TextView mCurrentPosition;
    private PlayerControl mControl;
    private int mCurrentProgress = 0;
    private boolean mIsUserTouchProgressBar = false;
    private ConstraintLayout mHeaderLayout;
    private TextView mMusicNameTv;
    private TextView mMusicianNameTv;
//    private LyricViewX mLyricViewX;
    private IMusicPlayActivityPresenter mMusicPlayActivityPresenter;
    private ImageView mCommentIv;
    private LrcView mLrcView;
    private RelativeLayout mJingyunLayout;
    private ImageView mIvShowPic;
    private JinyunView mJingyunView;
    ObjectAnimator objectAnimator;
    private ImageView mBgIv;
    private LoadService mLoadService;
    private String mPlayerEventTag = "2";
    private String mCurrentMusicId = null;

    @Override
    protected void initPresenter() {
        mMusicPlayActivityPresenter = PresenterManager.getInstance().getMusicPlayActivityPresenter();
        mMusicPlayActivityPresenter.registerViewCallback(this);
        getLyric();
    }

    private void getLyric() {
        SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
        mMusicNameTv.setText(nowPlayingSongInfo.getSongName());
        mMusicianNameTv.setText(nowPlayingSongInfo.getArtist());
        if (nowPlayingSongInfo != null) {
            mMusicPlayActivityPresenter.getMusicInfo(nowPlayingSongInfo.getSongId());
            mCurrentMusicId = nowPlayingSongInfo.getSongId();
        }
    }

    @Override
    protected void initEvent() {
        mLastMusicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControl.skipToPrevious();
            }
        });
        mNextMusicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControl.skipToNext();
            }
        });
        MutableLiveData<PlaybackStage> playbackStageMutableLiveData = mControl.playbackState();
        playbackStageMutableLiveData.observe(this, new Observer<PlaybackStage>() {
            @Override
            public void onChanged(PlaybackStage playbackStage) {
                switch (playbackStage.getStage()){
                    case PlaybackStage.PLAYING:
                        LogUtils.d("test","正在播放");
                        if (!mLrcView.hasLrc()) {
                            getLyric();
                        }
                        mPlayOrPauseIv.setImageResource(R.mipmap.pause_white);
                        mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
                        break;
                    case PlaybackStage.PAUSE:
                        LogUtils.d("test","暂停播放");
                        mPlayOrPauseIv.setImageResource(R.mipmap.play_white);
                        mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
                        break;
                    case PlaybackStage.ERROR:
                        break;

                }
            }
        });
        mPlayOrPauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SongInfo> playList = mControl.getPlayList();
                if (playList==null||playList.size()==0) {
                    return;
                }
                if (mControl.isPlaying()) {
                    mControl.pauseMusic();
                    onPause();
                }else if (mControl.isPaused()){
                    mControl.restoreMusic();
                    onResume();
                }
            }
        });
        mControl.addPlayerEventListener(new OnPlayerEventListener() {
            @Override
            public void onPlaybackStageChange(@NonNull PlaybackStage playbackStage) {
                if (!playbackStage.isStop()) {
//                    下一首
                    SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
                    mJingyunView.setVisibility(View.VISIBLE);
                    mLoadService.showCallback(LoadingCallback.class);
                    initMusicInfo(nowPlayingSongInfo,nowPlayingSongInfo.getSongCover());
                    String songId = nowPlayingSongInfo.getSongId();
                    if (!mCurrentMusicId.equals(songId)) {
                        mCurrentMusicId = songId;
                        getLyric();
                    }
//                    getBitmap();
                }
            }
        },mPlayerEventTag);
//        mLyricViewX.setDraggable(true, new OnPlayClickListener() {
//            @Override
//            public boolean onPlayClick(long l) {
//               //点击左边箭头时音乐跳转
//                long s = l / 1000;
//                mCurrentPosition.setText(mMinFormt.format(s));
//                mLyricViewX.updateTime(l,true);
//                mDurationBar.setProgress(Integer.parseInt(s+""));
//                mControl.seekTo(l,true);
//                LyricEntry currentLineLyricEntry = mLyricViewX.getCurrentLineLyricEntry();
//                return true;
//            }
//        });
        mIvShowPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mJingyunLayout.getVisibility()==View.VISIBLE){
                    mJingyunLayout.setVisibility(View.GONE);
                    mLrcView.setVisibility(View.VISIBLE);
                }
            }
        });
        mLrcView.setOnTapListener(new LrcView.OnTapListener() {
            @Override
            public void onTap(LrcView view, float x, float y) {
                if (mLrcView.getVisibility()==View.VISIBLE){
                    mJingyunLayout.setVisibility(View.VISIBLE);
                    mLrcView.setVisibility(View.GONE);
                }
            }
        });
        mLrcView.setDraggable(true, new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(LrcView view, long time) {
                //点击左边箭头时音乐跳转
                long s = time / 1000;
                mCurrentPosition.setText(mMinFormt.format(s));
                mControl.seekTo(time,true);
//                mLrcView.updateTime(time);
                mDurationBar.setProgress(Integer.parseInt(s+""));
                return true;
            }
        });
        //音乐进度监听
        mControl.setOnPlayProgressListener(new OnPlayProgressListener() {
            @Override
            public void onPlayProgress(long currPos, long duration) {
                mCurrentPosition.setText(mMinFormt.format(currPos));
                long s = currPos / 1000;
                mDurationBar.setProgress(Integer.parseInt(s+""));

                //刷新歌词
//                mLyricViewX.updateTime(currPos,true);
//                LogUtils.d("test","currPos ==>"+currPos);
//                mLrcView.updateTime(currPos);
                    mLrcView.updateTime(currPos);
            }
        });

        mDurationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mCurrentProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgressBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mIsUserTouchProgressBar = false;
                //手离开拖动进度条的时候更新进度
                //媒体流以毫秒为单位
                mControl.seekTo(mCurrentProgress * 1000,true);
                String currentPosition = mMinFormt.format(mCurrentProgress*1000);
                LogUtils.d("test","current = "+currentPosition);
                mCurrentPosition.setText(currentPosition);
                if (!mIsUserTouchProgressBar) {
                    mDurationBar.setProgress(mCurrentProgress);
                }
            }
        });

        mCommentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
                if (nowPlayingSongInfo != null) {
                    MoyuServiceWrap.Singletion.INSTANCE.getHolder().launchComment(nowPlayingSongInfo.getSongId());
                }
            }
        });
    }

    @Override
    protected void initView() {
        mLoadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {

            }
        });
        mLoadService.showCallback(LoadingCallback.class);
        mHeaderLayout = this.findViewById(R.id.home_hear_layout);
//        mLyricViewX = this.findViewById(R.id.lyricViewX);
//        mLyricViewX.setCurrentColor(  ContextCompat.getColor(
//                this,
//                R.color.colorWhite
//        ));
//        mLyricViewX.setLabel("暂无歌词");
//        mLyricViewX.setTimeTextColor(
//                ContextCompat.getColor(
//                        this,
//                        R.color.colorWhite
//                )
//        );
//        mLyricViewX.setTimelineColor(
//                ContextCompat.getColor(
//                        this,
//                        R.color.colorWhite
//                )
//        );
        mLrcView = this.findViewById(R.id.lyricView);
        mPlayOrPauseIv = this.findViewById(R.id.play_pause);
        mLastMusicIv = this.findViewById(R.id.last_music);
        mNextMusicIv = this.findViewById(R.id.next_music);
        mCommentIv = this.findViewById(R.id.comment_iv);
        mDurationBar = this.findViewById(R.id.track_seek_bar);
        mCurrentPosition = this.findViewById(R.id.current_position);
        mTotalDuration = this.findViewById(R.id.track_duration);
        mMusicNameTv = this.findViewById(R.id.music_name);
        mMusicianNameTv = this.findViewById(R.id.musician_name);
        this.findViewById(R.id.back).setOnClickListener(v -> finish());
        setStatusBar();
        mControl = StarrySky.with();
        ConstraintLayout layout = this.findViewById(R.id.layout);
        layout.setBackground(null);
        mBgIv = this.findViewById(R.id.bg_iv);
        mBgIv.setDrawingCacheEnabled(true);
        SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
        String songCover = nowPlayingSongInfo.getSongCover();

        mIvShowPic = this.findViewById(R.id.ivShowPic);
        initMusicInfo(nowPlayingSongInfo, songCover);
        if (mControl.isPlaying()) {
            mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
            mPlayOrPauseIv.setImageResource(R.mipmap.pause_white);
        }else {
            mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
            mPlayOrPauseIv.setImageResource(R.mipmap.play_white);
        }


        mJingyunLayout = this.findViewById(R.id.jingyu_layout);
        mJingyunView = this.findViewById(R.id.sv_bg);

//        getBitmap();
        PermissonUtil.checkPermission(this, new PermissionListener() {
            @Override
            public void havePermission() {
                initVisualizer();
            }
            @Override
            public void requestPermissionFail() {

            }
        }, permissons);

    }

    private void initMusicInfo(SongInfo nowPlayingSongInfo, String songCover) {
        if (songCover != null) {
            Glide.with(mBgIv.getContext())
                    .load(songCover)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(35,3)))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            setBackground(songCover);
                            return false;
                        }
                    })
                    .into(mBgIv);
        }else {
            Glide.with(mBgIv.getContext())
                    .load(R.mipmap.background)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            setBackground(null);
                            return false;
                        }
                    })
                    .into(mBgIv);
        }
        Glide.with(MusicPlayActivity.this).asBitmap().addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                mJingyunView.setmPaintColor(ImageUtil.getColor(resource, 3).getRgb());
                return false;
            }
        }).load(songCover).circleCrop().into(mIvShowPic);
        mIvShowPic.setClipToOutline(true);
        mIvShowPic.setOutlineProvider(ImageUtil.getOutline(true, 20, 1));
//
        objectAnimator = ObjectAnimator.ofFloat(mIvShowPic, "rotation", 0f, 360f);
        objectAnimator.setDuration(20 * 1000);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(-1);
        objectAnimator.start();
//        setBackground(songCover);
        if (nowPlayingSongInfo !=null){
            long duration = nowPlayingSongInfo.getDuration();
            mDurationBar.setMax(Integer.parseInt(duration+""));
            long min = duration/60;
            long s = duration%60;
            String total ="";
            if (1==(min+"").length()) {
                total = total+"0"+min+":";
            }else {
                total = total+min+":";
            }
            if (1==(s+"").length()) {
                total = total+"0"+s;
            }else {
                total+=s;
            }
            mTotalDuration.setText(total);
            mMusicNameTv.setText(nowPlayingSongInfo.getSongName());
            mMusicianNameTv.setText(nowPlayingSongInfo.getArtist());
        }
    }

    String[] permissons = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
//    @Override
//    protected void onResume() {
//        super.onResume();
////        getLyric();
//    }

    private void setStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mHeaderLayout);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.home_activity_music_play;
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
    public void setMusicInfo(MusicAndMusicianInfoBean data) {
        String lyric = data.getData().getMusicInfo().getLyric();
//        mLyricViewX.loadLyric(lyric,null);
        mLrcView.setVisibility(View.VISIBLE);
        mLrcView.loadLrc(lyric);
        mLoadService.showSuccess();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicPlayActivityPresenter.unregisterViewCallback(this);
        mControl.removePlayerEventListener(mPlayerEventTag);
    }

    private void setBackground(String url) {
        if (url==null) {
            Bitmap bitmap = BlurUtil.doBlur(BitmapFactory.decodeResource(getResources(), R.mipmap.background), 10, 30);
            mBgIv.setImageBitmap(bitmap);
        }

        mBgIv.setDrawingCacheEnabled(true);
        getBitmap();

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(0.7f, 0.7f, 0.7f, 1);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        mBgIv.setColorFilter(colorFilter);

    }

    int top = 0;
    int width = 0;
    int height = 0;
    public void getBitmap() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //启用DrawingCache并创建位图
//                iv_bg.buildDrawingCache();
//                while (iv_bg.getDrawingCache() == null) {
//                    iv_bg.buildDrawingCache();
//                    SystemClock.sleep(10);
//                }
                mBgIv.buildDrawingCache();
                while (mBgIv.getDrawingCache() == null) {
                    mBgIv.buildDrawingCache();
                    SystemClock.sleep(10);
                }
//                Bitmap bitmap2 = Bitmap.createBitmap(mBgIv.getDrawingCache());
//                Bitmap bitmap2 = Bitmap.createBitmap(iv_bg.getDrawingCache());
                Bitmap bitmap2 = Bitmap.createBitmap(mBgIv.getDrawingCache());
                top = top ==0 ?mJingyunView.getTop():top;
                width = width ==0 ?mJingyunView.getWidth():width;
                height = height ==0 ?mJingyunView.getHeight():height;
//                bitmap2 = Bitmap.createBitmap(bitmap2, 0, top, width, height);
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, top, width, height);
                mJingyunView.setBitmapBg(bitmap2);

//                iv_bg.setDrawingCacheEnabled(false);
                mBgIv.setDrawingCacheEnabled(false);
            }
        });
        thread.start();
        //暂时不显示
        mJingyunLayout.setVisibility(View.GONE);
        mLoadService.showSuccess();
    }
    AudioVisualConverter visualConverter=new AudioVisualConverter();

    private Visualizer.OnDataCaptureListener dataCaptureListener = new Visualizer.OnDataCaptureListener() {
        @Override
        public void onWaveFormDataCapture(Visualizer visualizer, final byte[] waveform, int samplingRate) {
            mJingyunView.setmBytes(visualConverter.converter(waveform));
        }

        @Override
        public void onFftDataCapture(Visualizer visualizer, final byte[] fft, int samplingRate) {

        }
    };
    private Visualizer visualizer;
    public void initVisualizer() {
        if(visualizer != null){
            visualizer = null;
        }
        visualizer = new Visualizer(0);
        //采样的最大值
        int captureSize = Visualizer.getCaptureSizeRange()[1];
        //采样的频率
        int captureRate = Visualizer.getMaxCaptureRate() * 2 / 3;
        visualizer.setEnabled(false);
        visualizer.setCaptureSize(captureSize);
        visualizer.setDataCaptureListener(dataCaptureListener, captureRate, true, true);
        visualizer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
        visualizer.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
        visualizer.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (objectAnimator.isRunning()) {
            objectAnimator.pause();
        }
    }
}