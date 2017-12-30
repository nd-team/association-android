package com.ike.sq.alliance.mvp.model

import android.content.Context

import com.bjike.issp.api.UserApi
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.UserContract
import com.ike.sq.alliance.network.NetworkUrl

import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/28.
 */

class UserModel(private val context: Context) : UserContract.Model {
    val retrofitClient: RetrofitClient
    val apiService: UserApi

    init {
         retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
         apiService = retrofitClient.create(UserApi::class.java)!!
    }
    override fun userInfo(header: String): Observable<JsonResult<UserBean>> {

        return apiService.userInfo(header)
    }

    override fun login(formMap: Map<String, String>): Observable<JsonResult<String>> {

        return apiService.login(formMap)
    }
}
