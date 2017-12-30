package com.ike.sq.alliance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.ike.sq.alliance.App
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.mvp.contract.FriendListContract
import com.ike.sq.alliance.mvp.contract.SelectFriendView
import com.ike.sq.alliance.mvp.presenter.FriendPresenter
import com.ike.sq.alliance.mvp.presenter.SelectFriendPresenter
import com.ike.sq.alliance.ui.adapter.FriendListAdapter
import com.ike.sq.alliance.ui.adapter.SelectFriendListAdapter
import com.ike.sq.alliance.ui.base.BaseActivity
import com.ike.sq.alliance.ui.widget.LetterBar
import com.ike.sq.alliance.ui.widget.PinyinComparator
import com.ike.sq.alliance.utils.CharacterParser
import kotlinx.android.synthetic.main.activity_select_friend.*
import kotlinx.android.synthetic.main.layout_title_top.*
import java.util.*
import kotlin.collections.HashMap

/**
 * 选择好友
 * Created by T-BayMax on 2017/9/14.
 */
class SelectActivity : BaseActivity(), SelectFriendView {

    //汉字转换成拼音的类

    private var characterParser: CharacterParser? = null
    private var pinyinComparator: PinyinComparator? = null
    private var mSourceFriendList: MutableList<Friend>? = null
    private var memberList: MutableList<Friend>? = null
    /**
     * 好友列表的 mFriendListAdapter
     */
    private var adapter: SelectFriendListAdapter? = null

    private var presenter: SelectFriendPresenter? = null

    private var isadd: Int = 0//0添加，1删除

    var token: String = ""
    var groupId: String = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_friend)
        initView()
        presenter = SelectFriendPresenter(this@SelectActivity, this)
        if (isadd == 1) {
            setFriendAdapter(memberList!!);
        } else {
            initData()
        }
        initClick()
    }

    private fun initView() {
        token = App.token


        memberList = intent.getParcelableArrayListExtra<Friend>("memberList")
        isadd = intent.getIntExtra("isadd", 0);

        groupId = intent.getStringExtra("groupId")
        characterParser = CharacterParser.getInstance()

        pinyinComparator = PinyinComparator()

        mSourceFriendList = ArrayList(0)

        // 根据a-z进行排序源数据
        Collections.sort(mSourceFriendList!!, pinyinComparator)
        sb.setSelectedListener(selectedListener)
        et_search.addTextChangedListener(textWatcher)

        adapter = SelectFriendListAdapter(this@SelectActivity, mSourceFriendList)
        lv_friends.adapter = adapter
        tv_title_right.visibility = View.VISIBLE
        tv_title_right.text = "确定"
        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { initData() })
    }

    private fun initData() {
        presenter!!.getFriendList(token)
    }

    fun initClick() {
        tv_title_right.setOnClickListener(View.OnClickListener {
            var params = HashMap<String, String>(0)
            var userIds = updateSelectedSizeView(adapter!!.checkBoxList)
            if (userIds!!.size>0 ) {
                var u=userIds.toString()
                params.put("userId",userIds.toString())
                when (isadd) {
                    0 -> presenter!!.addGroupUser(token, groupId, params)
                    1 -> presenter!!.deleteMember(token, groupId, params)
                }
            }else{
                Toast.makeText(this@SelectActivity,"请选择用户",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateSelectedSizeView(mCBFlag: Map<Int, Boolean>?): MutableList<String>? {
        if (mCBFlag != null) {
            /*     var size = 0
                 for (i in 0..mCBFlag.size - 1) {
                     if (mCBFlag[i]) {
                         size++
                     }
                 }
                 if (size == 0) {
                     tvEnter.setText("确定")
                 } else {
                     tvEnter.setText("确定($size)")*/
            val selectedList = ArrayList<String>()
            for (i in mSourceFriendList!!.indices) {
                if (mCBFlag[i]!!) {
                    selectedList.add(mSourceFriendList!![i].id!!)
                }
                // }
            }
            return selectedList
        }
        return null
    }

    private val selectedListener = object : LetterBar.SelectedListener {
        override fun onSelected(letter: String) {
            tv_group_dialog.text = letter
            tv_group_dialog.visibility = View.VISIBLE
            //该字母首次出现的位置
            val position = adapter!!.getPositionForSection(letter[0].toInt())
            if (position != -1) {
                lv_friends.setSelection(position)

            }
        }

        override fun onCancel() {
            tv_group_dialog.visibility = View.GONE
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private fun filledData(date: MutableList<Friend>): MutableList<Friend> {
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

    override fun getFriendList(data: MutableList<Friend>) {
        for (i in memberList!!.indices) {
            data.remove(memberList!![i])
        }
        setFriendAdapter(data)
    }

    fun setFriendAdapter(data: MutableList<Friend>) {
        mSourceFriendList = filledData(data)
        Collections.sort(mSourceFriendList!!, pinyinComparator)
        adapter!!.setFriendList(mSourceFriendList!!)
        swipeRefresh.isRefreshing = false
    }

    override fun addGroupUser(data: String) {
        Toast.makeText(this@SelectActivity, data, Toast.LENGTH_LONG).show()
        this@SelectActivity.finish()
    }

    override fun deleteMember(data: String) {
        Toast.makeText(this@SelectActivity, data, Toast.LENGTH_LONG).show()
        this@SelectActivity.finish()
    }

    override fun showError(errorString: String) {
        Toast.makeText(this@SelectActivity, errorString, Toast.LENGTH_LONG).show()
        swipeRefresh.isRefreshing = false
    }


}