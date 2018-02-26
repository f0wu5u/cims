package com.coltek.cims.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.coltek.cims.R;
import com.coltek.cims.ui.profile.ProfileActivity;
import com.coltek.cims.ui.registration.RegistrationActivity;

import static android.support.v4.view.GravityCompat.START;
    public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InjectView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(START)){
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent navigator = new Intent();
        Context context = getApplicationContext();
        int id = item.getItemId();
        switch (id){
            case R.id.nav_school:
                navigator.setClass(context, RegistrationActivity.class);
                navigator.putExtra("open","school");
            break;
            case R.id.nav_mentor:
                navigator.setClass(context, RegistrationActivity.class);
                navigator.putExtra("open","mentor");
            break;
            case R.id.nav_msg:
                break;
            case R.id.nav_news:
            break;
            case R.id.nav_info:
            break;
            case R.id.nav_settings:
                break;
            case R.id.nav_report:
                break;
        }
        startActivity(navigator);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(START);
        return true;
    }

    protected void InjectView(){
        ImageButton profileButton = findViewById(R.id.profile_btn),
                settingsButton = findViewById(R.id.setting_btn),
                messagesButton = findViewById(R.id.message_btn),
                accountButton =  findViewById(R.id.account_btn);

        Intent navigator = new Intent();
        profileButton.setOnClickListener(v -> {
            final Intent intent = navigator.setClass(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {

        });
    }
}
