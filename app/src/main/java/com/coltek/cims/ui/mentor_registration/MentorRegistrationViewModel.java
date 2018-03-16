package com.coltek.cims.ui.mentor_registration;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.coltek.cims.entity.Mentor;
import com.coltek.cims.helper.Resource;
import com.coltek.cims.repository.ApplicationRepository;
import com.coltek.cims.util.AbsentLiveData;
import com.coltek.cims.util.Objects;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;



public class MentorRegistrationViewModel extends ViewModel {

    private final LiveData<Mentor> mentor;
    private final LiveData<Resource<String>> resourceLiveData;
    private final MutableLiveData<Mentor> mentorMutableLiveData = new MutableLiveData<>();

    @Inject
    MentorRegistrationViewModel(ApplicationRepository applicationRepository) {
        this.mentor =  applicationRepository.getMentor();
        resourceLiveData = switchMap(mentorMutableLiveData, mentor1 -> {
            if (mentor1 == null) {
                return AbsentLiveData.create();
            } else {
                return applicationRepository.registerMentor(mentor1);
            }
        });
    }

    void modifyMentor(Mentor mentor){
        if (!Objects.equals(this.mentorMutableLiveData.getValue(), mentor))
            mentorMutableLiveData.setValue(mentor);
    }

    LiveData<Mentor> getMentor(){
        return mentor;
    }

    LiveData<Resource<String>> getResource() {
        return resourceLiveData;
    }
}
