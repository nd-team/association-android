package com.ike.sq.alliance;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.ike.sq.alliance.bean.UserBean;
import com.ike.sq.alliance.dao.base.BaseManager;
import com.ike.sq.alliance.receiver.NetChangeReceiver;
import com.ike.sq.alliance.utils.HttpLogger;
import com.ike.sq.alliance.utils.MyUtils;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by T-BayMax on 2017/8/24.
 */

public class App extends MultiDexApplication {
//    private NetChangeReceiver receiver;

    public static String token;
    public static UserBean userBean;
    public static OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        BaseManager.initOpenHelper(this);
        JUtils.initialize(this);
        JUtils.setDebug(true, "日志测试");

        EmojiManager.install(new IosEmojiProvider());
      //  org.xutils.x.Ext.init(this);
      //  org.xutils.x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        registerActivityLifecycleCallbacks(JActivityManager.getActivityLifecycleCallbacks());
        registerConnectivityNetworkMonitorForAPI21AndUp();
        NetChangeReceiver receiver = new NetChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyUtils.CONNECTIVITY_CHANGE);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
//声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
//设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(10000, TimeUnit.SECONDS)//设置连接超时时间

                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.detectFileUriExposure();
        }
    }

    private void registerConnectivityNetworkMonitorForAPI21AndUp() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {
                    /**
                     * @param network
                     */
                    @Override
                    public void onAvailable(Network network) {
                        //网络恢复
                        sendBroadcast(
                                getConnectivityIntent(false)
                        );

                    }

                    /**
                     * @param network
                     */
                    @Override
                    public void onLost(Network network) {
                        //网络关闭
                        sendBroadcast(
                                getConnectivityIntent(true)
                        );

                    }
                }

        );

    }

    /**
     * @param noConnection
     * @return
     */
    private Intent getConnectivityIntent(boolean noConnection) {
        Intent intent = new Intent();
        intent.setAction(MyUtils.CONNECTIVITY_CHANGE);
        intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection);
        return intent;
    }
}

