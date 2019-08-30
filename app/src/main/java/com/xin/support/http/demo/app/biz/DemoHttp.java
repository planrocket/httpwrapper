package com.xin.support.http.demo.app.biz;

import android.util.ArrayMap;

import com.xin.support.http.api.HttpSenderFade;
import com.xin.support.http.api.callback.Callback;


public class DemoHttp {
    public static void main(String[] args) {


        HttpSenderFade
                .getInstance()
                .obtain(HttpSenderFade.ROOT_CLIENT)
                .post("http://www.baidu.com", new ArrayMap<String, String>(-1), new Callback<String>() {


                    @Override
                    public String parseResponse(Object response) throws Exception {
                        return null;
                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });


    }
}
