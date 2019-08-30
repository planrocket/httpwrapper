package com.xin.support.http.api.sender;

import com.xin.support.http.api.callback.Callback;

import java.util.Map;


public interface ISender {
    void init();

    /**
     *
     * @param url
     * @param params
     * @return
     */
//    Response2 post(String url, Map<String, String> params);

    /**
     * 异步
     *
     * @param url
     * @param params
     * @param callback
     */
    void post(String url, Map<String, String> params, Callback callback);

}
