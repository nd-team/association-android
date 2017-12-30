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
 * Created by T-BayMax  17-9-22 下午3:24
 *
 */

package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by T-BayMax on 2017/9/22.
 */
interface IRecyclerViewChatView {
    /**
     * 上传文件
     */
    fun upload(msg: MutableList<String>)

    fun showError(errorString: String)
}

interface IRecyclerViewChatModel {
    fun upload(header: String, params:RequestBody): Observable<JsonResult<MutableList<String>>>

}

interface IRecyclerViewChatPresenter {
    fun upload(header: String, params:  RequestBody)

}

