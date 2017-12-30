package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.SearchFriendContract
import com.ike.sq.alliance.mvp.contract.UserDetailContract
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class UserDetailModel(private val context: Context) : UserDetailContract.UserDetailModel {

    val retrofitClient: RetrofitClient
    val apiService: FriendApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(FriendApi::class.java)!!
    }

    override fun applyFriend(token: String, fieldMap: Map<String, String>): Observable<JsonResult<String>> {
        return apiService.applyFriend(token, fieldMap)
    }

    override fun agreeFriend(header: String, friendId: String): Observable<JsonResult<String>> {
        return apiService.agreeFriend(header, friendId)
    }

    override fun refuseFriend(header: String, friendId: String): Observable<JsonResult<String>> {
        return apiService.refuseFriend(header, friendId)
    }

    override fun userInfo(header: String, userId: String): Observable<JsonResult<UserBean>> {
        return apiService.userInfo(header, userId)
    }

    override fun deleteFriend(friendId: String, token: String): Observable<JsonResult<String>> {

        return apiService.deleteFriend(token, friendId!!)
    }

   /* override fun modifyRemark(token: String,fieldMap: Map<String, String>): Observable<JsonResult<String>> {
        return apiService.modifyRemark(token,fieldMap)
    }*/

}