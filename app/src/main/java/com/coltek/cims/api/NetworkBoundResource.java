package com.coltek.cims.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.coltek.cims.helper.AppExecutors;
import com.coltek.cims.helper.Resource;
import com.coltek.cims.util.AbsentLiveData;
import com.coltek.cims.util.Objects;

/**
 * Created by BraDev ${LOCALE} on 1/12/2018.
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;
    private int retryLimit = 5;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        LiveData<ResultType> dbSource = shouldLoadDb() ? loadFromDb() : AbsentLiveData.create();
        result.setValue(Resource.loading(null));
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) appExecutors.diskIO().execute(() -> {
                if (shouldSaveDb()) {
                    saveCallResult(processResponse(response));
                }
                appExecutors.mainThread().execute(() ->
                        result.addSource(dbSource, newData -> setValue(Resource.success(newData)))
                );
            });
            else {
//                onFetchFailed(response.code);
                if (response.code == 500 && retryLimit>0){
                    retryLimit-=1;
                    fetchFromNetwork(dbSource);
                    return;
                }
                result.addSource(dbSource,newData -> setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    // TODO: 1/16/2018 ADD PROPER JOB SCHEDULE FOR RETRY TASK 
    protected void onFetchFailed() {
        
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @MainThread
    protected abstract boolean shouldLoadDb();

    @MainThread
    protected abstract boolean shouldSaveDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}