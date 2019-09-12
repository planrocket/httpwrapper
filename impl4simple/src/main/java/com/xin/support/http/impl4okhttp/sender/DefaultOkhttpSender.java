package com.xin.support.http.impl4okhttp.sender;


import android.util.Log;

import com.xin.support.http.api.callback.Callback;
import com.xin.support.http.api.response.SimpleResponse;
import com.xin.support.http.api.sender.ISender;
import com.xin.support.http.impl4okhttp.request.CommonRequest;
import com.xin.support.http.impl4okhttp.request.InternalUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class DefaultOkhttpSender implements ISender {
    private static final int TIME_OUT = 8;
    private OkHttpClient okHttpClient;


    @Override
    public void init() {
        okHttpClient =
                new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .followRedirects(true) //设置重定向 其实默认也是true
                        .build();
    }

    @Override
    public void destroy() {
        okHttpClient = null;
    }

//    @Override
//    public SimpleResponse post(final String url, final Map<String, String> params) {
//        return null;
//    }

    /**
     * todo: 支持header
     *
     * @param url
     * @param params
     * @param callback
     */
    @Override
    public void post(final String url, final Map<String, String> params, final Callback callback) {
        //包装必要参数
        Call call = okHttpClient.newCall(CommonRequest.createPostRequest(url, params));
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("DemoHttp", "onFailure call:" + call);

                dispatchResultFail(e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("DemoHttp", "onResponse call:" + call);

                try {
                    if (call.isCanceled()) {
                        dispatchResultFail(new IOException("Canceled!"), callback);
                        return;
                    }

                    if (!response.isSuccessful()) {
                        dispatchResultFail(new IOException("request failed , reponse's code is : " + response.code()), callback);
                        return;
                    }
                    SimpleResponse simpleResponse = InternalUtils.convert(call, response);
                    Object o = callback.parseResponse(simpleResponse);
                    dispatchResultSuccess(o, callback);
                } catch (Exception e) {
                    dispatchResultFail(e, callback);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    private void dispatchResultFail(final Exception e, final Callback callback) {
        if (callback == null) return;
        callback.onError(e);
        callback.onEnd();
    }

    private void dispatchResultSuccess(final Object object, final Callback callback) {
        if (callback == null) return;
        callback.onResponse(object);
        callback.onEnd();
    }

}
