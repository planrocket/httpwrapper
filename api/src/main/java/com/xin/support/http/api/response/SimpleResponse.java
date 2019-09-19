package com.xin.support.http.api.response;

import java.util.List;
import java.util.Map;

/**
 * 同步时返回的数据
 */
public class SimpleResponse {


    //http状态码
    public final int code;
    public final String message;
    public final String bodyStr;

    public final Map<String, List<String>> headers;

    public SimpleResponse(int code, Map<String, List<String>> headers, String message, String bodyStr) {
        this.code = code;
        this.headers = headers;
        this.message = message;
        this.bodyStr = bodyStr;
    }

}


