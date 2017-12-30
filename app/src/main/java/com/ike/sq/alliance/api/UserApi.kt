package com.bjike.issp.api

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import retrofit2.http.*
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/19.
 */
interface UserApi {


    /*companion object {
        val BASE_URL: String
            get() = "http://192.168.0.71:8080"
        val BASE_WS: String
            get() = "ws://192.168.0.71:8080"
    }
*/
    @FormUrlEncoded
    @POST("/login")
    fun login(@FieldMap fieldMap: Map<String, String>?): Observable<JsonResult<String>>

    @GET("/user/info")
    fun userInfo(@Header("token") header: String?): Observable<JsonResult<UserBean>>

    @GET("logout")
    fun logout(@Header("token") header: String?): Observable<JsonResult<String>>
}