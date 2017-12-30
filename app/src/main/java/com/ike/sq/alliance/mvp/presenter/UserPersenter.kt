package com.ike.sq.alliance.mvp.presenter

import android.content.Context

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.UserContract
import com.ike.sq.alliance.mvp.contract.UserContract.Presenter
import com.ike.sq.alliance.mvp.model.UserModel
import com.ike.sq.alliance.utils.applySchedulers

import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/28.
 */

class UserPersenter(context: Context,
                    view: UserContract.UserView) : Presenter {



    var mContext: Context? = null
    var mView: UserContract.UserView? = null
    val mModel: UserModel by lazy {
        UserModel(context)
    }

    init {
        mView = view
        mContext = context
    }


    override fun login(formMap: Map<String, String>, type: String) {
        val observable: Observable<JsonResult<String>>? = mContext?.let { mModel.login(formMap) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                mView?.login(result.data )
            else
                mView?.showError(result.msg)
        }, { x -> mView?.showError(x.toString()) })
    }
    override fun userInfo(header: String) {
        val observable: Observable<JsonResult<UserBean>>? = mContext?.let { mModel.userInfo(header) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<UserBean> ->
            if (result.code == 0)
                mView?.userInfo(result.data )
            else
                mView?.showError(result.msg)
        }, { x -> mView?.showError(x.toString()) })
    }
}
