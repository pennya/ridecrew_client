package com.ridecrew.ridecrew;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ridecrew.ridecrew.ui.BaseToolbarActivity;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;
import util.SharedUtils;

public class MainActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSettings();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleToolBar() {
        return R.string.app_name;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void layoutInit() {
        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("일정"));
        tabLayout.addTab(tabLayout.newTab().setText("공지"));
        tabLayout.addTab(tabLayout.newTab().setText("갤러리"));
        tabLayout.addTab(tabLayout.newTab().setText("마이페이지"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(this);
    }

    private void setDefaultSettings() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if( SharedUtils.getBooleanValue(this, DefineValue.IS_LOGIN) && MemberSingleton.getInstance().getMember() == null) {
            MemberSingleton.getInstance().setMember(new Member().setId(SharedUtils.getLongValue(this, DefineValue.LOGIN_ID_PK)));
        }
    }
}
