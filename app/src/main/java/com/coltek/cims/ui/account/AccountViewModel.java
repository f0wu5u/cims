package com.coltek.cims.ui.account;

import android.arch.lifecycle.ViewModel;

import com.coltek.cims.repository.ApplicationRepository;

import javax.inject.Inject;

/**
 * Created by BraDev ${LOCALE} on 3/16/2018.
 */

public class AccountViewModel extends ViewModel {

//    private final ApplicationRepository applicationRepository;

    @Inject
    public AccountViewModel(ApplicationRepository applicationRepository) {
//        this.applicationRepository = applicationRepository;
    }

    void changePassword(String oldPassword, String newPassword) {
        //TODO: SEND PASSWORDS TO SERVER
    }
}
