package com.coltek.cims.di.modules;

import com.coltek.cims.ui.registration.MentorRegisterFragment;
import com.coltek.cims.ui.registration.SchoolRegisterFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by BraDev ${LOCALE} on 1/14/2018.
 */

@Module
public abstract class RegistrationFragmentBuilder {

    @ContributesAndroidInjector
    abstract SchoolRegisterFragment schoolRegisterFragment();

    @ContributesAndroidInjector
    abstract MentorRegisterFragment mentorRegisterFragment();
}
