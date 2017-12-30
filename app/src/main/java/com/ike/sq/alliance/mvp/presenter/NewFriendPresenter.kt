package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.INewFriendPresenter
import com.ike.sq.alliance.mvp.contract.INewFriendView
import com.ike.sq.alliance.mvp.model.NewFriendModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/16.
 */
class NewFriendPresenter(var context: Context, var view: INewFriendView) : INewFriendPresenter {

    val model: NewFriendModel by lazy {
        NewFriendModel(context)
    }

    override fun agreeFriend(header: String, friendId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { model.agreeFriend(header, friendId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.agreeFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun refuseFriend(header: String, friendId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { model.refuseFriend(header, friendId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.refuseFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun getFriendList(token: String, type: String) {
        val observable: Observable<JsonResult<MutableList<Friend>>>? = context.let { model.getFriendList(token, type) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Friend>> ->
            if (result.code == 0)
                view.getFriendList(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}