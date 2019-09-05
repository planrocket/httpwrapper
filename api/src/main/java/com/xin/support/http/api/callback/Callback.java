package com.xin.support.http.api.callback;


public abstract class Callback<T> {

    public abstract T parseResponse(String response) throws Exception;

    /**
     * UI Thread
     *
     * @param request
     */
    public void onStart() {
    }


    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total) {

    }


    public void onError(Exception e) {

    }

    public abstract void onResponse(T response);

    /**
     * UI Thread
     *
     * @param
     */
    public void onEnd() {
    }

}