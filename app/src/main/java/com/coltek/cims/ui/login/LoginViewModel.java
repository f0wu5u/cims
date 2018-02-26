package com.coltek.cims.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.coltek.cims.api.AuthenticationResponse;
import com.coltek.cims.entity.Credentials;
import com.coltek.cims.helper.Resource;
import com.coltek.cims.repository.ApplicationRepository;

import javax.inject.Inject;

import static android.arch.lifecycle.Transformations.switchMap;


public class LoginViewModel extends ViewModel {

  private final MutableLiveData<Credentials> credential = new MutableLiveData<>();
  private final LiveData<Resource<AuthenticationResponse>> response;
  private ApplicationRepository applicationRepository;

    @Inject
  public LoginViewModel(ApplicationRepository applicationRepository) {
      this.applicationRepository = applicationRepository;
      response =  switchMap(credential, v -> this.applicationRepository.attemptLogin(v));
    }


    public LiveData<Resource<AuthenticationResponse>> getResponse(){
        return response;
    }

    public void attemptLogin(Credentials cr){
        this.credential.setValue(cr);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(LoginViewModel.class.getSimpleName(),"Cleared");
    }
}
