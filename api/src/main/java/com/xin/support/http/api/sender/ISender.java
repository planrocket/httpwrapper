package com.xin.support.http.api.sender;


/**
 * 定义Sender的部分行为
 * init、destory
 */
public interface ISender extends IHttpAction {
    void init();

    void destroy();
//    void reset();



}
