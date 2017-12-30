package com.ike.sq.alliance.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.SectionIndexer
import android.widget.TextView
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.ui.widget.image.SelectableRoundedImageView
import com.ike.sq.alliance.utils.file.image.MyBitmapUtils
import java.util.ArrayList

/**
 * 选择好友item
 * Created by T-BayMax on 2017/9/11.
 */

class SelectFriendListAdapter(private val context: Context, private var friendList: List<Friend>?) : BaseAdapter(), SectionIndexer {
    private val myBitmapUtils: MyBitmapUtils

    var checkBoxList = HashMap<Int, Boolean>(0)

    init {
        myBitmapUtils = MyBitmapUtils()

    }

    override fun getCount(): Int {
        return if (friendList == null) 0 else friendList!!.size
    }

    override fun getItem(position: Int): Any {
        return friendList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_item_add_friend, null)
            holder = ViewHolder()
            holder.friend_ico = convertView!!.findViewById(R.id.friend_ico) as SelectableRoundedImageView
            holder.tvName = convertView.findViewById(R.id.tv_name) as TextView
            holder.tvLetter = convertView.findViewById(R.id.tv_letter) as TextView
            holder.cb_friend = convertView.findViewById(R.id.cb_friend) as CheckBox
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val friend = friendList!![position]
        //根据position获取分类的首字母的char ascii值
        val section = getSectionForPosition(position)

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tvLetter!!.visibility = View.VISIBLE
            holder.tvLetter!!.text = friend.letters
        } else {
            holder.tvLetter!!.visibility = View.GONE
        }
        if (TextUtils.isEmpty(friend.headPath)) {
            /*String s = Generate.generateDefaultAvatar(friend.getRemark(), friend.getUserId());
            ImageLoader.getInstance().displayImage(s, holder.friend_ico, App.getOptions());*/
        } else {
            myBitmapUtils.disPlay(holder.friend_ico, friend.headPath)
        }
        holder.tvName!!.text = this.friendList!![position].nickname
        holder.cb_friend!!.isChecked = checkBoxList.get(position)!!
        holder.cb_friend!!.setOnClickListener(View.OnClickListener { v ->
            checkBoxList.put(position, (v as CheckBox).isChecked())
        })
        return convertView
    }

    private inner class ViewHolder {
        internal var friend_ico: SelectableRoundedImageView? = null
        internal var tvName: TextView? = null
        internal var tvLetter: TextView? = null
        internal var cb_friend: CheckBox? = null
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    override fun getSectionForPosition(position: Int): Int {
        return friendList!![position].letters!![0].toInt()
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    override fun getPositionForSection(section: Int): Int {
        for (i in 0..count - 1) {
            val sortStr = friendList!![i].letters
            val firstChar = sortStr!!.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }

        return -1
    }


    override fun getSections(): Array<Any>? {
        return null
    }

    fun setFriendList(friendList: List<Friend>) {
        this.friendList = friendList
        for (i in friendList!!.indices) {
            checkBoxList.put(i, false)
        }
        this.notifyDataSetChanged()
    }
open  interface CheckBoxClick
}