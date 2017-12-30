package com.ike.sq.alliance.ui.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*

import com.google.gson.Gson
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.mvp.contract.SearchFriendContract
import com.ike.sq.alliance.mvp.presenter.SearchFriendPresenter
import com.ike.sq.alliance.ui.adapter.SearchFriendRvAdapter
import com.ike.sq.alliance.ui.adapter.SearchFriendRvAdapter.OnItemClickListener
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.ui.widget.LoadDialog
import com.ike.sq.alliance.ui.widget.RecycleViewDivider
import com.ike.sq.alliance.ui.widget.SendDialog
import kotlinx.android.synthetic.main.activity_search_friend.*
import kotlinx.android.synthetic.main.dialog_send.*
import kotlinx.android.synthetic.main.dialog_send.view.*
import kotlinx.android.synthetic.main.layout_title_top.*

import java.lang.reflect.Type

import okhttp3.Call
import java.util.HashMap
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility

/**
 * 搜索好友----添加好友
 */
class SearchFriendActivity : BaseActivity(), TextWatcher, View.OnClickListener, SearchFriendContract.SearchFriendView {

    private var msg: String? = null
    private var status: String? = null
    private var userId: String? = null
    private var nickName: String? = null
    private var user: UserBean? = null
    private var searchFriendRvAdapter: SearchFriendRvAdapter? = null
    //private SearchPossiblePeopleRvAdapter searchPossibleAdapter;
    private var userList: MutableList<UserBean>? = null

    lateinit var presenter: SearchFriendPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_friend)
        user = App.userBean
        userId = user?.id
        nickName = user?.nickname
        presenter = SearchFriendPresenter(this, this)
        initView()

    }

    private fun initView() {
        //  searchPossibleAdapter=new SearchPossiblePeopleRvAdapter(this,userId,nickName);
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = OrientationHelper.HORIZONTAL
        /* rv_search_possible_people.setLayoutManager(layoutManager);
        rv_search_possible_people.setAdapter(searchPossibleAdapter);
        rv_search_possible_people.addItemDecoration(new RecycleViewDivider(this, OrientationHelper.VERTICAL,14,getResources().getColor(R.color.col_ECEEF0)));
*/
        searchFriendRvAdapter = SearchFriendRvAdapter(this)
        val layoutManager2 = LinearLayoutManager(this)
        layoutManager.orientation = OrientationHelper.VERTICAL
        rv_search_friends!!.layoutManager = layoutManager2
        rv_search_friends!!.adapter = searchFriendRvAdapter
        rv_search_friends!!.addItemDecoration(RecycleViewDivider(this, OrientationHelper.HORIZONTAL, 2, resources.getColor(R.color.col_ECEEF0)))
        et_friend!!.addTextChangedListener(this)
        iv_searchFriend_back.setOnClickListener(this)
        iv_searchFriend_scan_white.setOnClickListener(this)
        btn_seach_seacher.setOnClickListener(this)
        searchFriendRvAdapter?.setOnItemClickListener(
                object : SearchFriendRvAdapter.OnItemClickListener {
                    override fun sendFriendRequest(friendId: String) {
                        sendDialogView(friendId)
                    }

                    override fun onItemClick(userBean: UserBean) {
                        var intent = Intent(this@SearchFriendActivity, UserDetailActivity::class.java)
                        intent.putExtra("friendId", userBean.id)
                        intent.putExtra("nickname", userBean.nickname)
                        intent.putExtra("isFriend",false)
                        startActivity(intent)
                    }
                })

        iv_searchFriend_back.setOnClickListener(View.OnClickListener { this@SearchFriendActivity.finish() })
    }

    lateinit var dialog: Dialog
    fun sendDialogView(friendId: String) {
        var view = LayoutInflater.from(this).inflate(
                R.layout.dialog_send, null) as LinearLayout;
        dialog = SendDialog().initDialog(view, R.style.dialogstyle)
        dialog.show()

        view.iv_close.setOnClickListener(View.OnClickListener { dialog.dismiss() })
        view.btn_send.setOnClickListener(View.OnClickListener {
            var remark: String
            if (view.et_remark!!.text.toString().isNullOrEmpty())
                remark = view.et_remark.hint.toString()
            else
                remark = view.et_remark.text.toString()

            sendFriend(friendId, remark)
        })
    }

    /**
     * 好友申请
     */
    private fun sendFriend(friendId: String, remark: String) {
        LoadDialog.show(this)
        val fieldMap = HashMap<String, String>(0)
        fieldMap.put("friendId", friendId)
        fieldMap.put("remark", remark)
        presenter.applyFriend(App.token,fieldMap)
    }

    /**
     * 查找好友
     */
    private fun searchFriends() {
        LoadDialog.show(this)
        presenter.getSearchFriendList(msg!!)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_searchFriend_back -> finish()
            R.id.iv_searchFriend_scan_white -> {
            }
            R.id.btn_seach_seacher -> {
                msg = et_friend!!.text.toString().trim()
                if (!msg.isNullOrEmpty()) {
                    searchFriendRvAdapter!!.setKeyword(msg!!)

                    searchFriends()
                    ll_possible_understand!!.visibility = View.GONE
                    rv_search_friends!!.visibility = View.VISIBLE
                }
            }
            R.id.tv_search_more -> {
            }
        }// startActivityForResult(new Intent(mContext, CaptureActivity.class), 0);
        //  startActivity(new Intent(this, PossibleUnderstandActivity.class));
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val bundle = data.extras
            if (bundle != null) {
                val result = bundle.getString("result")
                msg = result!!.substring(6)
                et_friend!!.setText(msg)
                searchFriends()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.length == 0) {
            ll_possible_understand!!.visibility = View.VISIBLE
            rv_search_friends!!.visibility = View.GONE
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

    /**
     * 查找结果
     */
    override fun getSearchFriendList(data: MutableList<UserBean>) {
        LoadDialog.dismiss(this)
        if (null != data) {
            userList = data
            searchFriendRvAdapter?.setmDatas(userList!!)
        }
    }

    /**
     * 申请结果
     */
    override fun applyFriend(msg: String) {
        dialog.dismiss()
        LoadDialog.dismiss(this)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 错误结果
     */
    override fun showError(errorString: String) {
        if (null != dialog) {
            dialog.dismiss()
        }
        LoadDialog.dismiss(this)
        Toast.makeText(this, errorString, Toast.LENGTH_LONG).show()
    }

}
