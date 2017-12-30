/*
 *
 *  ,--^----------,--------,-----,-------^--,
 *   | |||||||||   `--------'     |          O
 *   `+---------------------------^----------|
 *     `\_,-------, _________________________|
 *       / XXXXXX /`|     /
 *      / XXXXXX /  `\   /
 *     / XXXXXX /\______(
 *    / XXXXXX /
 *   / XXXXXX /
 *  (________(
 *   `------'
 *
 * Created by T-BayMax  17-9-29 下午8:44
 *
 */

package com.ike.sq.alliance.websocket;

import java.security.SecureRandom;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by T-BayMax on 2017/9/29.
 */

public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {


    public OkHttpClient newBuilder(){
        return this;
    }

    @Override
    public Call newCall(Request request) {
        return null;
    }

    @Override
    public WebSocket newWebSocket(Request request, WebSocketListener listener) {
        /*RealWebSocket webSocket = new RealWebSocket(request, listener, new SecureRandom());
        webSocket.connect(this);*/
        return null;
    }
}
