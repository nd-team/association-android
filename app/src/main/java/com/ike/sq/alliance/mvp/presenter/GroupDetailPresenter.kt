package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.CreateGroupPresenter
import com.ike.sq.alliance.mvp.contract.CreateGroupView
import com.ike.sq.alliance.mvp.contract.GroupDetailPresenter
import com.ike.sq.alliance.mvp.contract.GroupDetailView
import com.ike.sq.alliance.mvp.model.CreateGroupModel
import com.ike.sq.alliance.mvp.model.GroupDetailModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/12.
 */
class GroupDetailPresenter(private val context: Context,
                           private val view: GroupDetailView) : GroupDetailPresenter {


    val mModel: GroupDetailModel by lazy {
        GroupDetailModel(context)
    }

    override fun getMemberList(token: String, groupId: String) {
        val observable: Observable<JsonResult<MutableList<Friend>>>? = context.let { mModel.getMemberList(token, groupId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<MutableList<Friend>> ->
            if (result.code == 0)
                view.getMemberList(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun addGroupMember(token: String,groupId: String, params: MutableMap<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.addGroupMember(token,groupId, params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.addGroupMember(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun deleteGroupMember(token: String,groupId: String, params: MutableMap<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.deleteGroupMember(token,groupId, params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.deleteGroupMember(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun deleteGroup(header: String?, groupId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.deleteGroup(header, groupId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.deleteGroup(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun dismissGroup(header: String?, groupId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.dismissGroup(header, groupId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.dismissGroup(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

}