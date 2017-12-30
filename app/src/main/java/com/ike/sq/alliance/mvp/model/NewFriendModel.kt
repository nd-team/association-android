package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.INewFriendModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/16.
 */
class NewFriendModel(private val context: Context) : INewFriendModel {
    val retrofitClient: RetrofitClient
    val apiService: FriendApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(FriendApi::class.java)!!
    }

    override fun getFriendList(token: String, type: String): Observable<JsonResult<MutableList<Friend>>> {
        return apiService.getFriendTypeList(token)
    }

    override fun agreeFriend(header: String, friendId: String): Observable<JsonResult<String>> {
        return apiService.agreeFriend(header, friendId)
    }

    override fun refuseFriend(header: String, friendId: String): Observable<JsonResult<String>> {
        return apiService.refuseFriend(header, friendId)
    }

}