package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable

/**
 * 新朋友
 *
 * Created by T-BayMax on 2017/9/16.
 */
interface INewFriendView {

    /**
     * 申请列表
     */
    fun getFriendList(data: MutableList<Friend>)

    /**
     * 同意好友申请
     */
    fun agreeFriend(msg: String)

    /**
     * 拒绝好友申请
     */
    fun refuseFriend(msg: String)

    fun showError(errorStr: String)

}

interface INewFriendModel {
    fun getFriendList(token: String, type: String): Observable<JsonResult<MutableList<Friend>>>

    /**
     * 同意好友申请
     */
    fun agreeFriend(header: String, friendId: String): Observable<JsonResult<String>>

    /**
     * 拒绝好友申请
     */
    fun refuseFriend(header: String, friendId: String): Observable<JsonResult<String>>

}

interface INewFriendPresenter {

    /**
     * 同意好友申请
     */
    fun agreeFriend(header: String, friendId: String)

    /**
     * 拒绝好友申请
     */
    fun refuseFriend(header: String, friendId: String)

    fun getFriendList(token: String, type: String)
}