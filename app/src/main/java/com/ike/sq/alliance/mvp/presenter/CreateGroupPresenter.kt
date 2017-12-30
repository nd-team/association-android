package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.mvp.model.CreateGroupModel
import com.ike.sq.alliance.mvp.contract.CreateGroupPresenter
import com.ike.sq.alliance.mvp.contract.CreateGroupView
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable


/**
 * Created by T-BayMax on 2017/9/12.
 */
class CreateGroupPresenter (private val context: Context,
private val view: CreateGroupView):CreateGroupPresenter {


    val mModel: CreateGroupModel by lazy {
        CreateGroupModel(context)
    }
    override fun addGroup(token: String, params: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.addGroup(token,params) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.addGroup(result.msg )
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
}