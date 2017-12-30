package com.ike.sq.alliance.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ike.sq.alliance.App;
import com.ike.sq.alliance.bean.Msg;
import com.ike.sq.alliance.chat.activity.BaseChatActivity;
import com.ike.sq.alliance.chat.adapter.ChatRecyclerAdapter;
import com.ike.sq.alliance.dao.ChatDbManager;
import com.ike.sq.alliance.listener.WebSocketConnectionListener;
import com.ike.sq.alliance.network.NetworkUrl;
import com.ike.sq.alliance.utils.MyUtils;
import com.ike.sq.alliance.utils.NotificationUtils;
import com.jude.utils.JUtils;

/**
 *
 * Created by T-BayMax on 2017/8/28.
 */

public class ConnectService extends Service {
    private String TAG = "ConnectService";
    private boolean isExit;//true 退出操作
    private MyBroadcastReceiver receiver;
    private WebSocketConnectionListener socketConnectionListener;

    public ChatDbManager mChatDbManager;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "绑定服务...");
        return new MessageBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mChatDbManager = new ChatDbManager();
        WebSocketConnectionListener.setChatWebSocket(new WebSocketConnectionListener.ChatDataObserver() {
            @Override
            public void OnMessage(Msg msg) {
                msg.setFriendId(msg.getReceiver());
                NotificationUtils notification = new NotificationUtils(ConnectService.this);
                notification.showNotification(msg);
             /*   Intent intent = new Intent(MyUtils.UpdateMessage);
                intent.putExtra("msg", msg);
                sendBroadcast(intent);*/
                msg.setType(ChatRecyclerAdapter.FROM_USER_MSG);
                msg.setTime(BaseChatActivity.returnTime());
                mChatDbManager.insert(msg);
            }

            @Override
            public void onNetClose() {
                setmConnect();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connect();
        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyUtils.CONNECTIVITY_CHANGE);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    private void connect() {
        if (null == App.userBean)
            return;
        String url = NetworkUrl.Companion.getBASE_WS() + App.userBean.getId();
        socketConnectionListener = WebSocketConnectionListener.getChartWebSocket(url);

    }

    public class MessageBinder extends Binder {
        public void sendTextMessage(String text) {
            if (App.client.followRedirects()) {
                if (!isExit) {
                    socketConnectionListener.sendMessage(text);
                }
            }
        }

        public void closeConnect() {
            disConnect();
        }
    }

    /**
     * 从新连接
     */
    public void setmConnect() {

        isExit = false;
        connect();

    }

    public void disConnect() {

        isExit = true;
        socketConnectionListener.closeWebSocket();
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
                disConnect();
                JUtils.Toast("没有网络了");
            } else {
                setmConnect();
                JUtils.Toast("网络恢复了");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disConnect();
        unregisterReceiver(receiver);
    }
}
