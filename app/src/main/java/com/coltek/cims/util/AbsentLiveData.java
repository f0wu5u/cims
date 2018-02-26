package com.coltek.cims.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
