package com.xin.support.http.demo.base.config;

import com.xin.support.http.api.HttpSenderFade;
import com.xin.support.http.impl4okhttp.sender.DefaultOkhttpSender;

public class Config {
    public static final String HTTP_C2B = "Simple";

    public static void main(String[] args) {
        HttpSenderFade.getInstance().register(HTTP_C2B, DefaultOkhttpSender.class);
    }
}
