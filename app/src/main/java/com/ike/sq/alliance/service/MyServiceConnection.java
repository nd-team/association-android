package com.ike.sq.alliance.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public class MyServiceConnection implements ServiceConnection {

    private ConnectService.MessageBinder binder;

    public ConnectService.MessageBinder getBinder() {
        return binder;
    }

    public void setBinder(ConnectService.MessageBinder binder) {
        this.binder = binder;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (ConnectService.MessageBinder) service;   //该binder,需要在activity中声明。
        Log.d("learnservice", "绑定服务conn...");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("learnservice", "解除绑定服务conn...");
    }
}
