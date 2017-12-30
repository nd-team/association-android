package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import io.reactivex.Observable

/**
 * 设置-退出登录
 *
 * Created by T-BayMax on 2017/9/15.
 */
interface SettingView {
    fun logout(results: String)
    fun showError(errorString: String)
}

interface SettingModel {
    fun logout(token:  String): Observable<JsonResult<String>>

}

interface SettingPresenter {

    fun logout(token: String)

}