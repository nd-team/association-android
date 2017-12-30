/*
 * Created by T-BayMax  17-9-18 下午4:20
 */

package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.IModifyRemarkModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/18.
 */
class ModifyRemarkModel(private val context: Context) : IModifyRemarkModel {
    val retrofitClient: RetrofitClient
    val apiService: FriendApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(FriendApi::class.java)!!
    }

    override fun modifyRemark(token: String, fieldMap: Map<String, String>): Observable<JsonResult<String>> {
        return apiService.modifyRemark(token,fieldMap)
    }


}