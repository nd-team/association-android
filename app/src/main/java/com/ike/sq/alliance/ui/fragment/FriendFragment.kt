package com.ike.sq.alliance.ui.fragment

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.chat.activity.RecyclerViewChatActivity
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.presenter.FriendPresenter
import com.ike.sq.alliance.ui.activity.GroupListActivity
import com.ike.sq.alliance.ui.activity.NewFriendListActivity
import com.ike.sq.alliance.ui.activity.SearchFriendActivity
import com.ike.sq.alliance.ui.activity.UserDetailActivity
import com.ike.sq.alliance.ui.adapter.FriendListAdapter
import com.ike.sq.alliance.ui.widget.LetterBar
import com.ike.sq.alliance.ui.widget.PinyinComparator
import com.ike.sq.alliance.ui.widget.image.SelectableRoundedImageView
import com.ike.sq.alliance.utils.CharacterParser
import kotlinx.android.synthetic.main.fragment_conversation_list.view.*
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.fragment_friend.view.*
import kotlinx.android.synthetic.main.item_friend_list_header.view.*

import java.lang.reflect.Type
import java.util.ArrayList
import java.util.Collections

import okhttp3.Call

/**
 * Created by T-BayMax on 2017/9/10.
 */

class FriendFragment : Fragment(), FriendListContract.friendView, SwipeRefreshLayout.OnRefreshListener {

    lateinit var containerView: View

    lateinit var mHeadView: View

    //汉字转换成拼音的类

    private var characterParser: CharacterParser? = null
    private var pinyinComparator: PinyinComparator? = null
    private var mSourceFriendList: MutableList<Friend>? = null
    /**
     * 好友列表的 mFriendListAdapter
     */
    private var adapter: FriendListAdapter? = null


    private var presenter: FriendPresenter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        containerView = inflater!!.inflate(R.layout.fragment_friend, container, false)

        initView()
        initData()
        return containerView
    }

    private fun initView() {


        val inflater = LayoutInflater.from(context)
        mHeadView = inflater.inflate(R.layout.item_friend_list_header, null)

        containerView.lv_friends.addHeaderView(mHeadView)
        containerView.swipeRefresh.setOnRefreshListener(this)
        characterParser = CharacterParser.getInstance()

        pinyinComparator = PinyinComparator()

        containerView.lv_friends.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //这里要利用adapter.getItem(position)来获取当前position所对应的对象
            val friend = adapter!!.getItem(position-1) as Friend
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra("friendId", friend.id)
            intent.putExtra("nickname", friend.nickname)
            intent.putExtra("isFriend",true)
            startActivity(intent)
        }
        mSourceFriendList = ArrayList(0)

        // 根据a-z进行排序源数据
        Collections.sort(mSourceFriendList!!, pinyinComparator)
        containerView.sb.setSelectedListener(selectedListener)
        containerView.et_search.addTextChangedListener(textWatcher)

        adapter = FriendListAdapter(activity, mSourceFriendList)
        containerView.lv_friends.adapter = adapter
        containerView.iv_add_friends.setOnClickListener {
            val intent = Intent(activity, SearchFriendActivity::class.java)
            startActivity(intent)
        }
        mHeadView.rl_new_friend.setOnClickListener {
            var intent = Intent(this.context, NewFriendListActivity::class.java)
            startActivity(intent)
        }
        mHeadView.rl_group.setOnClickListener(View.OnClickListener {
            var intent = Intent(this.context, GroupListActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initData() {
        presenter = FriendPresenter(activity, this)
        presenter!!.getFriendList(App.token)
    }

    private val selectedListener = object : LetterBar.SelectedListener {
        override fun onSelected(letter: String) {
            tv_group_dialog.text = letter
            tv_group_dialog.visibility = View.VISIBLE
            //该字母首次出现的位置
            val position = adapter!!.getPositionForSection(letter[0].toInt())
            if (position != -1) {
                containerView.lv_friends.setSelection(position)

            }
        }

        override fun onCancel() {
            containerView.tv_group_dialog.visibility = View.GONE
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private fun filledData(date: List<Friend>): MutableList<Friend> {
        val mSortList = ArrayList<Friend>()

        for (i in date.indices) {
            val model = date[i]
            //汉字转换成拼音
            val pinyin = characterParser!!.getSelling(model.nickname)
            val sortString = pinyin.substring(0, 1).toUpperCase()
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]".toRegex())) {
                model.letters = sortString.toUpperCase()
            } else {
                model.letters = "#"
            }


            mSortList.add(model)
        }
        return mSortList

    }

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
            filterData(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {

        }

        override fun afterTextChanged(s: Editable) {}
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private fun filterData(filterStr: String) {
        var filterDateList: MutableList<Friend>? = ArrayList()

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSourceFriendList
        } else {
            filterDateList!!.clear()
            for (sortModel in mSourceFriendList!!) {
                val name = sortModel.friendId
                if (name!!.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1 || characterParser!!.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel)
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList!!, pinyinComparator)
        mSourceFriendList = filterDateList
        adapter!!.setFriendList(filterDateList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun getFriendList(data: MutableList<Friend>) {
        mSourceFriendList = filledData(data)
        Collections.sort(mSourceFriendList!!, pinyinComparator)
        adapter!!.setFriendList(mSourceFriendList!!)
        containerView.swipeRefresh.isRefreshing = false

    }

    override fun showError(errorString: String) {

        containerView.swipeRefresh.isRefreshing = false
    }

    override fun onRefresh() {
        initData()
    }
}
