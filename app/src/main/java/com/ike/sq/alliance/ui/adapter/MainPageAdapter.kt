package com.ike.sq.alliance.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 *
 * Created by T-BayMax on 2017/9/8.
 */

class MainPageAdapter(fm: FragmentManager, private val fragments: List<Fragment>?) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return fragments?.size ?: 0
    }

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getItemPosition(`object`: Any?): Int {
        return super.getItemPosition(`object`)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}
