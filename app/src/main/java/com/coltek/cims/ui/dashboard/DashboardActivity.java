package com.coltek.cims.ui.dashboard;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.factory.ViewModelFactory;
import com.coltek.cims.ui.account.AccountFragment;
import com.coltek.cims.ui.mentor_registration.MentorRegistration;
import com.coltek.cims.ui.notice.NoticeFragment;
import com.coltek.cims.ui.profile.ProfileFragment;
import com.coltek.cims.ui.school_registration.SchoolRegistration;
import com.coltek.cims.ui.settings.SettingsActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.support.v4.view.GravityCompat.START;
import static com.coltek.cims.ui.profile.EditProfileFragment.newInstance;

public class DashboardActivity extends AppCompatActivity implements HasSupportFragmentInjector, FragCommunicable {

    @Inject
    ViewModelFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelProviders.of(DashboardActivity.this, factory).get(DashboardViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ViewPager pager = findViewById(R.id.dashboard_pager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        pager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager()).addFragment(new NoticeFragment()).addFragment(new ProfileFragment()).addFragment(new AccountFragment()));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.action_main);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.action_profile);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.action_account);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            int pagerPosition = pager.getCurrentItem();
            switch (id) {
                case R.id.action_profile:
                    if (pagerPosition != 1) {
                        pager.setCurrentItem(1);
                    }
                    return true;
                case R.id.action_main:
                    if (pagerPosition != 0) {
                        pager.setCurrentItem(0);
                    }
                    return true;
                case R.id.action_account:
                    if (pagerPosition != 2) {
                        pager.setCurrentItem(2);
                    }
                    return true;
                default:
                    return false;
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {

            Intent intent = new Intent();

            int id = item.getItemId();
            switch (id) {
                case R.id.nav_school:
                    intent.setClass(getApplicationContext(), SchoolRegistration.class);
                    break;
                case R.id.nav_mentor:
                    intent.setClass(getApplicationContext(), MentorRegistration.class);
                    break;
                case R.id.nav_info:
                    return false;
                case R.id.nav_settings:
                    intent.setClass(getApplicationContext(), SettingsActivity.class);
                    break;
                case R.id.nav_report:
                    return false;
            }
            drawer.closeDrawer(START, false);
            startActivity(intent);
            return true;
        });

        InjectView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(START)) {
            drawer.closeDrawer(START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void InjectView() {

    }


    @Override
    public void changeEmail(String email) {
        viewModel.updateContact(email);
    }

    @Override
    public void makeText(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditProfileFragment(String email) {
        newInstance(email).show(getSupportFragmentManager(), "EDIT_PROFILE");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
