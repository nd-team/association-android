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
 * Created by T-BayMax  17-9-22 下午3:32
 *
 */

package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.mvp.contract.IRecyclerViewChatPresenter
import com.ike.sq.alliance.mvp.contract.IRecyclerViewChatView
import com.ike.sq.alliance.mvp.model.RecyclerViewChatModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by T-BayMax on 2017/9/22.
 */
class RecyclerViewChatPresenter(private var context: Context,
                                private var view: IRecyclerViewChatView) : IRecyclerViewChatPresenter {

    val model: RecyclerViewChatModel by lazy {
        RecyclerViewChatModel(context)
    }

    override fun upload(header: String, params:RequestBody) {

        val observable: Observable<JsonResult<MutableList<String>>>? = context.let { model.upload(header, params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<String>> ->
            if (result.code == 0)
                view.upload(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

}
