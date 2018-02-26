package com.coltek.cims.ui.registration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.coltek.cims.R;
import com.coltek.cims.entity.Mentor;

import javax.inject.Inject;

public class RegistrationNavigator {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    RegistrationNavigator(RegistrationActivity registrationActivity){
        containerId = R.id.register_frag_frame;
        fragmentManager = registrationActivity.getSupportFragmentManager();

    }

    void navigateSchool(){
        transact(new SchoolRegisterFragment());
    }

    void navigateMentor(){
          transact(new MentorRegisterFragment());
    }

    private void transact(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(containerId,fragment)
                .commitAllowingStateLoss();
    }
}
