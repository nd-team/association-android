/*
 *
 *  ,--^----------,--------,-----,-------^--,
 *   | |||||||||   `--------'     |          O
 *   `+---------------------------^----------|
 *     `\_,-------, _________________________|
 *       / XXXXXX /`|     /
 *      / XXXXXX /  `\   /
 *     / XXXXXX /\______(
 *    / XXXXXX /
 *   / XXXXXX /
 *  (________(
 *   `------'
 *
 * Created by T-BayMax  17-9-22 下午3:28
 *
 */

package com.ike.sq.alliance.mvp.model

import android.content.Context
import com.bjike.issp.network.RetrofitClient
import com.ike.sq.alliance.api.MsgApi
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.IRecyclerViewChatModel
import com.ike.sq.alliance.network.NetworkUrl
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by T-BayMax on 2017/9/22.
 */
class RecyclerViewChatModel(private val context: Context) : IRecyclerViewChatModel {
    val retrofitClient: RetrofitClient
    val apiService: MsgApi

    init {
        retrofitClient = RetrofitClient.getInstance(context, NetworkUrl.BASE_URL)
        apiService = retrofitClient.create(MsgApi::class.java)!!
    }

    override fun upload(header: String, params : RequestBody): Observable<JsonResult<MutableList<String>>> {
        return apiService.upload(header, params)
    }
}