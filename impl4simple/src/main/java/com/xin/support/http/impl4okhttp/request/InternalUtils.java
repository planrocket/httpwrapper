package com.xin.support.http.impl4okhttp.request;

import com.xin.support.http.api.response.SimpleResponse;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InternalUtils {

    public static SimpleResponse generateSimpleResponse(final Call call, final Response response) {
        URL url = call.request().url().url();

        int code = response.code();
        Map<String, List<String>> headers = response.headers().toMultimap();
        String message = response.message();

        String bodyString = null;
        ResponseBody body = response.body();
        try {
            bodyString = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }


        SimpleResponse simpleResponse = new SimpleResponse(code, headers, message, bodyString);

        return simpleResponse;
    }


}
