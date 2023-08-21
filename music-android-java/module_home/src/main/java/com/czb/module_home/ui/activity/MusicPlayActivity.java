package com.czb.module_home.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_home.R;
import com.czb.module_home.callback.IMusicPlayActivityCallback;
import com.czb.module_home.model.bean.MusicAndMusicianInfoBean;
import com.czb.module_home.presenter.IMusicPlayActivityPresenter;
import com.czb.module_home.utils.PresenterManager;
import com.dirror.lyricviewx.LyricEntry;
import com.dirror.lyricviewx.LyricViewX;
import com.dirror.lyricviewx.OnPlayClickListener;
import com.lzx.starrysky.OnPlayProgressListener;
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
    private LyricViewX mLyricViewX;
    private IMusicPlayActivityPresenter mMusicPlayActivityPresenter;

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
                        getLyric();
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
                }else if (mControl.isPaused()){
                    mControl.restoreMusic();
                }
            }
        });
        mLyricViewX.setDraggable(true, new OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long l) {
               //点击左边箭头时音乐跳转
                long s = l / 1000;
                mCurrentPosition.setText(mMinFormt.format(s));
                mLyricViewX.updateTime(l,true);
                mDurationBar.setProgress(Integer.parseInt(s+""));
                mControl.seekTo(l,true);
                LyricEntry currentLineLyricEntry = mLyricViewX.getCurrentLineLyricEntry();
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
                mLyricViewX.updateTime(currPos,true);
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
    }

    @Override
    protected void initView() {
        mHeaderLayout = this.findViewById(R.id.home_hear_layout);
        mLyricViewX = this.findViewById(R.id.lyricViewX);
        mLyricViewX.setCurrentColor(  ContextCompat.getColor(
                this,
                R.color.colorWhite
        ));
        mLyricViewX.setLabel("暂无歌词");
        mLyricViewX.setTimeTextColor(
                ContextCompat.getColor(
                        this,
                        R.color.colorWhite
                )
        );
        mLyricViewX.setTimelineColor(
                ContextCompat.getColor(
                        this,
                        R.color.colorWhite
                )
        );
        mPlayOrPauseIv = this.findViewById(R.id.play_pause);
        mLastMusicIv = this.findViewById(R.id.last_music);
        mNextMusicIv = this.findViewById(R.id.next_music);
        mDurationBar = this.findViewById(R.id.track_seek_bar);
        mCurrentPosition = this.findViewById(R.id.current_position);
        mTotalDuration = this.findViewById(R.id.track_duration);
        mMusicNameTv = this.findViewById(R.id.music_name);
        mMusicianNameTv = this.findViewById(R.id.musician_name);
        this.findViewById(R.id.back).setOnClickListener(v -> finish());
        setStatusBar();
        mControl = StarrySky.with();

        SongInfo nowPlayingSongInfo = mControl.getNowPlayingSongInfo();
        if (nowPlayingSongInfo!=null){
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
        if (mControl.isPlaying()) {
            mPlayOrPauseIv.setTag(Constants.MusicState.PLAY);
            mPlayOrPauseIv.setImageResource(R.mipmap.pause_white);
        }else {
            mPlayOrPauseIv.setTag(Constants.MusicState.PAUSE);
            mPlayOrPauseIv.setImageResource(R.mipmap.play_white);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLyric();
    }

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
        mLyricViewX.loadLyric(lyric,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}