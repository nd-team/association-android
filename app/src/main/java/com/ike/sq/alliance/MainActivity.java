package com.ike.sq.alliance;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ike.sq.alliance.ui.adapter.MainPageAdapter;
import com.ike.sq.alliance.ui.base.BaseActivity;
import com.ike.sq.alliance.ui.fragment.ChatListFragment;
import com.ike.sq.alliance.ui.fragment.FriendFragment;
import com.ike.sq.alliance.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    TabLayout tabLayout;
    ViewPager main_vp;

    private List<Fragment> fragments;
    private MainPageAdapter adapter;
    private Integer[] images = {R.drawable.main_home_bg, R.drawable.main_chat_bg, R.drawable.main_interest_bg, R.drawable.main_contact_bg, R.drawable.main_mine_bg};
    private String[] names = {"首页", "聊天", "兴趣联盟", "通讯录", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
    }

    private void initView() {
        main_vp= (ViewPager) findViewById(R.id.main_vp);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FriendFragment());
        fragments.add(new ChatListFragment());
        fragments.add(new FriendFragment());
        fragments.add(new FriendFragment());
        fragments.add(new MineFragment());
        adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        main_vp.setAdapter(adapter);
        main_vp.addOnPageChangeListener(this);
        main_vp.setCurrentItem(0);
        tabLayout.setupWithViewPager(main_vp, true);


    }
    private void initTab() {
        int tabCount = tabLayout.getTabCount();//获取TabLayout的个数
        for (int i=0; i<tabCount; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tab_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
            imageView.setImageDrawable(getResources().getDrawable(images[i]));
            TextView textView = (TextView) view.findViewById(R.id.tv_msg);
            textView.setText(names[i]);
            TabLayout.Tab tab = tabLayout.getTabAt(i);////获取TabLayout的子元素Tab
            tab.setCustomView(view);//设置TabLayout的子元素Tab的布局View
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
