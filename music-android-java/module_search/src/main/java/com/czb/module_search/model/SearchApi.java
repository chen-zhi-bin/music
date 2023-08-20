package com.czb.module_search.model;

import com.czb.module_search.model.bean.MusicInfoByLyricsBean;
import com.czb.module_search.model.bean.MusicInfoByNameBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApi {

    @POST("/music/list/name/{page}/30")
    Observable<MusicInfoByNameBean> doSearchByName(@Path("page")int page, @Query("name")String name);

    @POST("/music/list/lyric/{page}/30")
    Observable<MusicInfoByLyricsBean> doSearchByLyric(@Path("page")int page,@Query("lyric")String lyric);
}
