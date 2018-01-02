package com.ridecrew.ridecrew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ridecrew.ridecrew.ui.MyPageFragment;
import com.ridecrew.ridecrew.ui.ScheduleFragment;
import com.ridecrew.ridecrew.ui.SettingsFragent;


public class TabPagerAdapter extends FragmentStatePagerAdapter{

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //ScheduleFragment tabFragment1 = new ScheduleFragment();
                com.ridecrew.ridecrew.ui.ScheduleFragment tabFragment1 =
                        new ScheduleFragment();
                return tabFragment1;
            case 1:
                NoticeFragment tabFragment2 = new NoticeFragment();
                return tabFragment2;
            case 2:
                GalleryFragment tabFragment3 = new GalleryFragment();
                return tabFragment3;
            case 3:
                //MyPageFragment tabFragment4 = new MyPageFragment();
                SettingsFragent tabFragment4 = new SettingsFragent();
                return tabFragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
