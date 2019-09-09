package com.xin.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.xin.support.http.api.HttpSenderFade;
import com.xin.support.http.api.callback.Callback;
import com.xin.support.http.api.response.SimpleResponse;
import com.xin.support.http.impl4okhttp.sender.DefaultOkhttpSender;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public static final String HTTP_C2B = "Simple";
    String url = "http://rap2api.taobao.org/app/mock/224173/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configHttp();
    }

    public void configHttp() {
        HttpSenderFade.getInstance().register(HTTP_C2B, DefaultOkhttpSender.class);
    }

    public void sendPostByUtils(View view) {
        Log.e("DemoHttp", "OkHttpUtils start ");

        postCallByOkhttpUtils();
    }

    public void sendPost(View view) {
        Log.e("DemoHttp", "my start ");

        postCall();
    }

    private void postCallByOkhttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        OkHttpUtils
                .post()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("DemoHttp", "OkHttpUtils parseResponse onError:");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("DemoHttp", "OkHttpUtils onResponse:" + response);

                    }
                });
    }

    private void postCall() {
        ArrayMap<String, String> params = new ArrayMap(2);
        params.put("username", "hyman");
        params.put("password", "123");

        HttpSenderFade
                .getInstance()
                .obtain(HTTP_C2B)
                .post(url, params, new Callback<String>() {


                    @Override
                    public String parseResponse(SimpleResponse response) throws Exception {
                        Log.e("DemoHttp", "parseResponse response:" + response);
                        return response.bodyString;
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("DemoHttp", "onResponse response:" + response);
                    }
                });
    }


}
