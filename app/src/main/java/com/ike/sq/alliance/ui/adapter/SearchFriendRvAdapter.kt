package com.ike.sq.alliance.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ike.sq.alliance.R
import com.ike.sq.alliance.bean.UserBean
import com.ike.sq.alliance.chat.widget.AudioRecordButton
import com.ike.sq.alliance.network.NetworkUrl
import com.ike.sq.alliance.ui.widget.image.CircleImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.possible_understand_item.view.*

import java.lang.reflect.Type
import java.util.ArrayList


/**
 * 查找好友item
 * Created by T-BayMax on 2017/9/9.
 */

class SearchFriendRvAdapter(private var mContext: Context) : RecyclerView.Adapter<SearchFriendRvAdapter.MyViewHolder>() {

    private var mDatas: MutableList<UserBean> = ArrayList()
    private val inflater: LayoutInflater
    private var keyword: String? = null

    interface OnItemClickLitener {
        fun onItemClick(view: View, position: Int)
    }

    private var mOnItemClickLitener: OnItemClickLitener? = null

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }

    /**设置当前查找输入的关键字
     * @param keyword
     */
    fun setKeyword(keyword: String) {
        this.keyword = keyword
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }

    fun setmDatas(data: MutableList<UserBean>) {

        mDatas = data
        notifyDataSetChanged()
    }

    fun clearData() {
        mDatas.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        with(holder?.itemView!!) {
            if (mOnItemClickLitener != null) {
                holder.itemView?.setOnClickListener {
                    val pos = holder.layoutPosition
                    mOnItemClickLitener!!.onItemClick(holder.itemView, pos)
                }
            }
            val user = mDatas[position]
            //if(userOrGroupBean..equals("0")){
            //个人
            iv_possible_understand_item_sex!!.visibility = View.VISIBLE
            tv_possible_understand_item_sameNumber!!.visibility = View.VISIBLE
            Picasso.with(mContext).load(NetworkUrl.BASE_ORIGINAL + user.headPath).into(iv_possible_understand_item_header)
            iv_possible_understand_item_sex!!.setImageResource(if (user.sexType == "1") R.mipmap.mine_man else R.mipmap.mine_women)
            tv_possible_understand_item_name!!.text = getKeywordColor(keyword, user.nickname, mContext)
            tv_possible_understand_item_number!!.text = user.phone
            tv_possible_understand_item_sameNumber.setText("0位共同好友");
            btn_possible_understand_join!!.text = "+ 好友"
            // if(userOrGroupBean.getRelationship()==null){
            tv_possible_understand_item_relation!!.visibility = View.GONE
            /* }else{
                    holder.tv_possible_understand_item_relation.setVisibility(View.VISIBLE);
                    holder.tv_possible_understand_item_relation.setText(getRelationship(user.getRelationship()));
                }*/
            /* }else{
                //群组
                Picasso.with(mContext).load(HttpUtils.IMAGE_RUL+userOrGroupBean.getGroupPortraitUrl()).into(holder.ivPossibleUnderstandItemHeader);
                holder.tvPossibleUnderstandItemName.setText(getKeywordColor(keyword,user.getGroupName(),mContext));
                holder.ivPossibleUnderstandItemSex.setVisibility(View.GONE);
                holder.tvPossibleUnderstandItemRelation.setVisibility(View.GONE);
                holder.tvPossibleUnderstandItemSameNumber.setVisibility(View.GONE);
                holder.tvPossibleUnderstandItemNumber.setText("群人数:"+user.getGroupUserNumber()+"人");
                holder.btnPossibleUnderstandJoin.setText("申请加入");
            }*/
           /* btn_possible_understand_join!!.setOnClickListener {
                val editText = EditText(mContext)
                AlertDialog.Builder(mContext)
                        .setTitle("验证信息")
                        .setView(editText)
                        .setPositiveButton("确定") { dialogInterface, i ->
                            val provingMessage = editText.text.toString()
                            //LoadDialog.show(mContext);
                            // if(userOrGroupBean.getStatus().equals("0")){
                            // sendFriendRequest(userOrGroupBean.getStatus(),useId,nickName,userOrGroupBean.getUserId(),provingMessage);
                            *//* }else{
                                                    sendGroupRequest(userOrGroupBean.getStatus(),useId,nickName,userOrGroupBean.getGroupId(),provingMessage);
                                                }*//*
                        }
                        .setNegativeButton("取消") { dialogInterface, i -> }
                        .show()
            }*/
            rl_item.setOnClickListener(View.OnClickListener { listener.onItemClick(user) })
            btn_possible_understand_join.setOnClickListener(View.OnClickListener { listener.sendFriendRequest(user.id) })
        }

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.possible_understand_item, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
    lateinit var listener:OnItemClickListener;
     fun setOnItemClickListener(listener:OnItemClickListener ){
        this.listener=listener
    }

     interface OnItemClickListener {
         fun sendFriendRequest(friendId: String);
         fun onItemClick(userBean: UserBean);
        // open fun sendGroupRequest(friendId: String) ;
    }

    /**
     * 关键字变色
     */
    fun getKeywordColor(keyword: String?, strtext: String, context: Context): SpannableStringBuilder {
        var strtext = strtext
        val docInfo = strtext
        var keywordIndex = strtext.indexOf(keyword!!)
        val style = SpannableStringBuilder(docInfo)
        while (keywordIndex != -1) {
            /**
             * 关键字颜色改变
             */
            style.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorAccent)), keywordIndex, keywordIndex + keyword.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val tempkeywordTempIndex = keywordIndex + keyword.length
            strtext = docInfo.substring(tempkeywordTempIndex, docInfo.length)
            keywordIndex = strtext.indexOf(keyword)
            if (keywordIndex != -1) {
                keywordIndex = keywordIndex + tempkeywordTempIndex
            }
        }
        return style
    }

    private fun getRelationship(relationship: String): String {
        var relation = ""
        when (relationship) {
            "1" -> relation = "亲人"
            "2" -> relation = "同事"
            "3" -> relation = "校友"
            "4" -> relation = "同乡"
        }
        return relation
    }
}
