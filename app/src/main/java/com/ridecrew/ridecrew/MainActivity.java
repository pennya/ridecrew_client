package com.ridecrew.ridecrew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ridecrew.ridecrew.ui.BaseToolbarActivity;
import com.ridecrew.ridecrew.ui.LoginActivity;
import com.ridecrew.ridecrew.ui.SignUpActivity;

import Define.DefineValue;
import Entity.Member;
import Entity.MemberSingleton;
import util.SharedUtils;

public class MainActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInit();
        setDefaultSettings();
    }
    //옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu:
                if( SharedUtils.getBooleanValue(this, DefineValue.IS_LOGIN)) {
                    viewPager.setCurrentItem(3);
                    return true;
                }
                else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
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
            Member member = Member.builder()
                    .setEmail(SharedUtils.getStringValue(this, DefineValue.CURRENT_LOGIN_ID))
                    .setId(SharedUtils.getLongValue(this, DefineValue.LOGIN_ID_PK))
                    .setDeviceId(SharedUtils.getStringValue(this, DefineValue.DEVICE_ID))
                    .setNickName(SharedUtils.getStringValue(this, DefineValue.NICKNAME));
            MemberSingleton.getInstance().setMember(member);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "탭을 다시 한번 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
