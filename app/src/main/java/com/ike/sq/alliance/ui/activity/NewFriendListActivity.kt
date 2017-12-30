package com.ike.sq.alliance.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import android.widget.Toast
import com.ike.sq.alliance.App

import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.mvp.contract.INewFriendView
import com.ike.sq.alliance.mvp.presenter.NewFriendPresenter
import com.ike.sq.alliance.ui.adapter.NewFriendListItemAdapter
import com.ike.sq.alliance.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_new_friend_list.*

/**
 * 新朋友
 * Created by T-BayMax  2017/9/16
 **/
class NewFriendListActivity : BaseActivity(), INewFriendView {
    lateinit var presenter: NewFriendPresenter
    lateinit var token: String
    lateinit var list: MutableList<Friend>

    lateinit var adapter: NewFriendListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_friend_list)
        presenter = NewFriendPresenter(this, this)
        token = App.token
        initView()
        initData()
        initClick()
    }

    private fun initView() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = OrientationHelper.VERTICAL
        rv_new_friend!!.layoutManager = layoutManager
        adapter = NewFriendListItemAdapter(this)
        rv_new_friend.adapter = adapter
    }

    private fun initData() {
        presenter.getFriendList(token, "")
    }

    private fun initClick() {
        adapter.setOnItemClickLitener(object : NewFriendListItemAdapter.OnItemClickLitener {
            override fun onItemClick(view: View, friend: Friend) {
                val intent = Intent(this@NewFriendListActivity, UserDetailActivity::class.java)
                intent.putExtra("friendId", friend.id)
                intent.putExtra("nickname", friend.nickname)
                intent.putExtra("isFriend",false)
                startActivity(intent)
            }

            override fun onItemAgree(view: View, friend: Friend) {
                presenter.agreeFriend(token, friend.id!!)
            }
        })
    }

    override fun getFriendList(data: MutableList<Friend>) {
        list = data
        if (list.size > 0)
            adapter.setData(this.list)
    }

    override fun agreeFriend(msg: String) {
        Toast.makeText(this@NewFriendListActivity, msg, Toast.LENGTH_LONG).show()
    }

    override fun refuseFriend(msg: String) {
        Toast.makeText(this@NewFriendListActivity, msg, Toast.LENGTH_LONG).show()
    }

    override fun showError(errorStr: String) {
        Toast.makeText(this@NewFriendListActivity, errorStr, Toast.LENGTH_LONG).show()
    }

}
