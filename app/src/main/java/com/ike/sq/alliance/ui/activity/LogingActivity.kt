package com.ike.sq.alliance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ike.sq.alliance.App
import com.ike.sq.alliance.MainActivity
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.UserContract
import com.ike.sq.alliance.mvp.presenter.UserPersenter
import com.ike.sq.alliance.service.ConnectService
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.utils.PreferenceService
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 * Created by T-BayMax on 2017/9/4.
 */
class LogingActivity : BaseActivity(), UserContract.UserView {


    lateinit var account: String
    lateinit var password: String

    lateinit var preference: PreferenceService

    lateinit var presenter: UserPersenter

    override fun showError(eorrString: String) {
        Log.e("eorr", eorrString)
        Toast.makeText(this@LogingActivity, eorrString, Toast.LENGTH_LONG).show();

    }

    override fun login(results: String) {

        Log.e("token", results)
        preference.addSetting("token", results)
        App.token = results;
        presenter.userInfo(results)
    }

    override fun userInfo(userBean: UserBean) {
        userBean?.account = account
        userBean?.password = password
        App.userBean=userBean;
        preference.putBean("user", userBean)
        val intent = Intent(this@LogingActivity, ConnectService::class.java)
        startService(intent)
        val intentAct = Intent(this@LogingActivity, MainActivity::class.java)
        startActivity(intentAct)
        this@LogingActivity.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preference = PreferenceService(this@LogingActivity)
        presenter = UserPersenter(this, this)
        btn_login.setOnClickListener(View.OnClickListener {
            account = et_login_phone.text.toString()
            password = et_login_pwd.text.toString()
            val fieldMap = HashMap<String, String>()
            fieldMap.put("account", account)
            fieldMap.put("password", password)
            /*  fieldMap.put("loginType", "MOBILE")
              fieldMap.put("rememberMe", "true")*/
            presenter.login(fieldMap, "")
        })

    }
}