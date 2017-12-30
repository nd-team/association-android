package com.ike.sq.alliance.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.mvp.presenter.CreateGroupPresenter
import com.ike.sq.alliance.mvp.contract.CreateGroupView
import com.ike.sq.alliance.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.layout_title_top.*
import kotlinx.android.synthetic.main.view_item_friend.*

/**
 * 新建分组
 * Created by T-BayMax on 2017/9/12.
 */
class CreateGroupActivity : BaseActivity(), CreateGroupView {

    private var presenter: CreateGroupPresenter

    init {
        presenter = CreateGroupPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        btn_add_group.setOnClickListener(View.OnClickListener {
            var params = HashMap<String, String>(0)
            params.put("name", et_name.text.toString())
            params.put("userId", App.userBean.id)
            params.put("description", et_description.text.toString())
            presenter.addGroup(App.token, params)
        })
        iv_title_back.setOnClickListener(View.OnClickListener { this@CreateGroupActivity.finish() })
    }


    override fun addGroup(data: String) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
        this@CreateGroupActivity.finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}