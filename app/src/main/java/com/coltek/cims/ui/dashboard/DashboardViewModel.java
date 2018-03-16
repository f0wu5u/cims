package com.coltek.cims.ui.dashboard;

import android.arch.lifecycle.ViewModel;

import com.coltek.cims.repository.ApplicationRepository;

import javax.inject.Inject;

/**
 * Created by BraDev ${LOCALE} on 3/15/2018.
 */

public class DashboardViewModel extends ViewModel {

    private final ApplicationRepository applicationRepository;

    @Inject
    public DashboardViewModel(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    void updateContact(String email) {
        this.applicationRepository.updateContact(email);
    }


}
