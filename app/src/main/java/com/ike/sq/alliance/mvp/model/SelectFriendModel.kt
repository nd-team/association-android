package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.api.GroupApi
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.SelectFriendModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class SelectFriendModel(private val context: Context) : SelectFriendModel {

    val retrofitClient: RetrofitClient
    val apiService: FriendApi
    val groupApiService:GroupApi

    init {
         retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(FriendApi::class.java)!!
        groupApiService = retrofitClient.create(GroupApi::class.java)!!
    }
    override fun getFriendList(token: String): Observable<JsonResult<MutableList<Friend>>> {
        return apiService.getFriendList(token)
    }
    override fun addGroupUser(token: String, groupId: String, params: Map<String, String>): Observable<JsonResult<String>> {
        return groupApiService.addGroupUser(token,groupId,params)
    }

    override fun deleteMember(token: String, groupId: String, params: Map<String, String>): Observable<JsonResult<String>> {
        return groupApiService.deleteMember(token,groupId,params)
    }


}