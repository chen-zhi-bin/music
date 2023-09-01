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

//import me.wcy.lrcview.LrcView;

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

//        LrcView lrcView = this.findViewById(R.id.lrc_view);
//        lrcView.loadLrc("[00:00.000] 作词 : 薛之谦\n" +
//                "[00:01.000] 作曲 : 韩星洲\n" +
//                "[00:12.210]我在听新闻里面说 他们曾来过\n" +
//                "[00:19.020]火星人的心脏靠左\n" +
//                "[00:23.170]我们也曾听大人说 他没有管我\n" +
//                "[00:30.270]他们一定看见什么过\n" +
//                "[00:33.820]森林不在 动物减半\n" +
//                "[00:36.510]人们拍照 留念那些 飞机残骸\n" +
//                "[00:39.710]妻离子散 空袭灾难\n" +
//                "[00:42.100]那些不敢 谈政治的 都去避难\n" +
//                "[00:45.090]被遗弃的小孩 搀扶那颗炸弹\n" +
//                "[00:47.560]他父母被人用枪指着头要答案\n" +
//                "[00:50.380]若要停战 先要谈判\n" +
//                "[00:52.710]这一片片 荒凉土地幕后到底谁管\n" +
//                "[00:55.460]如果钢铁都燃起火\n" +
//                "[00:59.750]看城市多折磨\n" +
//                "[01:02.070]请你配合我 一起难过\n" +
//                "[01:06.510]假如猿人没点起火\n" +
//                "[01:10.590]我们回到那生活\n" +
//                "[01:13.390]你是否救得回刚离群的我\n" +
//                "[01:18.070]火星人来过\n" +
//                "[01:20.820]火星人来过\n" +
//                "[01:23.620]火星人救我\n" +
//                "[01:29.230]火星人救我\n" +
//                "[01:31.980]火星人救我\n" +
//                "[01:34.740]火星人爱我\n" +
//                "[01:41.600]其实我们也忏悔过借口都好说\n" +
//                "[01:48.420]可以怪我心脏偏左\n" +
//                "[01:52.500]有些领袖话音刚落也会很难过\n" +
//                "[01:59.580]因为会议迟迟通不过\n" +
//                "[02:03.360]舍利不在象牙贩卖\n" +
//                "[02:05.720]人们认为贫穷可以卖掉小孩\n" +
//                "[02:08.550]钻石太窄富人不爱\n" +
//                "[02:11.380]还在楼顶挥霍那些仿真钱财\n" +
//                "[02:14.220]反正这没战乱也没有什么天灾\n" +
//                "[02:16.780]我管他谁让瘟疫艾滋继续泛滥\n" +
//                "[02:19.800]这种心态还能表态\n" +
//                "[02:22.100]毕竟这是个打字不用负责任的年代\n" +
//                "[02:25.080]如果欲望都燃起火\n" +
//                "[02:28.820]怎么自私怎么活\n" +
//                "[02:31.230]请你举起手假装难过\n" +
//                "[02:35.840]假如猿人没点起火\n" +
//                "[02:39.790]我们回到那生活\n" +
//                "[02:42.860]你是否劝得住开第一枪的我\n" +
//                "[02:48.600]假如有第三次战火\n" +
//                "[02:52.530]让地核接近我\n" +
//                "[02:55.440]你别难过请抱紧我\n" +
//                "[02:59.460]如果你不能说服我\n" +
//                "[03:03.630]就请你瞄准我\n" +
//                "[03:06.520]你听地球刚哭过\n" +
//                "[03:11.150]地球刚哭过\n" +
//                "[03:13.840]地球有话说\n" +
//                "[03:16.620]地球好脆弱\n" +
//                "[03:22.260]火星人救我\n" +
//                "[03:25.020]火星人救我\n" +
//                "[03:27.810]火星人来过...\n" +
//                "[03:30.855] 编曲 : 韩星洲\n" +
//                "[03:33.900] 制作人 : 韩星洲\n" +
//                "[03:36.945] 混音 : 赵靖\n");
//
    }
}