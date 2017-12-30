package com.ike.sq.alliance.mvp.presenter

import android.content.Context
import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.UserDetailContract
import com.ike.sq.alliance.mvp.model.UserDetailModel
import com.ike.sq.alliance.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by T-BayMax on 2017/8/30.
 */
class UserDetailPresenter(private var context: Context,
                          private var view: UserDetailContract.UserDetailView)
    : UserDetailContract.UserDetailPresenter {
    val mModel: UserDetailContract.UserDetailModel by lazy {
        UserDetailModel(context)
    }

    override fun applyFriend(token: String, fieldMap: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.applyFriend(token, fieldMap) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.applyFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun agreeFriend(header: String, friendId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.agreeFriend(header, friendId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.agreeFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun refuseFriend(header: String, friendId: String) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.refuseFriend(header, friendId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.refuseFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun userInfo(header: String, userId: String) {
        val observable: Observable<JsonResult<UserBean>>? = context.let { mModel.userInfo(header, userId) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<UserBean> ->
            if (result.code == 0)
                view.userInfo(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }

    override fun deleteFriend(friendId: String, token: String) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.deleteFriend(friendId, token) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.deleteFriend(result.msg)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
/*

    override fun modifyRemark(header: String, fieldMap: Map<String, String>) {
        val observable: Observable<JsonResult<String>>? = context.let { mModel.modifyRemark(header, fieldMap) }
        observable?.applySchedulers()?.subscribe({ result: JsonResult<String> ->
            if (result.code == 0)
                view.modifyRemark(result.data)
            else
                view.showError(result.msg)
        }, { x -> view.showError(x.toString()) })
    }
*/

}