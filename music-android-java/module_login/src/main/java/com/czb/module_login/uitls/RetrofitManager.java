package com.czb.module_login.uitls;


import com.czb.module_base.common.Constants;
import com.czb.module_login.model.LoginApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private LoginApi mApi;
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

    public LoginApi getApi() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = retrofit.create(LoginApi.class);
        }
        return mApi;
    }
}
