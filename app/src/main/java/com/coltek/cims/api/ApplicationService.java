package com.coltek.cims.api;

import android.arch.lifecycle.LiveData;

import com.coltek.cims.entity.Credentials;
import com.coltek.cims.entity.Mentor;
import com.coltek.cims.entity.School;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryName;


public interface ApplicationService {

        @POST("login")
        LiveData<ApiResponse<AuthenticationResponse>> login(@Body Credentials credentials);

        @GET("logout")
        LiveData<ApiResponse<AuthenticationResponse>> logout(@QueryName String IndexNumber);

        @GET("rest_password")
        LiveData<ApiResponse<String>> reset(@QueryName String Email);

        @PUT("updateIntern")
        void updateContact(@Body String email);

    @POST("registerMentor")
        LiveData<ApiResponse<String>> registerMentor(@Body Mentor mentor);

    @POST("registerSchool")
        LiveData<ApiResponse<String>> registerSchool(@Body School sch);
}
