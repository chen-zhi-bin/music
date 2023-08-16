package com.czb.module_home.model;

import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeApi {

    @POST("/looper/list")
    Observable<BannerBean> getBanner();

    @GET("/music/list/top/{page}/10")
    Observable<TopMusicBean> getTop(@Path("page")int size);

    @GET("/music/list/recommend/{page}/30")
    Observable<RecommendMusicBean> getRecommend(@Path("page")int page);
}