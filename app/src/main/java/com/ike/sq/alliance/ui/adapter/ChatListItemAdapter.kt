package com.ike.sq.alliance.ui.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.ike.sq.alliance.bean.Msg
import com.ike.sq.alliance.network.NetworkUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.possible_understand_item.view.*
import kotlinx.android.synthetic.main.view_item_friend.view.*

/**
 * 消息item
 * Created by T-BayMax on 2017/9/8.
 */
class ChatListItemAdapter(private var context: Context) : BaseAdapter() {
    private var msgList: MutableList<Msg>? = ArrayList()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var v=convertView
        if (convertView==null) {
            v = LayoutInflater.from(context).inflate(R.layout.view_item_friend, null)
            holder = ViewHolder(v)
            v.tag = holder
        } else {
            v=convertView
            holder = v?.tag as ViewHolder
        }
        var msg = msgList!![position]
        Picasso.with(context).load(NetworkUrl.BASE_ORIGINAL + msg.senderHeadPath).into(holder.friend_ico)
        holder.tv_name.text = msg.senderName
        return v!!
    }

    override fun getItem(position: Int): Any {
        return msgList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return msgList!!.size
    }

    class ViewHolder {
        var friend_ico: ImageView
        var tv_name: TextView
       constructor(view: View){
           friend_ico=view.friend_ico
           tv_name=view.tv_name
       }
    }
    fun setData(data: MutableList<Msg>){
        msgList=data
        this.notifyDataSetChanged()
    }
}