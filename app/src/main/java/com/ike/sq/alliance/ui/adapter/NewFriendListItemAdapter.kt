/*
 * Created by T-BayMax  17-9-16 下午6:29
 */

package com.ike.sq.alliance.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.Friend
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_new_friend_item.view.*

/**
 * Created by T-BayMax on 2017/9/16.
 */
class NewFriendListItemAdapter(private var content: Context) : RecyclerView.Adapter<NewFriendListItemAdapter.ViewHolder>() {
    private var list: MutableList<Friend>
    private val inflater: LayoutInflater


    init {
        list=ArrayList()
        inflater = LayoutInflater.from(content)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.view_new_friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

            var friend = list[position]
            if (mOnItemClickLitener != null) {
                holder!!.itemView?.setOnClickListener {
                    mOnItemClickLitener!!.onItemClick(holder!!.itemView, friend)
                }
                if (friend.applyType == "APPLY") {
                    holder!!.itemView.tv_agree.setOnClickListener {
                        mOnItemClickLitener!!.onItemAgree(holder.itemView, friend)
                    }
                } else if (friend.applyType == "PASS") {
                    holder!!.itemView.tv_agree.text="已同意"
                    holder!!.itemView.tv_agree.setTextColor(R.color.color_66)
                    holder!!.itemView.tv_agree.setBackgroundColor(android.R.color.white)
                }else{
                    holder!!.itemView.tv_agree.text="已拒绝"
                    holder!!.itemView.tv_agree.setTextColor(R.color.color_66)
                    holder!!.itemView.tv_agree.setBackgroundColor(android.R.color.white)
                }
            }
        holder!!.itemView.tv_name.text = friend.nickname
        holder!!.itemView.tv_remark.text = friend.remark
            if (!friend.headPath.isNullOrEmpty())
                Picasso.with(content).load(friend.headPath).into(holder!!.itemView.friend_ico)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    open fun setData(list: MutableList<Friend>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    open interface OnItemClickLitener {
        fun onItemClick(view: View, friend: Friend)
        fun onItemAgree(view: View, friend: Friend)
    }

    private var mOnItemClickLitener: OnItemClickLitener? = null

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener

    }
}