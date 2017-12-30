package com.ike.sq.alliance.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.chat.activity.RecyclerViewChatActivity
import com.ike.sq.alliance.dao.ChatDbManager
import com.ike.sq.alliance.mvp.contract.ChatListFragmentContract
import com.ike.sq.alliance.ui.activity.GroupListActivity
import com.ike.sq.alliance.ui.adapter.ChatListItemAdapter
import kotlinx.android.synthetic.main.fragment_conversation_list.view.*
import kotlinx.android.synthetic.main.item_friend_list_header.view.*
import org.greenrobot.greendao.query.WhereCondition

/**
 * 聊天列表
 * Created by T-BayMax on 2017/9/8.
 */
class ChatListFragment : Fragment(), ChatListFragmentContract.ChatListFragmentView {

    lateinit var containerView: View
    lateinit var msgList: MutableList<Msg>
    lateinit var adapter: ChatListItemAdapter
    var mChatDbManager: ChatDbManager? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        containerView = inflater!!.inflate(R.layout.fragment_conversation_list, container, false)

        initView()
        initData()
        return containerView
    }

    fun initView() {
        adapter = ChatListItemAdapter(context)
        containerView.lv_chat.adapter = adapter
        containerView.lv_chat.setOnItemClickListener { _, _, position, _
            ->
            var msg = msgList[position]
            val intent = Intent(activity, RecyclerViewChatActivity::class.java)
            intent.putExtra("friendId", msg.receiver);
            intent.putExtra("nickname", msg.senderName);
            intent.putExtra("headPath", msg.senderHeadPath)
            startActivity(intent)
        }
        //containerView.srl_chat.setOnRefreshListener { initData() }

    }

    fun initData() {
        containerView.tv_show_no_chat.visibility=View.GONE
        if (mChatDbManager == null)
            mChatDbManager = ChatDbManager()
        var condition = WhereCondition.StringCondition("msgType = 'POINT' or msgType = 'GROUP' "
                + " GROUP BY friendId ")
        msgList = mChatDbManager!!.loadGroup(condition)
        if (msgList.size>0){
            adapter.setData(msgList)
           // containerView.srl_chat.isRefreshing = false
        }else{
            containerView.tv_show_no_chat.visibility=View.VISIBLE
        }

    }
}