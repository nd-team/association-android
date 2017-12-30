package com.ike.sq.alliance.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.ike.sq.alliance.bean.UserBean
import java.io.*
import java.util.*

/**
 * @author T-BayMax
 */
class PreferenceService(private val context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_NAME,
                Context.MODE_PRIVATE)
    }

    /**
     * 保存设置
     *
     * @param key
     * @param value
     */
    fun addSetting(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)

        editor.commit()
    }

    /**
     * 获取设置
     *
     * @return
     */
    fun getSetting(key: String): String {
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString(key, "")
    }

    /**
     * 存放实体类以及任意类型
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    fun putBean(key: String, obj: Serializable?) {

        try {
            var baos = ByteArrayOutputStream();
            var oos = ObjectOutputStream(baos);
            oos.writeObject(obj);
            var string64 = String(Base64.encode(baos.toByteArray(), 0))
            val editor = sharedPreferences.edit()
            editor.putString(key, string64).commit()
        } catch (e: IOException) {
            e.printStackTrace();
        }

    }

    fun getBean(key: String): Serializable {
        var obj: Serializable? = null;
        try {
            var base64 = sharedPreferences.getString(key, "")

            var base64Bytes = Base64.decode(base64.toByteArray(), 1)
            var bais = ByteArrayInputStream(base64Bytes);
            var ois = ObjectInputStream(bais)
            obj = ois.readObject() as Serializable
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return obj!!
    }

    /**
     * 清掉某个设置
     *
     * @param key
     */
    fun clearEditor(key: String) {
        /**开始清除SharedPreferences中保存的内容 */
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.commit()
    }

    /**
     * 清除所有设置
     */
    fun clearData() {
        sharedPreferences.edit().clear().commit()
    }

    companion object {
        private val SHARED_NAME = "bjike"
    }
}