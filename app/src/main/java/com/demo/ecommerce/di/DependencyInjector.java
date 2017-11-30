package com.demo.ecommerce.di;


import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.di.components.AppComponent;
import com.demo.ecommerce.di.components.DaggerAppComponent;
import com.demo.ecommerce.di.modules.AppModule;
import com.demo.ecommerce.di.modules.DatabaseModules;

public class DependencyInjector {

    private static AppComponent appComponent;

    private DependencyInjector() {
    }

    public static void initialize(AppContext appContext) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext))
                .databaseModules(new DatabaseModules())
                .build();
    }

    public static AppComponent appComponent() {
        return appComponent;
    }
}
