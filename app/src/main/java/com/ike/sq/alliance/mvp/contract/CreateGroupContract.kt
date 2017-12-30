package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable

/**
 * 添加分组
 *
 * Created by T-BayMax on 2017/9/12.
 */
interface CreateGroupView {
    fun addGroup(data: String)
    fun showError(error:String)
}

interface CreateGroupModel {
    fun addGroup(token:String,params: Map<String, String>): Observable<JsonResult<String>>
}

interface CreateGroupPresenter {
    fun addGroup(token:String,params: Map<String, String>)
}