package com.xin.support.http.api.response;

import android.support.v4.util.ArrayMap;

/**
 * 同步时返回的数据
 */
public class SimpleResponse {
    SimpleRequest request;

    //http状态码
    int code;
    String message;

    ArrayMap headers;

}
