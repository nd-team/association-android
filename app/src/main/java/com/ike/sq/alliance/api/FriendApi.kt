package com.ike.sq.alliance.api

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by T-BayMax on 2017/8/30.
 */
interface FriendApi {
    /**
     * 好友列表
     */
    @GET("/chat/friend/list")
    fun getFriendList(@Header("token") header: String?): Observable<JsonResult<MutableList<Friend>>>

    /**
     * 好友列表
     */
    @GET("/chat/friend/type/list")
    fun getFriendTypeList(@Header("token") header: String?): Observable<JsonResult<MutableList<Friend>>>

    /**
     * 查找好友
     */
    @GET("/user/search/{account}")
    fun getSearchFriendList(@Path("account") account: String?): Observable<JsonResult<MutableList<UserBean>>>

    /**
     * 好友详情
     */
    @GET("/user/find/{userId}")
    fun userInfo(@Header("token") header: String?, @Path("userId") userId: String?): Observable<JsonResult<UserBean>>

    /**
     * 好友申请
     */
    @FormUrlEncoded
    @POST("/chat/friend/apply")
    fun applyFriend(@Header("token") header: String?, @FieldMap fieldMap: Map<String, String>?): Observable<JsonResult<String>>

    /**
     * 同意好友申请
     */
    @PUT("/chat/friend/agree/{friendId}")
    fun agreeFriend(@Header("token") header: String?, @Path("friendId") friendId: String): Observable<JsonResult<String>>

    /**
     * 拒绝好友申请
     */
    @FormUrlEncoded
    @PUT("/chat/friend/refuse")
    fun refuseFriend(@Header("token") header: String?, @Field("friendId") friendId: String): Observable<JsonResult<String>>

    /**
     * 删除好友
     */
    @DELETE("/chat/friend/delete/{friendId}")
    fun deleteFriend(@Header("token") header: String?, @Part("friendId") friendId: String): Observable<JsonResult<String>>

    @FormUrlEncoded
    @PUT("/chat/friend/remark")
    fun modifyRemark(@Header("token") header: String?, @FieldMap fieldMap: Map<String, String>?): Observable<JsonResult<String>>
}