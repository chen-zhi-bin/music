package com.czb.module_ucenter.model;

import com.czb.module_ucenter.model.bean.CollectionListBean;
import com.czb.module_ucenter.model.bean.LogoutBean;
import com.czb.module_ucenter.model.bean.UserInfoBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/user/user_info/{userId}")
    Observable<UserInfoBean> getUserInfo(@Path("userId")String userId);

    @GET("/user/logout")
    Observable<LogoutBean> doLogout(@Header("cookie_token")String token);

    @GET("/collection/list/{page}")
    Observable<CollectionListBean> getCollectionListByPage(@Header("cookie_token")String token,@Path("page")int page);
}
