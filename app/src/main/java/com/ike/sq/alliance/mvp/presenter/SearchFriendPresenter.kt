package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.contract.SearchFriendContract
import com.ike.sq.alliance.mvp.model.FriendModel
import com.ike.sq.alliance.mvp.model.SearchFriendModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class SearchFriendPresenter(private var context: Context,
                            private var view: SearchFriendContract.SearchFriendView)
    : SearchFriendContract.SearchFriendPresenter {
    override fun applyFriend(header: String, fieldMap: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.applyFriend(header,fieldMap) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.applyFriend(result.msg )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })    }

    val mModel: SearchFriendModel by lazy {
        SearchFriendModel(context)
    }
    override fun getSearchFriendList(token: String) {
        val observable: Observable<JsonResult<MutableList<UserBean>>>? = context.let { mModel.getSearchFriendList(token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<UserBean>> ->
            if (result.code == 0)
                view.getSearchFriendList(result.data )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}