package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.mvp.contract.IMsgListPresenter
import com.ike.sq.alliance.mvp.contract.IMsgListView
import com.ike.sq.alliance.mvp.model.MsgListModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/16.
 */
class MsgListPresenter(private var context: Context,
                       private var view: IMsgListView) : IMsgListPresenter {
    val mModel: MsgListModel by lazy {
        MsgListModel(context)
    }
    override fun getMsgReviverList(token: String, reviver: String) {
        val observable: Observable<JsonResult<MutableList<Msg>>>? = context.let { mModel.getMsgReviverList(token,reviver) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Msg>> ->
            if (result.code == 0)
                view.getMsgReviverList(result.data )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun getMsgGroupList(token: String, groupId: String) {
        val observable: Observable<JsonResult<MutableList<Msg>>>? = context.let { mModel.getMsgGroupList(token,groupId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Msg>> ->
            if (result.code == 0)
                view.getMsgGroupList(result.data )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}