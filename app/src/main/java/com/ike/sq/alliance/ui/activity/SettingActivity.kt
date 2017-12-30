package com.ike.sq.alliance.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ike.sq.alliance.App

import com.ike.sq.alliance.R
import com.ike.sq.alliance.dao.ChatDbManager
import com.ike.sq.alliance.dao.base.BaseManager
import com.ike.sq.alliance.mvp.contract.SettingView
import com.ike.sq.alliance.mvp.presenter.SettingPersenter
import com.ike.sq.alliance.utils.PersistentCookieStore
import com.ike.sq.alliance.utils.PreferenceService
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.Intent
import com.ike.sq.alliance.MainActivity
import com.ike.sq.alliance.service.ConnectService
import com.ike.sq.alliance.service.MyServiceConnection
import com.ike.sq.alliance.ui.base.BaseActivity

/**
 * 设置
 * Created by T-BayMax on 2017/9/14.
 */
class SettingActivity : BaseActivity(), SettingView {
    lateinit var ps: PreferenceService
    lateinit var pcs: PersistentCookieStore

    lateinit var dao: ChatDbManager
    lateinit var persenter: SettingPersenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initView()
        onViewClicked()
    }

    private fun initView() {
        persenter = SettingPersenter(this, this)
        ps = PreferenceService(this@SettingActivity)
        pcs = PersistentCookieStore(this@SettingActivity)

        dao = ChatDbManager()
    }

    fun onViewClicked() {

        btn_setting_out.setOnClickListener(View.OnClickListener {
            persenter.logout(App.token)
            ps.clearData()
            pcs.removeAll()
            dao.deleteAll()
            App.token=null
            App.userBean=null
            val intent = Intent(this@SettingActivity,MyServiceConnection::class.java)
            stopService(intent);//结束服务
            val intentc = Intent(this@SettingActivity,ConnectService::class.java)
            stopService(intentc);//结束服务，关闭webSocket
           // App.client.dispatcher().cancelAll();// to cancel all requests
            MainActivity().finish()
            var i=Intent(this@SettingActivity,LogingActivity::class.java)
            startActivity(i)//跳转登录页面
            this@SettingActivity.finish()

        })
        ll_setting_back.setOnClickListener(View.OnClickListener { this@SettingActivity.finish() })
    }

    override fun logout(results: String) {
        Log.e("logout",results)
    }

    override fun showError(errorString: String) {
        Log.e("logout",errorString)
    }

}
