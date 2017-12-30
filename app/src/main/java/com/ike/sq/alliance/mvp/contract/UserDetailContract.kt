package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.JsonResult
import com.ike.sq.alliance.bean.UserBean

import io.reactivex.Observable

/**
 * 用户详情，好友详情
 *
 * Created by T-BayMax on 2017/8/30.
 */

class UserDetailContract {
    interface UserDetailView {
        /**
         * 申请好友
         *
         * @param msg
         */
        fun applyFriend(msg: String)

        /**
         * 同意好友申请
         */
        fun agreeFriend(msg: String)

        /**
         * 拒绝好友申请
         */
        fun refuseFriend(msg: String)

        /**
         * id查询个人信息
         *
         * @param userBean
         */
        fun userInfo(userBean: UserBean)

        /**
         * 删除好友
         *
         * @param msg
         */
        fun deleteFriend(msg: String)

        /**
         * 修改备注
         */
//        fun modifyRemark(msg: String)

        fun showError(errorString: String)
    }

    interface UserDetailModel {
        /**
         * 申请好友
         *
         * @param fieldMap
         * @return
         */
        fun applyFriend(token: String, fieldMap: Map<String, String>): Observable<JsonResult<String>>

        /**
         * 同意好友申请
         */
        fun agreeFriend(header: String, friendId: String): Observable<JsonResult<String>>

        /**
         * 拒绝好友申请
         */
        fun refuseFriend(header: String, friendId: String): Observable<JsonResult<String>>

        /**
         * 删除好友
         *
         * @param friendId
         * @param token
         */
        fun deleteFriend(friendId: String, token: String): Observable<JsonResult<String>>

//        fun modifyRemark(token: String,fieldMap: Map<String, String>): Observable<JsonResult<String>>
        /**
         * id查询个人信息
         *
         * @param header
         * @return
         */
        fun userInfo(header: String, userId: String): Observable<JsonResult<UserBean>>
    }

    interface UserDetailPresenter {
        /**
         * 申请好友
         *
         * @param token
         * @param fieldMap
         */
        fun applyFriend(token: String, fieldMap: Map<String, String>)

        /**
         * 同意好友申请
         */
        fun agreeFriend(header: String, friendId: String)

        /**
         * 拒绝好友申请
         */
        fun refuseFriend(header: String, friendId: String)

        /**
         * 删除好友
         *
         * @param friendId
         * @param token
         */
        fun deleteFriend(friendId: String, token: String)

//        fun modifyRemark(header: String, fieldMap: Map<String, String>)
        /**
         * id查询个人信息
         *
         * @param header
         */
        fun userInfo(header: String, userId: String)
    }
}
