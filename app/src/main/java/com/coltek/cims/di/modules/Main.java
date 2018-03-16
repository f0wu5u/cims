package com.coltek.cims.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import com.coltek.cims.api.ApplicationService;
import com.coltek.cims.db.AppDatabase;
import com.coltek.cims.db.daos.MentorDao;
import com.coltek.cims.db.daos.SchoolDao;
import com.coltek.cims.db.daos.StudentDao;
import com.coltek.cims.factory.LiveDataCallAdapterFactory;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static java.util.concurrent.TimeUnit.MINUTES;
import static timber.log.Timber.i;

@Module(includes = ViewModelBuilder.class)
public class Main {

    @Provides
    @Singleton
    ApplicationService applicationService(OkHttpClient okHttpClient){
        return new Retrofit.Builder().baseUrl("http://192.168.173.1/cims_api_v1/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(ApplicationService.class);
    }
    @Provides
    @Singleton
    HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> i(message)).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    Interceptor networkInterceptor(Headers headers) {
        return chain -> chain.proceed(chain.request()).newBuilder().headers(headers).build();
    }

    @Provides
    @Singleton
    Headers headers(SharedPreferences preferences,CacheControl cacheControl){
        return new Headers.Builder()
                .add("Cache-control",cacheControl.toString()).set("Authorization", preferences.getString("token", "CIMS-TOKEN-FREE")).set("Content-Type", "application/json;charset=utf-8")
                .build();
    }
    @Provides
    @Singleton
    CacheControl cacheControl() {
        return new CacheControl.Builder()
                .maxAge(2, MINUTES)
                .build();
    }

    @Provides
    @Singleton
    Cache cache(File file) {
        return new Cache(file, 10 * 1000 * 1000);
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, Interceptor networkInterceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(networkInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    File file(Application application) {
        return new File(application.getCacheDir(), "okhttp-cache");

    }



    @Provides
    @Singleton
    AppDatabase database(Application application){
        return  Room.databaseBuilder(application,AppDatabase.class, "com.coltek.cims-db").build();
    }

    @Provides
    @Singleton
    StudentDao studentDao(AppDatabase database){
        return database.studentDao();
    }

    @Provides
    @Singleton
    SchoolDao schoolDao(AppDatabase database){
        return database.schoolDao();
    }

    @Provides
    @Singleton
    MentorDao mentorDao(AppDatabase database){
        return database.mentorDao();
    }

    @Provides
    @Singleton
    SharedPreferences sharedPreferences(Application application){
        return application.getSharedPreferences("com.coltek.cims-pref", MODE_PRIVATE);
    }
}
