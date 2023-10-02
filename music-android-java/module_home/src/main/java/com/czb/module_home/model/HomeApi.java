package com.czb.module_home.model;

import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.CollectedMusicBean;
import com.czb.module_home.model.bean.MusicAndMusicianInfoBean;
import com.czb.module_home.model.bean.MusicianMusicBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.MusicianBean;
import com.czb.module_home.model.bean.TopMusicBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeApi {

    @POST("/looper/list")
    Observable<BannerBean> getBanner();

    @GET("/music/list/top/{page}/10")
    Observable<TopMusicBean> getTop(@Path("page")int size);

    @GET("/music/list/top/{page}/30")
    Observable<TopMusicBean> getTopList(@Path("page")int page);

    @GET("/music/list/recommend/{page}/30")
    Observable<RecommendMusicBean> getRecommend(@Path("page")int page);

    @GET("/singer/list/1/10")
    Observable<MusicianBean> getSingerRecommend();

    @GET("/music/list/{musicianId}/{page}/30")
    Observable<MusicianMusicBean> getMusicByMusicianId(@Path("musicianId")String musicianId,@Path("page")int page);

    @GET("/music/info/{musicId}")
    Observable<MusicAndMusicianInfoBean> getMusicInfo(@Path("musicId")String musicId);

    @POST("/collection/{musicId}")
    Observable<CollectedMusicBean> postCollectMusicById(@Header("cookie_token")String token, @Path("musicId")String musicId);
}
