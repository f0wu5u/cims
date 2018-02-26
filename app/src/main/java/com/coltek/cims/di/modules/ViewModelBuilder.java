package com.coltek.cims.di.modules;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.coltek.cims.di.ViewModelKey;
import com.coltek.cims.factory.ViewModelFactory;
import com.coltek.cims.ui.login.LoginViewModel;
import com.coltek.cims.ui.profile.ProfileViewModel;
import com.coltek.cims.ui.registration.MentorRegistrationViewModel;
import com.coltek.cims.ui.resetpassword.ResetViewModel;
import com.coltek.cims.ui.splashscreen.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    public abstract ViewModel SplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel LoginviewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ResetViewModel.class)
    public abstract ViewModel ResetViewModel(ResetViewModel resetViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel ProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MentorRegistrationViewModel.class)
    public abstract ViewModel MentorRegistrationViewModel(MentorRegistrationViewModel mentorRegistrationViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(MentorRegistrationViewModel.class)
//    public abstract ViewModel MentorRegistrationViewModel(MentorRegistrationViewModel mentorRegistrationViewModel);

    @Binds
    public abstract ViewModelProvider.Factory factory(ViewModelFactory factory);

}