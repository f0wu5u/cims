package com.coltek.cims.ui.splashscreen;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.ui.dashboard.DashboardActivity;
import com.coltek.cims.ui.login.LoginActivity;

import javax.inject.Inject;

public class Splashscreen extends AppCompatActivity implements Injectable{

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SplashViewModel splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        Intent intent = new Intent();
        Context context = getApplicationContext();

        splashViewModel.isLogged().observe(this, aBoolean->{
            if (aBoolean){
                intent.setClass(context, DashboardActivity.class);
            }else{
                intent.setClass(context, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        });
    }

}
