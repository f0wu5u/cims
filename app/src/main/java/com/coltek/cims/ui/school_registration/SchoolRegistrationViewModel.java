package com.coltek.cims.ui.school_registration;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.coltek.cims.entity.School;
import com.coltek.cims.helper.Resource;
import com.coltek.cims.repository.ApplicationRepository;
import com.coltek.cims.util.AbsentLiveData;
import com.coltek.cims.util.Objects;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

/**
 * Created by BraDev ${LOCALE} on 1/25/2018.
 */

public class SchoolRegistrationViewModel extends ViewModel {
    private final LiveData<School> school;
    private final LiveData<Resource<String>> resourceLiveData;
    private final MutableLiveData<School> schoolMutableLiveData = new MutableLiveData<>();

    @Inject
    public SchoolRegistrationViewModel(ApplicationRepository applicationRepository) {
        school = applicationRepository.getSchool();
        resourceLiveData = switchMap(schoolMutableLiveData,sch->{
            if(sch==null){
                return AbsentLiveData.create();
            }else{
                return applicationRepository.registerSchool(sch);
            }
        });
    }

    void modifySchool(School school) {
        if (!Objects.equals(this.schoolMutableLiveData.getValue(), school))
            schoolMutableLiveData.setValue(school);

    }

    LiveData<School> getSchool() {
        return school;
    }

    LiveData<Resource<String>> getResource() {
        return resourceLiveData;
    }
}
