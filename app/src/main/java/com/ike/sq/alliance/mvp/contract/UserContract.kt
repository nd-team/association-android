package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean

import io.reactivex.Observable

/**
 * 用户登录-获取用户信息
 *
 * Created by T-BayMax on 2017/8/28.
 */

class UserContract {
    interface UserView {
        fun login(results: String)
        fun userInfo(userBean: UserBean)
        fun showError(errorString: String)
    }

    interface Model {
        fun login(formMap: Map<String, String>): Observable<JsonResult<String>>
        fun userInfo(header: String): Observable<JsonResult<UserBean>>

    }

    interface Presenter {

        fun login(formMap: Map<String, String>, type: String)
        fun userInfo(formMap: String)
    }
}
