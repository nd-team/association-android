package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.contract.GroupListContract
import com.ike.sq.alliance.mvp.model.FriendModel
import com.ike.sq.alliance.mvp.model.GroupListModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class GroupListPresenter(private var context: Context,
                         private var view: GroupListContract.IGroupListView)
    : GroupListContract.IGroupListPresenter {

    val mModel: GroupListModel by lazy {
        GroupListModel(context)
    }
    override fun getGroupList(token: String) {
        val observable: Observable<JsonResult<MutableList<GroupBean>>>? = context.let { mModel.getGroupList(token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<GroupBean>> ->
            if (result.code == 0)
                view.getGroupList(result.data )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}