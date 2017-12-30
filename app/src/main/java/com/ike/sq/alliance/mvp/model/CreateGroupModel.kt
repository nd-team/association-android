package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.api.GroupApi
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.CreateGroupModel
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class CreateGroupModel(private val context: Context) : CreateGroupModel {
    val retrofitClient: RetrofitClient
    val apiService: GroupApi

    init {
         retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
         apiService = retrofitClient.create(GroupApi::class.java)!!
    }
    override fun addGroup(token:String,params: Map<String, String>): Observable<JsonResult<String>> {

        return apiService.addGroup(token,params)
    }
}