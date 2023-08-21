package com.czb.module_community.model;

import com.czb.module_community.model.bean.CommentAndMusicBean;
import com.czb.module_community.model.bean.CommentBean;
import com.czb.module_community.model.bean.CommentReturnBean;
import com.czb.module_community.model.bean.MomentComment;
import com.czb.module_community.model.bean.MomentSubComment;
import com.czb.module_community.model.bean.SubCommentBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommunityApi {

    @GET("/comment/music/{musicId}/{page}/30")
    Observable<CommentBean> getComment(@Path("musicId")String musicId,@Path("page")int page);

    @POST("/comment")
    Observable<CommentReturnBean> postComment(@Header("cookie_token")String token, @Body MomentComment commentBean);

    @POST("/comment/sub_comment")
    Observable<CommentReturnBean> postSubComment(@Header("cookie_token")String token, @Body MomentSubComment subComment);

    @GET("/comment/comment/{commentId}/{page}/30")
    Observable<SubCommentBean> getCommentByCommentId(@Path("commentId")String musicId, @Path("page")int page);

    @GET("/comment/list/{page}/30")
    Observable<CommentAndMusicBean> getCommentList(@Path("page")int page);
}
