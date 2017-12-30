package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable


/**
 * 群列表
 *
 * Created by T-BayMax on 2017/9/11.
 */
class GroupListContract {
    interface IGroupListView {
        fun getGroupList(list: MutableList<GroupBean>)
        fun showError(error:String)

    }

    interface IGroupListModel {
        fun getGroupList(token: String): Observable<JsonResult<MutableList<GroupBean>>>
    }

    interface IGroupListPresenter {
        fun getGroupList(token: String)
    }

}