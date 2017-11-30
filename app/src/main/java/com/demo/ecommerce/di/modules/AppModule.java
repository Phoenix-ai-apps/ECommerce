package com.demo.ecommerce.di.modules;

import android.app.Application;
import android.content.Context;

import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.di.ApplicationContext;
import com.demo.ecommerce.di.DatabaseInfo;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.helper.HelperInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 27/11/17.
 */

@Module
public class AppModule implements HelperInterface {

    private final AppContext application;

    public AppModule(AppContext application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    AppContext provideApplication() {
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return DATABASE_NAME_RUNTIME;
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return DATABASE_VERSION_RUNTIME;
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
