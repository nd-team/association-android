package com.ike.sq.alliance.api

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable
import retrofit2.http.*


/**
 * Created by T-BayMax on 2017/9/11.
 */
interface GroupApi {
    /**
     * 群列表
     */
    @GET("/chat/group/user/list")
    fun getGroupList(@Header("token") header: String?): Observable<JsonResult<MutableList<GroupBean>>>

    /**
     * 创建群
     */
    @FormUrlEncoded
    @POST("/chat/group/add")
    fun addGroup(@Header("token") header: String?, @FieldMap fieldMap: Map<String, String>?): Observable<JsonResult<String>>

    /**
     * 解散群
     */
    @DELETE("/chat/group/delete/{id}")
    fun deleteGroup(@Header("token") header: String?, @Path("id") groupId: String): Observable<JsonResult<String>>

    /**
     * 添加群成员
     */
    @FormUrlEncoded
    @POST("/add/to/group/{groupId}")
    fun addGroupUser(@Header("token") header: String?, @Path("groupId") groupId: String, @FieldMap params: Map<String, String>): Observable<JsonResult<String>>

    /**
     * 群列表
     */
    @GET("/chat/group/member/{groupId}")
    fun getGroupMember(@Header("token") header: String?, @Path("groupId") groupId: String): Observable<JsonResult<MutableList<Friend>>>

    /**
     * 删除群成员
     */
    @DELETE("/delete/by/{groupId}")
    fun deleteMember(@Header("token") header: String?, @Path("groupId") groupId: String, @FieldMap params: Map<String, String>): Observable<JsonResult<String>>
}