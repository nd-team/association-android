package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.contract.SelectFriendPresenter
import com.ike.sq.alliance.mvp.contract.SelectFriendView
import com.ike.sq.alliance.mvp.model.FriendModel
import com.ike.sq.alliance.mvp.model.SelectFriendModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class SelectFriendPresenter(private var context: Context,
                            private var view: SelectFriendView)
    : SelectFriendPresenter {

    val mModel: SelectFriendModel by lazy {
        SelectFriendModel(context)
    }

    override fun getFriendList(token: String) {
        val observable: Observable<JsonResult<MutableList<Friend>>>? = context.let { mModel.getFriendList(token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Friend>> ->
            if (result.code == 0)
                view.getFriendList(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
    override fun addGroupUser(token: String, groupId: String, params: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? =
                context.let { mModel.addGroupUser(token,groupId,params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.addGroupUser(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun deleteMember(token: String, groupId: String, params: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? =
                context.let { mModel.deleteMember(token,groupId,params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.deleteMember(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

}