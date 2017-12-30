/*
 * Created by T-BayMax  17-9-18 下午4:15
 */

package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/18.
 */
interface IModifyRemarkView {

    /**
     * 修改备注
     */
    fun modifyRemark(msg: String)

    fun showError(errorString: String)
}

interface IModifyRemarkModel {

    fun modifyRemark(token: String, fieldMap: Map<String, String>): Observable<JsonResult<String>>
}

interface IModifyRemarkPresenter {


    fun modifyRemark(header: String, fieldMap: Map<String, String>)
}