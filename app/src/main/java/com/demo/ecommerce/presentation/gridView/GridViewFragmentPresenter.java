package com.demo.ecommerce.presentation.gridView;

import com.demo.ecommerce.GridFragment;
import com.demo.ecommerce.di.DependencyInjector;

/**
 * Created by root on 30/11/17.
 */

public class GridViewFragmentPresenter implements GridViewFragmentContractor.Presenter {

    private GridViewFragmentContractor.View mView;


    public <T extends GridFragment & GridViewFragmentContractor.View> GridViewFragmentPresenter(T view) {

        this.mView = view;
        DependencyInjector.appComponent().inject(this);

    }
    @Override
    public void start() {

    }
}
