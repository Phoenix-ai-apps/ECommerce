package com.demo.ecommerce.presentation.recyclerView;

import com.demo.ecommerce.RecyclerFragment;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.presentation.recyclerView.RecyclerViewFragmentContractor;

/**
 * Created by root on 30/11/17.
 */

public class RecyclerViewFragmentPresenter implements RecyclerViewFragmentContractor.Presenter {

    private RecyclerViewFragmentContractor.View mView;


    public <T extends RecyclerFragment & RecyclerViewFragmentContractor.View> RecyclerViewFragmentPresenter(T view) {

        this.mView = view;
        DependencyInjector.appComponent().inject(this);

    }

    @Override
    public void start() {

    }

}
