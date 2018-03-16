package com.coltek.cims.repository;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coltek.cims.api.ApiResponse;
import com.coltek.cims.api.ApplicationService;
import com.coltek.cims.api.AuthenticationResponse;
import com.coltek.cims.api.NetworkBoundResource;
import com.coltek.cims.db.daos.MentorDao;
import com.coltek.cims.db.daos.SchoolDao;
import com.coltek.cims.db.daos.StudentDao;
import com.coltek.cims.entity.Credentials;
import com.coltek.cims.entity.Mentor;
import com.coltek.cims.entity.School;
import com.coltek.cims.entity.Student;
import com.coltek.cims.helper.AppExecutors;
import com.coltek.cims.helper.Resource;
import com.coltek.cims.util.AbsentLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApplicationRepository {

    private final StudentDao studentDao;
    private final MentorDao mentorDao;
    private final SchoolDao schoolDao;
    private final ApplicationService applicationService;
    private final AppExecutors executors;
    private final SharedPreferences sharedPreferences;

    @Inject
    public ApplicationRepository(AppExecutors executors, ApplicationService applicationService, StudentDao studentDao, MentorDao mentorDao, SchoolDao schoolDao, SharedPreferences sharedPreferences) {
        this.studentDao = studentDao;
        this.executors = executors;
        this.applicationService = applicationService;
        this.mentorDao = mentorDao;
        this.schoolDao = schoolDao;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<Resource<AuthenticationResponse>> attemptLogin(Credentials credentials){
        return new NetworkBoundResource<AuthenticationResponse,AuthenticationResponse>(executors){

            @Override
            protected void saveCallResult(@NonNull AuthenticationResponse item) {
                if(!item.isError()) {
                    sharedPreferences.edit()
                            .putBoolean("active", true)
                            .putString("token", item.getStudent().getToken())
                            .putString("id", item.getStudent().getIndex())
                            .apply();
                    studentDao.save(item.getStudent());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable AuthenticationResponse data) {
                return true;
            }

            @Override
            protected boolean shouldLoadDb() {
                return false;
            }

            @Override
            protected boolean shouldSaveDb() {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<AuthenticationResponse> loadFromDb() {
               return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AuthenticationResponse>> createCall() {
                return applicationService.login(credentials);
            }
        }.asLiveData();
    }

    public void updateContact(String email){
      executors.diskIO().execute(() -> studentDao.updateContact(email));
    }

    public LiveData<Student> getProfile(){
        return studentDao.getProfile();
    }

    public LiveData<Mentor> getMentor() {
        return mentorDao.getProfile();
    }

    public LiveData<School> getSchool() {
        return schoolDao.getSchool();
    }

    public LiveData<Resource<String>> attemptPasswordReset(String email){
        return new NetworkBoundResource<String,String>(executors){

            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @Override
            protected boolean shouldLoadDb() {
                return false;
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                return applicationService.reset(email);
            }
        }.asLiveData();
    }


    public LiveData<Resource<String>> registerMentor(Mentor mentor) {
        if (mentor.getIndex_number().equals("0"))
            mentor.setIndex_number(sharedPreferences.getString("id", null));
        return new NetworkBoundResource<String, String>(executors) {
            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @Override
            protected boolean shouldLoadDb() {
                executors.diskIO().execute(()-> mentorDao.save(mentor));
                return false;
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                return applicationService.registerMentor(mentor);
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> registerSchool(School sch) {
        if (sch.getIndex_number().equals("0"))
            sch.setIndex_number(sharedPreferences.getString("id", null));
        return new NetworkBoundResource<String, String>(executors) {
            @Override
            protected void saveCallResult(@NonNull String item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @Override
            protected boolean shouldLoadDb() {
                executors.diskIO().execute(()-> schoolDao.save(sch));
                return false;
            }

            @Override
            protected boolean shouldSaveDb() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<String> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<String>> createCall() {
                return applicationService.registerSchool(sch);
            }
        }.asLiveData();
    }

}
