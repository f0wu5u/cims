package com.coltek.cims.di.modules;

import com.coltek.cims.ui.login.LoginActivity;
import com.coltek.cims.ui.profile.ProfileActivity;
import com.coltek.cims.ui.registration.RegistrationActivity;
import com.coltek.cims.ui.resetpassword.ResetPasswordActivity;
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

    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ContributesAndroidInjector(modules = RegistrationFragmentBuilder.class)
    abstract RegistrationActivity registrationActivity();

}
