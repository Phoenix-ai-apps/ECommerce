package com.demo.ecommerce.di.components;

import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.di.modules.AppModule;
import com.demo.ecommerce.di.modules.DatabaseModules;
import com.demo.ecommerce.presentation.gridView.GridViewFragmentPresenter;
import com.demo.ecommerce.presentation.mainActivity.MainActivityPresenter;
import com.demo.ecommerce.presentation.recyclerView.RecyclerViewFragmentPresenter;

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
    void inject(GridViewFragmentPresenter presenter);
    void inject(RecyclerViewFragmentPresenter presenter);

}
