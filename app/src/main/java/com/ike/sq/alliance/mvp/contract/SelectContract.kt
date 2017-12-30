package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable

/**
 * 选择好友
 *
 * Created by T-BayMax on 2017/9/14.
 */
interface SelectFriendView {
    /**
     * 好友列表
     */
    fun getFriendList(data: MutableList<Friend>)

    /**
     * 添加成员
     */
    fun addGroupUser(data: String)

    /**
     * 删除成员
     */
    fun deleteMember(data: String)

    fun showError(errorString: String)
}

interface SelectFriendModel {
    fun getFriendList(token: String): Observable<JsonResult<MutableList<Friend>>>
    fun addGroupUser(token: String, groupId: String, params: Map<String, String>): Observable<JsonResult<String>>
    fun deleteMember(token: String, groupId: String, params: Map<String, String>): Observable<JsonResult<String>>
}

interface SelectFriendPresenter {
    fun getFriendList(token: String)
    fun addGroupUser(token: String, groupId: String, params: Map<String, String>)
    fun deleteMember(token: String, groupId: String, params: Map<String, String>)
}