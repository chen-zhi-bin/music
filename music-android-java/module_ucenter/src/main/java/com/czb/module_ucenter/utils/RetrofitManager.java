package com.czb.module_ucenter.utils;

import com.czb.module_base.common.Constants;
import com.czb.module_ucenter.model.UserApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {
    private UserApi mApi;
    private static RetrofitManager sApiManager;

    public static RetrofitManager getInstence() {
        if (sApiManager == null) {
            synchronized (RetrofitManager.class) {
                if (sApiManager == null) {
                    sApiManager = new RetrofitManager();
                }
            }
        }
        return sApiManager;
    }

    public UserApi getApi() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            mApi =  retrofit.create(UserApi.class);
        }
        return mApi;
    }
}
