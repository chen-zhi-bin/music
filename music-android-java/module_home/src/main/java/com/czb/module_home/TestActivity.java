package com.czb.module_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.LogUtils;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.lzx.starrysky.manager.PlaybackStage;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PlayerControl control = StarrySky.with();
        MutableLiveData<PlaybackStage> playbackStageMutableLiveData = control.playbackState();
        playbackStageMutableLiveData.observe(this, new Observer<PlaybackStage>() {
            @Override
            public void onChanged(PlaybackStage playbackStage) {
                switch (playbackStage.getStage()){
                    case PlaybackStage.PLAYING:
                        LogUtils.d("test","正在播放");
                        break;
                    case PlaybackStage.PAUSE:
                        LogUtils.d("test","暂停播放");
                        break;

                    case PlaybackStage.ERROR:

                        break;

                }
            }
        });
        control.prepare();
        if (control != null) {
            LogUtils.d("test","init test succ");
        }

//        control.playMusicByUrl("http://music.163.com/song/media/outer/url?id=317151.mp3");
        SongInfo songInfo1 = new SongInfo();
        songInfo1.setSongId("1254156236");
        songInfo1.setSongName("纸船");
        songInfo1.setSongUrl("http://10.0.2.2:8090"+"/music/1691750512218_1139629777351606272.mp3");
        control.addSongInfo(songInfo1);
//        control.playMusicByUrl("http://10.0.2.2:8090"+"/music/1691750512218_1139629777351606272.mp3");
//        control.playMusicByUrl(Constants.BASE_URL+"/music/1691750576251_1139630045925474304.flac");
        System.out.println(control.getNowPlayingSongUrl());
        List<SongInfo> playList = control.getPlayList();
        SongInfo songInfo = new SongInfo();
        songInfo.setSongId("1254180257");
        songInfo.setSongUrl("http://music.163.com/song/media/outer/url?id=317151.mp3");
//        playList.add(songInfo);
        control.addSongInfo(songInfo);
        LogUtils.d("test","play ="+control.isPlaying()+"");
        LogUtils.d("test","play ="+playList.get(0).getSongUrl()+"");
        this.findViewById(R.id.home_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("test","play ="+control.isPlaying()+"");
                LogUtils.d("test","play ="+control.getDuration()+"");
                if (control.isPlaying()) {
                    control.pauseMusic();
                }else {
                    control.restoreMusic();
                }
            }
        });
        this.findViewById(R.id.home_test_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.skipToNext();
            }
        });
    }
}