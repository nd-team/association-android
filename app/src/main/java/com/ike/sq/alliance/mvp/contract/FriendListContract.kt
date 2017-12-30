package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult

import io.reactivex.Observable

/**
 * 好友列表
 *
 * Created by T-BayMax on 2017/8/30.
 */

class FriendListContract {
    interface friendView {

        fun getFriendList(data: MutableList<Friend>)

        fun showError(errorString: String)
    }

    interface friendModel {
        fun getFriendList(token: String): Observable<JsonResult<MutableList<Friend>>>
    }

    interface friendPresenter {
        fun getFriendList(token: String)
    }
}
