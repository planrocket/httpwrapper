package com.xin.support.http.api.sender;

import com.xin.support.http.api.callback.Callback;

import java.util.Map;

/**
 * 定义Http的部分行为
 */
public interface IHttpAction {
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
     * todo：是否需要将 post 设置为 泛型方法
     *
     * @param url
     * @param params
     * @param callback
     */
    void post(String url, Map<String, String> params, Callback callback);
}
