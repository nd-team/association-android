package com.ike.sq.alliance.api

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.Msg
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by T-BayMax on 2017/9/16.
 */
interface MsgApi {
    /**
     * 个人消息记录
     */
    @GET("/chat/msg/point/{reviver}")
    fun getMsgReviver(@Header("token") header: String?, @Path("reviver") reviver: String): Observable<JsonResult<MutableList<Msg>>>

    /**
     * 群消息记录
     */
    @GET("/chat/msg/group/{groupId}")
    fun getMsgGroup(@Header("token") header: String?, @Path("groupId") groupId: String): Observable<JsonResult<MutableList<Msg>>>

    //@Multipart
    @POST("/chat/file/upload")
    fun upload(@Header("token") header: String?, @Body maps: RequestBody?): Observable<JsonResult<MutableList<String>>>

}