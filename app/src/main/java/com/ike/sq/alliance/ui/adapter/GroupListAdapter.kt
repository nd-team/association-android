package com.ike.sq.alliance.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.GroupBean
import com.ike.sq.alliance.ui.widget.image.SelectableRoundedImageView
import com.squareup.picasso.Picasso

import java.util.ArrayList

/**
 * 群列表item
 * Created by Min on 2017/9/14.
 */

class GroupListAdapter(private val context: Context) : RecyclerView.Adapter<GroupListAdapter.MyViewHolder>(), View.OnClickListener {
    private val list = ArrayList<GroupBean>()
    private var mOnItemClickListener: OnRecyclerViewItemClickListener? = null

    interface OnRecyclerViewItemClickListener {
        fun onItemClick(view: View, groups: GroupBean)
    }

    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener) {
        this.mOnItemClickListener = listener
    }

    fun setList(list: List<GroupBean>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_friend, parent, false)
        val holder = MyViewHolder(view)
        view.setOnClickListener(this)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val group = list[position]
        val url = group.headPath
        holder.tv_name.text = group.name
        Picasso.with(context).load(url).into(holder.friend_ico)
        holder.itemView.tag = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_name: TextView
        var friend_ico: SelectableRoundedImageView

        init {
            tv_name = view.findViewById(R.id.tv_name) as TextView
            friend_ico = view.findViewById(R.id.friend_ico) as SelectableRoundedImageView
        }
    }

    override fun onClick(view: View) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener!!.onItemClick(view, view.tag as GroupBean)
        }
    }
}
