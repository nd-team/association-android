/*
 *
 *  ,--^----------,--------,-----,-------^--,
 *   | |||||||||   `--------'     |          O
 *   `+---------------------------^----------|
 *     `\_,-------, _________________________|
 *       / XXXXXX /`|     /
 *      / XXXXXX /  `\   /
 *     / XXXXXX /\______(
 *    / XXXXXX /
 *   / XXXXXX /
 *  (________(
 *   `------'
 *
 * Created by T-BayMax  17-9-18 下午5:18
 *
 */



package com.ike.sq.alliance.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ike.sq.alliance.App

import com.ike.sq.alliance.R
import com.ike.sq.alliance.mvp.contract.IModifyRemarkView
import com.ike.sq.alliance.mvp.presenter.ModifyRemarkPresenter
import com.ike.sq.alliance.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_modify_remark.*
import kotlinx.android.synthetic.main.layout_title_top.*

/**
 * 修改好友备注
 */
class ModifyRemarkActivity : BaseActivity(), IModifyRemarkView {

    var friendId: String = ""
    lateinit var presenter: ModifyRemarkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_remark)
        presenter = ModifyRemarkPresenter(this@ModifyRemarkActivity, this@ModifyRemarkActivity)
        friendId = intent.getStringExtra("friendId")
        tv_title_right.visibility = View.VISIBLE
        tv_title_right.text = "确定"
        iv_title_back.setOnClickListener {
            this.finish()
        }
        tv_title_right.setOnClickListener {
            if (et_remark.text.isNullOrBlank()) {
                Toast.makeText(this, "备注不能为空", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var fieldMap = HashMap<String, String>(0)
            fieldMap.put("friendId", friendId)
            fieldMap.put("nickname", et_remark.text.toString())
            presenter.modifyRemark(App.token, fieldMap)
        }
    }


    override fun modifyRemark(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        this@ModifyRemarkActivity.finish()
    }

    override fun showError(errorString: String) {
        Toast.makeText(this, errorString, Toast.LENGTH_LONG).show()
    }

}
