package com.xin.support.http.api;


import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.xin.support.http.api.sender.IHttpAction;
import com.xin.support.http.api.sender.ISender;
import com.xin.support.http.api.sender.NonImplSender;

import java.lang.reflect.InvocationTargetException;


public class HttpSenderManager {
    private static final String TAG = HttpSenderManager.class.getSimpleName();

    private final ArrayMap<String, Class<? extends ISender>> clazzes;
    private final ArrayMap<String, ISender> instances;

    private HttpSenderManager() {
        clazzes = new ArrayMap();
        instances = new ArrayMap();
    }

    public static HttpSenderManager getInstance() {
        return Singleton.INSTANCE;
    }


    /**
     * 注册一个ISender实现类
     *
     * @param clientName
     * @param claszz
     */
    public void register(String clientName, Class<? extends ISender> claszz) {
        clazzes.put(clientName, claszz);
    }


    /**
     * 获取对应的Sender实例
     *
     * @param clientName
     * @return
     */
    public IHttpAction obtain(String clientName) {
        //先从实例Map获取
        ISender instance = instances.get(clientName);
        if (instance != null) return instance;

        instance = makeClient(clientName);

        instance.init();

        return instance;
    }

    /**
     * 实例化client
     *
     * @param clientName
     * @return
     */
    private ISender makeClient(String clientName) {
        Class<? extends ISender> targetClass = clazzes.get(clientName);
        if (targetClass == null) {
            Log.e("HttpSenderManager", clientName + " Not Found，Using Default Client-NonImplSender，Please Check you code");
            targetClass = NonImplSender.class;
        }


        ISender instance = null;

        try {
            instance = targetClass.getConstructor().newInstance();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "HttpSenderManager -> obtain -> makeClient_IllegalAccessException:" + e.getMessage());
        } catch (InstantiationException e) {
            Log.e(TAG, "HttpSenderManager -> obtain -> makeClient_InstantiationException:" + e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "HttpSenderManager -> obtain -> makeClient_NoSuchMethodException:" + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "HttpSenderManager -> obtain -> makeClient_InvocationTargetException:" + e.getMessage());
        }

        if (instance == null) {
            throw new RuntimeException("" + instance + "must have public default Construtor");
        }
        instances.put(clientName, instance);
        return instance;
    }


    private static class Singleton {
        private static final HttpSenderManager INSTANCE = new HttpSenderManager();
    }
}
