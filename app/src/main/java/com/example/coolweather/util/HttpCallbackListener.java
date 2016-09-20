package com.example.coolweather.util;

/**
 * Created by xxz on 2016/9/19.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
