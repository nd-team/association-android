package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.GroupApi
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.CreateGroupModel
import com.ike.sq.alliance.mvp.contract.GroupDetailModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/13.
 */
class GroupDetailModel(private val context: Context) : GroupDetailModel {
    val retrofitClient: RetrofitClient
    val apiService: GroupApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(GroupApi::class.java)!!
    }

    override fun getMemberList(token: String, groupId: String): Observable<JsonResult<MutableList<Friend>>> {

        return apiService.getGroupMember(token, groupId)
    }

    override fun addGroupMember(token: String,groupId: String, params: MutableMap<String, String>): Observable<JsonResult<String>> {
        return apiService.addGroupUser(token,groupId,params)    }

    override fun deleteGroupMember(token: String, groupId: String, params: MutableMap<String, String>): Observable<JsonResult<String>> {
        return apiService.deleteMember(token,groupId,params)
    }

    override fun deleteGroup(header: String?, groupId: String): Observable<JsonResult<String>> {
        return apiService.deleteGroup(header,groupId)
    }

    override fun dismissGroup(header: String?, groupId: String): Observable<JsonResult<String>> {
        return apiService.deleteGroup(header,groupId)
    }

}