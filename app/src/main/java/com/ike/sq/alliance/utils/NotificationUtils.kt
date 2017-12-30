package com.ike.sq.alliance.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.NotificationCompat
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.chat.activity.RecyclerViewChatActivity

/**
 * Created by T-BayMax on 2017/9/5.
 */
class NotificationUtils(private val context: Context) {

    private var mBuilder: NotificationCompat.Builder? = null
    private var mNotificationManager: NotificationManager? = null
      final val NOTIFICATION_INT: Int = 1000

    open fun showNotification(msg: Msg) {
        if (null == mNotificationManager) {
            mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        mBuilder = NotificationCompat.Builder(context)//Notification 的兼容类
        mBuilder?.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder?.setSmallIcon(R.mipmap.ic_launcher)   //若没有设置largeicon，此为左边的大icon，设置了largeicon，则为右下角的小icon，无论怎样，都影响Notifications area显示的图标
        mBuilder?.setContentTitle(msg.senderName) //标题
        mBuilder?.setContentText(msg.content)         //正文
        mBuilder?.setNumber(1)                       //设置信息条数
        mBuilder?.setOngoing(true)      //true使notification变为ongoing，用户不能手动清除
        val notification = mBuilder?.build()
        val notificationIntent = Intent(context, RecyclerViewChatActivity::class.java)

        /*add the followed two lines to resume the app same with previous statues*/
        notificationIntent.setAction(Intent.ACTION_MAIN)
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        /**/

        notificationIntent.putExtra("friendId", msg.senderId)
        notificationIntent.putExtra("nickname", msg.senderName)
        val contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        notification?.contentIntent = contentIntent
        mNotificationManager?.notify(NOTIFICATION_INT, notification)
    }
}