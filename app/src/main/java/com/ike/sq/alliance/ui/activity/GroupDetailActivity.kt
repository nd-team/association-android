package com.ike.sq.alliance.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

import com.google.gson.Gson
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.dao.GroupDbManager
import com.ike.sq.alliance.mvp.presenter.GroupDetailPresenter
import com.ike.sq.alliance.mvp.contract.GroupDetailView
import com.ike.sq.alliance.ui.adapter.MemberGridViewItemAdapter
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.ui.widget.DemoGridView
import com.ike.sq.alliance.ui.widget.SwitchButton
import com.ike.sq.alliance.utils.file.image.MyBitmapUtils
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.layout_title_top.*

import java.io.File
import java.lang.reflect.Type

import okhttp3.Call
import java.io.Serializable

/**
 * 群组详情
 */
class GroupDetailActivity : BaseActivity(), GroupDetailView, CompoundButton.OnCheckedChangeListener {

    private var list: MutableList<Friend>? = null//群成员
    private var showList: MutableList<Friend>? = null//显示的群成员
    private var mGroup: GroupBean? = null
    private var isCreated = false   //是否是群主
    var isShow = false//是否显示所有群成员

    var presenter: GroupDetailPresenter
    lateinit var adapter: MemberGridViewItemAdapter

    private var token: String


    init {
        presenter = GroupDetailPresenter(this, this)
        token = App.token
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)
        mGroup = intent.getSerializableExtra("groupBean") as GroupBean?
        initView()
        presenter.getMemberList(token, mGroup!!.id)
        initClick()
    }


    private fun initView() {
        list = ArrayList<Friend>(0)
        adapter = MemberGridViewItemAdapter(this, isCreated)
        gridview.adapter = adapter
        tv_group_member_size.visibility = View.GONE
        updateAdapterData()
        if (App.userBean.id.equals(mGroup!!.userId)) {
            isCreated = true
            btn_group_quit.setText(R.string.dismiss_group)
        }
        gridview.setOnItemClickListener { _, _, position, _ ->
            var friend = showList!![position]
            if (App.userBean.id == friend.id) {
                return@setOnItemClickListener
            }
            if (showList!!.size == position - 1) { //群成员提出群
                var intent = Intent(this@GroupDetailActivity, SelectActivity::class.java)
                val bundle = Bundle()  //传递对象
                bundle.putParcelableArrayList("memberList", list as ArrayList<Friend>)
                intent.putExtras(bundle)
                intent.putExtra("isadd",1);
                intent.putExtra("groupId",mGroup!!.id)
                startActivity(intent)

            } else if ((showList!!.size == position - 2 && isCreated)
                    || (!isCreated && position == showList!!.size - 1)) { //添加群成员
                var intent = Intent(this@GroupDetailActivity, SelectActivity::class.java)
                val bundle = Bundle()  //传递对象
                bundle.putParcelableArrayList("memberList", list as ArrayList<Friend>)
                intent.putExtras(bundle)
                intent.putExtra("isadd",0);
                intent.putExtra("groupId",mGroup!!.id)
                startActivity(intent)
            } else {
                var intent = Intent(this@GroupDetailActivity, UserDetailActivity::class.java)
                intent.putExtra("friendId", friend.id)
                intent.putExtra("nickname", friend.nickname)
                startActivity(intent)
            }
        }

    }

    fun initClick() {

        iv_title_back.setOnClickListener(View.OnClickListener { this@GroupDetailActivity.finish() })
        btn_group_quit.setOnClickListener(View.OnClickListener {
            if (isCreated) {
                presenter.deleteGroup(token, mGroup!!.id)
            } else {
                presenter.dismissGroup(token, mGroup!!.id)
            }
        })
        tv_group_member_size.setOnClickListener(View.OnClickListener {
            isShow = true
            updateAdapterData()
            tv_group_member_size.visibility = View.GONE
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

    }

    override fun addGroupMember(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteGroupMember(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMemberList(data: MutableList<Friend>) {

        list = data
        updateAdapterData()

    }

    fun updateAdapterData() {
        if (isShow) {
            showList = list
            adapter.setData(list)
        } else {
            if (list!!.size > 20) {
                tv_group_member_size.visibility = View.VISIBLE

                showList = list!!.subList(0, 19)
            } else {
                showList = list
            }

        }
        defaultItem()
    }

    fun defaultItem() {

        var addMember = Friend()
        addMember.remark = "添加群成员"
        showList!!.add(addMember)
        if (isCreated) {
            var deleteMember = Friend()
            deleteMember.remark = "踢人出群"
            showList!!.add(deleteMember)
        }
        adapter.setData(showList)//更新列表
    }

    override fun deleteGroup(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissGroup(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        Toast.makeText(this@GroupDetailActivity, error, Toast.LENGTH_LONG).show()
    }

}
