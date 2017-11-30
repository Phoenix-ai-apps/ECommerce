package com.demo.ecommerce.di.components;

import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.di.modules.AppModule;
import com.demo.ecommerce.di.modules.DatabaseModules;
import com.demo.ecommerce.presentation.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by root on 27/11/17.
 */



@Singleton
@Component(modules = {AppModule.class, DatabaseModules.class})
public interface AppComponent {

    void inject(AppContext appContext);

    //Activity Injection
    void inject(MainActivity mainActivity);

    void inject(MainActivityPresenter presenter);

}
