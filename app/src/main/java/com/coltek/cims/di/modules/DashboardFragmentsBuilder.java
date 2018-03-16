package com.coltek.cims.di.modules;

import com.coltek.cims.ui.account.AccountFragment;
import com.coltek.cims.ui.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by BraDev ${LOCALE} on 3/14/2018.
 */

@Module
public abstract class DashboardFragmentsBuilder {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract AccountFragment contributeUserFragment();
//
//    @ContributesAndroidInjector
//    abstract SearchFragment contributeSearchFragment();
}
