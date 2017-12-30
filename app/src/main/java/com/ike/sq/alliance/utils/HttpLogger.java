package com.ike.sq.alliance.utils;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by T-BayMax on 2017/9/15.
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.e("HttpLogInfo", message);
    }
}
