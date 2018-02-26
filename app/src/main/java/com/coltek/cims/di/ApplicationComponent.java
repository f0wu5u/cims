package com.coltek.cims.di;

import com.coltek.cims.MainApplication;
import com.coltek.cims.di.modules.ActivityBuilder;
import com.coltek.cims.di.modules.Main;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ActivityBuilder.class,
        Main.class,
        AndroidSupportInjectionModule.class
})

public interface ApplicationComponent  extends AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder{
        @BindsInstance Builder application(android.app.Application application);
        ApplicationComponent build();
    }

    void inject(MainApplication application);
}
