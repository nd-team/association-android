package com.ike.sq.alliance.ui.activity

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.chat.activity.RecyclerViewChatActivity
import com.ike.sq.alliance.dao.ChatDbManager
import com.ike.sq.alliance.dao.GroupDbManager
import com.ike.sq.alliance.mvp.contract.GroupListContract
import com.ike.sq.alliance.mvp.presenter.GroupListPresenter
import com.ike.sq.alliance.ui.adapter.GroupListAdapter
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.ui.widget.LoadDialog
import kotlinx.android.synthetic.main.activity_group_list.*
import kotlinx.android.synthetic.main.layout_title_top.*


/**
 * 群列表
 */
class GroupListActivity : BaseActivity(), GroupListContract.IGroupListView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    //    private BaseRecyclerAdapter<Groups> adapter;
    private var adapter: GroupListAdapter? = null
    private var list: MutableList<GroupBean> = ArrayList<GroupBean>(0)
    private val groupName: String? = null
    private val groupId: String? = null
    private val groupPortraitUri: String? = null

    internal var groupDbManager: GroupDbManager?=null
    private var token: String? = null

    private var presenter: GroupListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_list)
        token = App.token
        groupDbManager = GroupDbManager()
        presenter = GroupListPresenter(this, this)
        tv_title.text = "我的群组"
        tv_title_right.visibility = View.VISIBLE
        iv_title_right.visibility=View.VISIBLE
        iv_title_right.setImageResource(R.mipmap.add_more)
        iv_title_back.setOnClickListener( this )
        iv_title_right.setOnClickListener(this)
        swipeRefresh.setOnRefreshListener(this)
        initAdapter()
        initGroups()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initData() {
        list = groupDbManager!!.loadAll()
        if (list.size > 0) {
            adapter!!.setList(list)
            LoadDialog.dismiss(this)
        } else {
            rv_group_list.visibility = View.GONE
            tv_no_group.visibility = View.VISIBLE
            tv_no_group.text = "你暂时未加入任何一个群组"
            LoadDialog.dismiss(this)
        }
    }

    private fun initAdapter() {
        adapter = GroupListAdapter(this)
        rv_group_list!!.adapter = adapter
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_group_list!!.layoutManager = lm
        rv_group_list!!.setHasFixedSize(true)
        // rvGroupList.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        initListItemClick()
    }

    private fun initListItemClick() {
        adapter!!.setOnItemClickListener(object : GroupListAdapter.OnRecyclerViewItemClickListener {
            override fun onItemClick(view: View, groups: GroupBean) {
                val intent = Intent(this@GroupListActivity, RecyclerViewChatActivity::class.java)
                intent.putExtra("friendId", groups.id)
                intent.putExtra("nickname", groups.name)
                intent.putExtra("headPath",groups.headPath)
                intent.putExtra("isGroup",true)
                intent.putExtra("groupBean",groups)
                startActivity(intent)
            }
        })
        iv_title_back.setOnClickListener(View.OnClickListener { this@GroupListActivity.finish() })
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_title_back -> this@GroupListActivity.finish()
            R.id.iv_title_right -> {
                val intent = Intent(this, CreateGroupActivity::class.java)
                intent.putExtra("createGroup", true)
                startActivity(intent)
            }
        }
    }

    override fun onRefresh() {
        initGroups()
    }

    /**
     * 获取群组列表
     */
    private fun initGroups() {
        tv_no_group.visibility=View.GONE
        presenter!!.getGroupList(App.token)
    }

    override fun getGroupList(data: MutableList<GroupBean>) {

        swipeRefresh.isRefreshing = false
        groupDbManager!!.deleteAll()
        list = data
        if (groupDbManager!!.insertList(list))
            initData()
        else
            tv_no_group.visibility=View.VISIBLE
    }

    override fun showError(error: String) {

        swipeRefresh.isRefreshing = false
    }
}
