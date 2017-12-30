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
 * Created by T-BayMax  17-9-19 上午10:03
 *
 */

package com.ike.sq.alliance.utils

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import java.util.Map


/**
 * Created by T-BayMax on 2017/9/19.
 */
 open class ActivityCollector {
    /**
     * 存放activity的列表
     */
    var activities: HashMap<Class<*>, Activity> = LinkedHashMap()

    /**
     * 添加Activity
     *
     * @param activity
     */
    open fun addActivity(activity: Activity, clz: Class<*>) {
        activities.put(clz, activity)
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    open fun <T : Activity> isActivityExist(clz: Class<T>): Boolean {
        var res: Boolean
        var activity = getActivity(clz);
        res = if (activity == null) {
            false
        } else {
            !(activity.isFinishing || activity.isDestroyed)

        }

        return res
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    open fun <T : Activity> getActivity(clazz: Class<T>): T {
        return activities[clazz] as T
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    open fun removeActivity(activity: Activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.javaClass);
        }
    }

    /**
     * 移除所有的Activity
     */
    open fun removeAllActivity() {
        if (activities != null && activities.size > 0) {
            var sets = activities.entries;

            for (s: MutableMap.MutableEntry<Class<*>, Activity> in sets) {
                if (!s.value.isFinishing) {
                    s.value.finish();
                }
            }
        }
        activities.clear();
    }


}