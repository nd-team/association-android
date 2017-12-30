package com.ike.sq.alliance.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.UserDetailContract
import com.ike.sq.alliance.ui.widget.XCRoundRectImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*

import java.lang.reflect.Type

import okhttp3.Call
import com.ike.sq.alliance.R.mipmap.mine_man
import com.ike.sq.alliance.R.mipmap.mine_women
import com.ike.sq.alliance.chat.activity.RecyclerViewChatActivity
import com.ike.sq.alliance.mvp.presenter.UserDetailPresenter
import com.ike.sq.alliance.network.NetworkUrl
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.ui.widget.image.CircleImageView


/**
 * 用户详情页
 *
 * Created by T-BayMax on 2017/9/9.

 */
class UserDetailActivity : BaseActivity(), View.OnClickListener, UserDetailContract.UserDetailView {

    private var user: UserBean? = null
    private var isChangeName = false
    private var mContext: Context? = null
    private val mId: String? = null
    private val isPhoneContact = false
    var nickname = "test"//聊天对象昵称
    var friendId = "0"
    var headPath = ""
    var isFriend:Boolean=false;
    lateinit var presenter: UserDetailContract.UserDetailPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        mContext = this
        // user = intent.getSerializableExtra("user") as UserBean?
        val it = intent
        nickname = it.getStringExtra("nickname")
        friendId = it.getStringExtra("friendId")
        isFriend=it.getBooleanExtra("isFriend",false)
        findUser()
        initClick()

    }

    fun findUser() {
        presenter = UserDetailPresenter(this@UserDetailActivity, this@UserDetailActivity)
        presenter.userInfo(App.token, friendId)
    }

    fun initClick() {
        ll_user_detail_back.setOnClickListener(this)
        ll_user_detail_displayName.setOnClickListener(this)
        ll_user_detail_moreInfo.setOnClickListener(this)
        btn_user_detail_recommed.setOnClickListener(this)
        btn_user_detail_addFriends.setOnClickListener(this)
        btn_send_message.setOnClickListener(this)
        btn_delete_friend.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_user_detail_back -> {
                this@UserDetailActivity.finish()
            }
            R.id.ll_user_detail_displayName -> {
                val intent = Intent(this@UserDetailActivity, ModifyRemarkActivity::class.java)
                intent.putExtra("friendId", friendId)
                startActivity(intent)
            }
            R.id.ll_user_detail_moreInfo -> {
            }
            R.id.btn_user_detail_recommed//马上推荐
            -> {
            }
            R.id.btn_user_detail_addFriends//添加好友
            -> {
            }
            R.id.btn_send_message   //发信息
            -> {
                val intent = Intent(this@UserDetailActivity, RecyclerViewChatActivity::class.java)
                intent.putExtra("friendId", friendId)
                intent.putExtra("nickname", nickname)
                intent.putExtra("headPath", headPath)
                intent.putExtra("isGroup", false)
                startActivity(intent)
            }
            R.id.btn_delete_friend -> AlertDialog.Builder(mContext!!)
                    .setTitle("删除好友")
                    .setPositiveButton("确定") { dialogInterface, i -> deleteFriends() }
                    .setNegativeButton("取消") { dialogInterface, i -> }
                    .show()
            else -> {
            }
        }/* Intent intent = new Intent(this, SetDisplayNameActivity.class);
                intent.putExtra("friendInfo", friendInfo);
                startActivityForResult(intent, 11);*//*Intent intent1 = new Intent(this, MoreUserDetailInfoActivity.class);
                intent1.putExtra("friendId", userId);
                startActivity(intent1);*///                startActivity(new Intent(this, RecommendActivity.class));
        //                startActivity(new Intent(this, SearchFriendActivity.class));
        //                RongIM.getInstance().startPrivateChat(mContext, userId, userName);
    }

    private fun deleteFriends() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 11 && resultCode == RESULT_OK) {
            if (data != null) {
                val displayName = data.getStringExtra("displayName")
                tv_user_detail_name!!.text = displayName
                tv_user_detail_displayName!!.text = displayName
                isChangeName = true
            }
        }
    }

    private fun initData() {
        ll_user_detail_displayName.visibility=View.VISIBLE;
        ll_user_detail_moreInfo.visibility=View.VISIBLE;
        if (isFriend){
            btn_delete_friend.visibility=View.VISIBLE
        }
        Picasso.with(this).load(NetworkUrl.BASE_ORIGINAL + user?.headPath).into(iv_user_detail_userIcon);
        when (user?.sexType) {
            "MAN" -> {
                iv_user_detail_sex.setImageResource(R.mipmap.mine_man)
            }
            "WOMAN" -> {
                iv_user_detail_sex.setImageResource(R.mipmap.mine_women)
            }
            "UNKNOWN" -> {

            }
        }

        if (user?.nickname.isNullOrEmpty()) {
            tv_user_detail_name.setText(user?.username);
            tv_user_detail_displayName.setText(user?.username);
        } else {
            tv_user_detail_name.setText(user?.nickname);
            tv_user_detail_displayName.setText(user?.nickname);

        }
        tv_user_detail_phone.text = user?.phone
        tv_user_detail_account.setText(user?.number);
        tv_user_detail_email.setText(user?.email);
        tv_user_detail_birthday.setText(user?.birthday);
        tv_user_detail_address.setText(user?.address);
        /* tv_user_detail_recommenerName.setText(userInfo.getRecommendUserId());
         tv_user_detail_claimerName.setText(userInfo.getClaimUserId());
         tv_user_detail_contributionNum.setText(userInfo.getContributionScore());
         tv_user_detail_creditScore.setText(userInfo.getCreditScore());
         tv_user_detail_intimacy.setText(userInfo.getIntimacy());
         initButtonVisable(userInfo.getStatus());*/
    }

    private fun initButtonVisable(status: String) {
        when (status) {
            "0" -> btn_user_detail_addFriends!!.visibility = View.VISIBLE
            "1" -> {
                btn_send_message!!.visibility = View.VISIBLE
                btn_delete_friend!!.visibility = View.VISIBLE
            }
        }

    }

    override fun applyFriend(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    override fun agreeFriend(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun refuseFriend(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun userInfo(userBean: UserBean) {
        user = userBean
        headPath = user!!.headPath
        initData()
    }

    override fun deleteFriend(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
/*
    override fun modifyRemark(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }*/

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }


}
