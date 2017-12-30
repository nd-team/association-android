package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.api.GroupApi
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.GroupListContract
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/11.
 */
class GroupListModel(private val context: Context) : GroupListContract.IGroupListModel {
    val retrofitClient: RetrofitClient
    val apiService: GroupApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(GroupApi::class.java)!!
    }
    override fun getGroupList(token: String): Observable<JsonResult<MutableList<GroupBean>>> {
        return apiService.getGroupList(token)
    }
}