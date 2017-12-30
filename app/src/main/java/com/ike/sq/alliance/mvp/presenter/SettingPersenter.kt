package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.SettingPresenter
import com.ike.sq.alliance.mvp.contract.SettingView
import com.ike.sq.alliance.mvp.contract.UserContract
import com.ike.sq.alliance.mvp.model.SettingModel
import com.ike.sq.alliance.mvp.model.UserModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/28.
 */

class SettingPersenter(var context: Context,
                       var view: SettingView) : SettingPresenter {
    val mModel: SettingModel by lazy {
        SettingModel(context)
    }




    override fun logout(token: String) {
        val observable: Observable<JsonResult<String>>? = context?.let { mModel.logout(token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view?.logout(result.msg )
            else
                view?.showError(result.msg)
        }, { x -> view?.showError(x.toString()) })
    }

}