package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.model.FriendModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class FriendPresenter(private var context: Context,
                      private var view: FriendListContract.friendView)
    : FriendListContract.friendPresenter {

    val mModel: FriendModel by lazy {
        FriendModel(context)
    }
    override fun getFriendList(token: String) {
        val observable: Observable<JsonResult<MutableList<Friend>>>? = context.let { mModel.getFriendList(token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Friend>> ->
            if (result.code == 0)
                view.getFriendList(result.data )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}