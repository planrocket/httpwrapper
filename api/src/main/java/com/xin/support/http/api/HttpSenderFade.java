package com.xin.support.http.api;


import android.support.v4.util.ArrayMap;

import com.xin.support.http.api.sender.ISender;

public class HttpSenderFade {
    public static final String ROOT_CLIENT = "ROOT";

    private ArrayMap<String, Class<? extends ISender>> senderClasses = new ArrayMap();
    private ArrayMap<String, ISender> senderInstances = new ArrayMap();

    private HttpSenderFade() {

    }

    public static HttpSenderFade getInstance() {
        return Singleton.INSTANCE;
    }

    public ISender obtain() {
        return obtain(ROOT_CLIENT);
    }


    /**
     * 注册一个ISender实现类
     *
     * @param clientName
     * @param claszz
     */
    public void register(String clientName, Class<? extends ISender> claszz) {
        senderClasses.put(clientName, claszz);
    }


    /**
     * 获取对应的Sender实例
     *
     * @param clientName
     * @return
     */
    public ISender obtain(String clientName) {
        ISender instance = senderInstances.get(clientName);
        if (instance != null) return instance;

        instance = makeClient(clientName);

        instance.init();

        return instance;
    }

    private ISender makeClient(String clientName) {
        Class<? extends ISender> targetClass = senderClasses.get(clientName);
        if (targetClass == null) {
            throw new RuntimeException("" + clientName + "may not register in HttpSenderFade");
        }


        ISender instance = null;

        try {
            instance = targetClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (instance == null) {
            throw new RuntimeException("" + instance + "must have public Construtor");
        }
        return instance;
    }


    private static class Singleton {
        private static final HttpSenderFade INSTANCE = new HttpSenderFade();
    }
}
