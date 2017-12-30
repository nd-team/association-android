package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean

import io.reactivex.Observable

/**
 * 查找好友
 *
 * Created by T-BayMax on 2017/8/30.
 */

class SearchFriendContract {
    interface SearchFriendView {

        fun getSearchFriendList(data: MutableList<UserBean>)
        fun applyFriend(msg: String)

        fun showError(errorString: String)
    }

    interface SearchFriendModel {
        fun getSearchFriendList(token: String): Observable<JsonResult<MutableList<UserBean>>>
        fun applyFriend(header: String, fieldMap: Map<String, String>): Observable<JsonResult<String>>
    }

    interface SearchFriendPresenter {
        fun getSearchFriendList(token: String)
        fun applyFriend(header: String, fieldMap: Map<String, String>)
    }
}
