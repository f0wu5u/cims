package com.coltek.cims.ui.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.coltek.cims.entity.Student;
import com.coltek.cims.repository.ApplicationRepository;

import javax.inject.Inject;

/**
 * Created by BraDev ${LOCALE} on 11/12/2017.
 */

public class ProfileViewModel extends ViewModel {

    private final LiveData<Student> studentProfile;
    private final ApplicationRepository applicationRepository;
    @Inject
    ProfileViewModel(ApplicationRepository applicationRepository) {
        studentProfile = applicationRepository.getProfile();
        this.applicationRepository = applicationRepository;
    }

    LiveData<Student> getStudentProfile(){
        return studentProfile;
    }

     void updateContact(String email){
        this.applicationRepository.updateContact(email);
    }


}
