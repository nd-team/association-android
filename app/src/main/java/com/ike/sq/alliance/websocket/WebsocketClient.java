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
 * Created by T-BayMax  17-9-29 下午8:29
 *
 */

package com.ike.sq.alliance.websocket;

import android.util.Log;

import com.ike.sq.alliance.network.HttpsUtils;
import com.ike.sq.alliance.network.NetworkUrl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by T-BayMax on 2017/9/29.
 */

public class WebsocketClient {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private static OkHttpClient sClient;
    private static WebSocket sWebSocket;
    public static synchronized void startRequest() {
        if (sClient == null) {
            sClient = new OkHttpClient();
        }
        if (sWebSocket == null) {
            Request request = new Request.Builder().url(NetworkUrl.Companion.getBASE_WS()).build();

            EchoWebSocketListener listener = new EchoWebSocketListener();
            sWebSocket = sClient.newWebSocket(request, listener);
        }
    }

    private static void sendMessage(WebSocket webSocket) {
        webSocket.send("Knock, knock!");
        webSocket.send("Hello!");
        webSocket.send(ByteString.decodeHex("deadbeef"));
    }

    public static void sendMessage() {
        WebSocket webSocket;
        synchronized (WebsocketClient.class) {
            webSocket = sWebSocket;
        }
        if (webSocket != null) {
            sendMessage(webSocket);
        }
    }

    public static synchronized void closeWebSocket() {
        if (sWebSocket != null) {
            sWebSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
            sWebSocket = null;
        }
    }

    public static synchronized void destroy() {
        if (sClient != null) {
            sClient.dispatcher().executorService().shutdown();
            sClient = null;
        }
    }

    private static void resetWebSocket() {
        synchronized (WebsocketClient.class) {
            sWebSocket = null;
        }
    }

    public static class EchoWebSocketListener extends WebSocketListener {
        private static final String TAG = "EchoWebSocketListener";

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            sendMessage(webSocket);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i(TAG, "Receiving: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.i(TAG, "Receiving: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.i(TAG, "Closing: " + code + " " + reason);
            resetWebSocket();
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Log.i(TAG, "Closed: " + code + " " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
            resetWebSocket();
        }
    }
}
