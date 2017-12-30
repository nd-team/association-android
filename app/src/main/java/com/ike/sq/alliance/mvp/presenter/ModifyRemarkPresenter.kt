/*
 * Created by T-BayMax  17-9-18 下午4:20
 */

package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.contract.IModifyRemarkPresenter
import com.ike.sq.alliance.mvp.contract.IModifyRemarkView
import com.ike.sq.alliance.mvp.model.ModifyRemarkModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/9/18.
 */
class ModifyRemarkPresenter (private var context: Context,
                             private var view: IModifyRemarkView)
    : IModifyRemarkPresenter {
    val mModel: ModifyRemarkModel by lazy {
        ModifyRemarkModel(context)
    }

    override fun modifyRemark(header: String, fieldMap: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.modifyRemark(header, fieldMap) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.modifyRemark(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

}