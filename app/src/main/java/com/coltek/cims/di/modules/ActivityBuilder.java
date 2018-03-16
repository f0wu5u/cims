package com.coltek.cims.di.modules;

import com.coltek.cims.ui.dashboard.DashboardActivity;
import com.coltek.cims.ui.login.LoginActivity;
import com.coltek.cims.ui.mentor_registration.MentorRegistration;
import com.coltek.cims.ui.resetpassword.ResetPasswordActivity;
import com.coltek.cims.ui.school_registration.SchoolRegistration;
import com.coltek.cims.ui.splashscreen.Splashscreen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract Splashscreen splashscreen();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector
    abstract ResetPasswordActivity resetPasswordActivity();

    @ContributesAndroidInjector(modules = DashboardFragmentsBuilder.class)
    abstract DashboardActivity dashboardActivity();

    @ContributesAndroidInjector
    abstract MentorRegistration mentorRegistration();

    @ContributesAndroidInjector
    abstract SchoolRegistration schoolRegistration();

}