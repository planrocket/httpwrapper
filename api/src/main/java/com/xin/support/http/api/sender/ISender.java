package com.xin.support.http.api.sender;

import com.xin.support.http.api.callback.Callback;

import java.util.Map;


public interface ISender {
    void init();

    void destroy();
//    void reset();

    /**
     *
     * @param url
     * @param params
     * @return
     */
//    SimpleResponse post(String url, Map<String, String> params);

    /**
     * 异步
     * todo：是否需要将 CallBack 替换为 livedata
     * @param url
     * @param params
     * @param callback
     */
    void post(String url, Map<String, String> params, Callback callback);

}
