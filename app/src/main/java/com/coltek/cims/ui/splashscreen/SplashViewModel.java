package com.coltek.cims.ui.splashscreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by BraDev ${LOCALE} on 1/14/2018.
 */

public class SplashViewModel extends ViewModel {

   private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();

    @Inject
    public SplashViewModel(SharedPreferences sharedPreferences) {
        isLoggedIn.setValue(sharedPreferences.getBoolean("active",false));
    }

    public LiveData<Boolean> isLogged() {
        return isLoggedIn;
    }
}
