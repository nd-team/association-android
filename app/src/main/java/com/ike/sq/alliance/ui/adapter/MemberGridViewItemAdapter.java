package com.ike.sq.alliance.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ike.sq.alliance.R;
import com.ike.sq.alliance.bean.Friend;
import com.ike.sq.alliance.bean.GroupBean;
import com.ike.sq.alliance.bean.GroupMember;
import com.ike.sq.alliance.network.HttpsUtils;
import com.ike.sq.alliance.network.NetworkUrl;
import com.ike.sq.alliance.ui.widget.image.SelectableRoundedImageView;
import com.ike.sq.alliance.utils.file.image.MyBitmapUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * 群成员item
 * Created by T-BayMax on 2017/9/11.
 */

public class MemberGridViewItemAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> list = new ArrayList<Friend>(0);
    private LayoutInflater inflater;
    private boolean isCreated;

    public MemberGridViewItemAdapter(Context context, boolean isCreated) {
        this.context = context;
        this.isCreated = isCreated;
        this.inflater = inflater.from(context);

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setData(List<Friend> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_groups_list_item, null);
            holder = new ViewHolder();
            holder.sivGroupDetails = (SelectableRoundedImageView) convertView.findViewById(R.id.siv_group_details_head);
            holder.tvGroupDetailsName = (TextView) convertView.findViewById(R.id.tv_group_details_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Friend friend = list.get(position);
        if (position == getCount() - 1 && isCreated) {

            holder.sivGroupDetails.setImageResource(R.mipmap.icon_btn_deleteperson);
        } else if ((isCreated && position == getCount() - 2) || (!isCreated && position == getCount() - 1)) {

            holder.sivGroupDetails.setImageResource(R.mipmap.add_group_head);
        } else {
            if (null != friend.getHeadPath()) {
                Picasso.with(context).load(NetworkUrl.Companion.getBASE_ORIGINAL() + friend.getHeadPath()).into(holder.sivGroupDetails);
            } else {
                holder.sivGroupDetails.setImageResource(R.mipmap.default_portrait);
            }

        }
        String text = "";
        if (null != friend.getNickname()) {
            Rect bounds = new Rect();
            text = friend.getNickname();

            int viewWidth = holder.tvGroupDetailsName.getWidth();
            TextPaint paint = holder.tvGroupDetailsName.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.width();
            int textWidth = width / text.length();
            if (viewWidth > width - 4 * textWidth) {
                text = text.substring(0, viewWidth / textWidth - 4) + "..";
            } else {

            }
        }
        holder.tvGroupDetailsName.setText(text);
        return convertView;
    }

    static class ViewHolder {
        SelectableRoundedImageView sivGroupDetails;
        TextView tvGroupDetailsName;
    }
}
