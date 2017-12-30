package com.ike.sq.alliance.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ike.sq.alliance.App;
import com.ike.sq.alliance.MainActivity;
import com.ike.sq.alliance.bean.Msg;
import com.ike.sq.alliance.utils.GsonUtils;
import com.ike.sq.alliance.utils.NotificationUtils;
import com.jude.utils.JUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by T-BayMax on 2017/8/26.
 */

public class WebSocketConnectionListener extends WebSocketListener {
    private WebSocket _WebSocket = null;
    private Msg msg;

    private static WebSocketConnectionListener mChatWebSocket = null;


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        _WebSocket = webSocket;
        Log.e("onOpen>>>>>>>", "open");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.e("MESSAGE>>>>>>>", text);

        Gson gson = new Gson();
        Msg msg = gson.fromJson(text, Msg.class);
        if (null != observer)
            observer.OnMessage(msg);

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
      /*  if (code==1000) {
            closeWebSocket();
        }else {*/
            observer.onNetClose();
//        }
        Log.e("Close:", code + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        App.client.dispatcher().cancelAll();
        Log.e("onFailure>>>>>>","onFailure");
        synchronized (this) {
            _WebSocket = null;
        }
        t.printStackTrace();
    }

    /**
     * 初始化WebSocket服务器
     */
    private void run(String url) {
        Request request = new Request.Builder().url(url).build();
        App.client.newWebSocket(request, this);
      //  App.client.dispatcher().executorService().shutdown();
    }

    /**
     * @param s
     * @return
     */
    public boolean sendMessage(String s) {
        return _WebSocket.send(s);
    }

    /**
     * @param s
     * @return
     */
    public boolean sendMessage(ByteString s) {
        return _WebSocket.send(s);
    }

    public void closeWebSocket() {
        mChatWebSocket = null;
       if (_WebSocket.close(1000, "主动关闭"))
        Log.e("close", "关闭成功");
    }

    /**
     * 获取全局的ChatWebSocket类
     *
     * @return ChatWebSocket
     */
    public static WebSocketConnectionListener getChartWebSocket(String url) {
        if (mChatWebSocket == null) {
            mChatWebSocket = new WebSocketConnectionListener();
            mChatWebSocket.run(url);
        }
        return mChatWebSocket;
    }

    static ChatDataObserver observer;

    public static void setChatWebSocket(ChatDataObserver dataObserver) {
        observer = dataObserver;
    }

    public interface ChatDataObserver {
        public void OnMessage(Msg jsonObject);
        public void onNetClose();
    }
}


