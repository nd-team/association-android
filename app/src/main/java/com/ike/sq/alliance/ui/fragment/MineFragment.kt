package com.ike.sq.alliance.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.ike.sq.alliance.R


import android.app.Activity.RESULT_OK
import com.ike.sq.alliance.App
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.ui.activity.SettingActivity
import kotlinx.android.synthetic.main.fragment_mine.view.*

/**
 * 我的
 * Created by T-BayMax on 2017/9/10.
 */

class MineFragment : Fragment(), View.OnClickListener {
    lateinit var containerView: View
    private val PERMISSION_DOLOCATIONPERMISSION = arrayOf("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", "android.permission.ACCESS_COARSE_LOCATION")
   /* private val sp: SharedPreferences? = null
    private val userPortraitUrl: String? = null
    private val mobile: String? = null
    private val birthday: String? = null
    private val nickName: String? = null
    private val sex: String? = null
    private val useId: String? = null
    private val email: String? = null
    private val recommendUserId: String? = null
    private val address: String? = null
    private val experience: String? = null
    private val creditScore: String? = null
    private val contributionScore: String? = null
    private val claimUserId: String? = null
    private val favour: String? = null
    private val checkVip: String? = null*/
    lateinit var user:UserBean

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        containerView = inflater!!.inflate(R.layout.fragment_mine, container, false)
        initView()
        return containerView
    }

    private fun initView() {
        user=App.userBean
        containerView.ll_mine_setting.setOnClickListener(this)
        containerView.tv_mine_name.text=user.nickname?:""
        containerView.tv_mine_account.text=user.number?:""
        containerView.tv_mine_email.text=user.email?:""
        containerView.tv_mine_phone.text=user.phone?:""
        containerView.tv_mine_birthday.text=user.birthday?:""
        containerView.tv_mine_address.text=user.address?:""
        if (user.sexType==="MAN"){
            containerView.iv_mine_sex.setImageResource(R.mipmap.mine_man)
        }else if(user.sexType==="WOMAN"){
            containerView.iv_mine_sex.setImageResource(R.mipmap.mine_women)
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_mine_sign -> {
            }
            R.id.iv_mine_card -> {
            }
            R.id.iv_mine_edit -> {
            }
            R.id.ll_mine_recommend -> {
            }
            R.id.ll_mine_wasRecomend -> {
            }
            R.id.ll_mine_wallet -> {
            }
            R.id.ll_mine_contacts -> {
            }
            R.id.ll_mine_QR_code -> {
            }
            R.id.ll_mine_feedback -> {
            }
            R.id.ll_mine_applay_vip -> {
            }
            R.id.ll_mine_setting -> {
                var i = Intent(activity, SettingActivity::class.java)
                startActivity(i)
            }
            R.id.tv_mine_weather ->
                //android6.0 打开位置权限
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, PERMISSION_DOLOCATIONPERMISSION, 1005)
                } else {
                    // startActivity(new Intent(getContext(), WeatherForecastActivity.class));
                }
        }// startActivity(new Intent(getContext(), SignPickerActivity.class));
        //  startActivity(new Intent(getContext(), MineCardActivity.class));
        // startActivityForResult(new Intent(getActivity(), PersonalInformationActivity.class), 110);
        // startActivity(new Intent(getContext(), RecommendActivity.class));
        // startActivity(new Intent(getContext(), MineRecomendActivity.class));
        // JrmfClient.intentWallet(getActivity());
        // startActivity(new Intent(getContext(), RelationMapActivity.class));
        /*Intent intent = new Intent(getContext(), MineQRCodeActivity.class);
                intent.putExtra("userId", useId);
                intent.putExtra("userPortraitUrl", userPortraitUrl);
                intent.putExtra("userName", nickName);
                intent.putExtra("sex", sex);
                startActivity(intent);*/// startActivity(new Intent(getContext(), FeedBackActivity.class));
        // startActivityForResult(new Intent(getContext(), ApplayVIPActivity.class), 111);
        // startActivity(new Intent(getContext(), SettingActivity.class));
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 110 && resultCode == RESULT_OK) {
            initView()
        } else if (requestCode == 111 && resultCode == RESULT_OK) {
            // getMineUserInfo(useId);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 1005) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                // startActivity(new Intent(getContext(), WeatherForecastActivity.class));
            } else {
                Toast.makeText(context, "查询天气需要有定位权限", Toast.LENGTH_LONG).show()
            }
        }
    }

}
