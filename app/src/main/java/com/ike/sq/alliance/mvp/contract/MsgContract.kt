package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.Msg
import io.reactivex.Observable

/**
 * 聊天消息记录
 *
 * Created by T-BayMax on 2017/9/16.
 */
interface IMsgListView {
    /**
     * 个人消息记录
     */
    fun getMsgReviverList(list: MutableList<Msg>)

    /**
     * 群消息记录
     */
    fun getMsgGroupList(list: MutableList<Msg>)
    fun showError(error: String)

}

interface IMsgListModel {
    fun getMsgReviverList(token: String, reviver: String): Observable<JsonResult<MutableList<Msg>>>
    fun getMsgGroupList(token: String, groupId: String): Observable<JsonResult<MutableList<Msg>>>
}

interface IMsgListPresenter {
    fun getMsgReviverList(token: String, reviver: String)
    fun getMsgGroupList(token: String, groupId: String)
}
