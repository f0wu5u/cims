package com.coltek.cims.ui.resetpassword;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.coltek.cims.helper.Resource;
import com.coltek.cims.repository.ApplicationRepository;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;

/**
 * Created by BraDev ${LOCALE} on 11/11/2017.
 */

public class ResetViewModel extends ViewModel {

    private final MutableLiveData<String> email =  new MutableLiveData();
    private final LiveData<Resource<String>> response;

    @Inject
    public ResetViewModel(ApplicationRepository applicationRepository){
        response = switchMap(email,v-> applicationRepository.attemptPasswordReset(v));
    }

    public void setEmail(String email){
        this.email.setValue(email);
    }

    public LiveData<Resource<String>> getResponse(){
        return response;
    }


}
