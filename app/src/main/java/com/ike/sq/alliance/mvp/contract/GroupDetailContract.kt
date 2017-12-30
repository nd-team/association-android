package com.ike.sq.alliance.mvp.contract

import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.JsonResult
import io.reactivex.Observable

/**
 * 群详情
 *
 * Created by T-BayMax on 2017/9/13.
 */
interface GroupDetailView {
    /**
     * 添加群成员
     */
    fun addGroupMember(data: String)

    /**
     * 删除群成员
     */
    fun deleteGroupMember(data: String)

    /**
     * 获取群成员列表
     */
    fun getMemberList(data: MutableList<Friend>)

    /**
     * 解散群
     */
    fun deleteGroup(data: String)

    /**
     * 退群
     */
    fun dismissGroup(data:String)

    /**
     * 请求失败
     */
    fun showError(error: String)
}

interface GroupDetailModel {

    fun addGroupMember(token: String,groupId: String, params: MutableMap<String, String>): Observable<JsonResult<String>>
    fun deleteGroupMember(token: String, groupId: String, params: MutableMap<String, String>): Observable<JsonResult<String>>
    fun getMemberList(token: String, groupId: String): Observable<JsonResult<MutableList<Friend>>>
    fun deleteGroup(header: String?, groupId: String): Observable<JsonResult<String>>
    fun dismissGroup(header: String?, groupId: String): Observable<JsonResult<String>>
}

interface GroupDetailPresenter {

    fun addGroupMember(token: String,groupId: String, params: MutableMap<String, String>)
    fun deleteGroupMember(token: String, groupId: String, params: MutableMap<String, String>)
    fun getMemberList(token: String, groupId: String)
    fun deleteGroup(header: String?, groupId: String)
    fun dismissGroup(header: String?, groupId: String)
}