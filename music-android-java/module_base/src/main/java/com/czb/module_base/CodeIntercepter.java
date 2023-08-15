package com.czb.module_base;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CodeIntercepter implements Interceptor {
    private String mUrl;

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder().url(mUrl).build();
        Response response = chain.proceed(build);
        return response;
    }
}
