package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.MsgApi
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.mvp.contract.IMsgListModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/16.
 */
class MsgListModel(private val context: Context) : IMsgListModel {
    val retrofitClient: RetrofitClient
    val apiService: MsgApi
    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(MsgApi::class.java)!!
    }
    override fun getMsgReviverList(token: String, reviver: String): Observable<JsonResult<MutableList<Msg>>> {
        return apiService.getMsgReviver(token,reviver)
    }

    override fun getMsgGroupList(token: String, groupId: String): Observable<JsonResult<MutableList<Msg>>> {
       return apiService.getMsgGroup(token,groupId)
    }
}