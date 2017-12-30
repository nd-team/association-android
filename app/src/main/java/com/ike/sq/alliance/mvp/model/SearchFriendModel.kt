package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.FriendApi
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.contract.SearchFriendContract
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class SearchFriendModel(private val context: Context) : SearchFriendContract.SearchFriendModel {
    val retrofitClient: RetrofitClient
    val apiService: FriendApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(FriendApi::class.java)!!
    }

    override fun applyFriend(header: String, fieldMap: Map<String, String>): Observable<JsonResult<String>> {
        return apiService.applyFriend(header,fieldMap)
    }

    override fun getSearchFriendList(token: String): Observable<JsonResult<MutableList<UserBean>>> {

        return apiService.getSearchFriendList(token)
    }
}