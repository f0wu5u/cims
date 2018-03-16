package com.coltek.cims.ui.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by BraDev ${LOCALE} on 3/14/2018.
 */

class DashboardPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragments = new ArrayList();

    DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    DashboardPagerAdapter addFragment(Fragment fr) {
        fragments.add(fr);
        return this;
    }
}
