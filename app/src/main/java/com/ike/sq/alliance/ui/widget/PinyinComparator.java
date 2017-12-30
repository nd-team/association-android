package com.ike.sq.alliance.ui.widget;

import com.ike.sq.alliance.bean.Friend;

import java.util.Comparator;

/**
 * Created by Min on 2016/11/25.
 */

public class PinyinComparator implements Comparator<Friend> {


    public int compare(Friend o1, Friend o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}