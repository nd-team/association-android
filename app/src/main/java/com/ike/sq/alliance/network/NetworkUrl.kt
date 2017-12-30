package com.ike.sq.alliance.network

/**
 * Created by T-BayMax on 2017/8/30.
 */

class NetworkUrl {
    companion object {
        val BASE_URL: String//服务器地址
            get() = "http://192.168.0.95:8080"
        val BASE_WS: String//websocket服务
            get() = "ws://192.168.0.95:8080/chat/"
        val BASE_ORIGINAL:String//获取原图
        get() = BASE_URL+"/file/original/pic?path="
        val BASE_THUMBNAILS:String//获取缩略图
            get() = BASE_URL+"/file/original/pic?path="
        val BASE_DOWNLOAD:String//下载
            get() = BASE_URL+"/file/download?path="
    }
}