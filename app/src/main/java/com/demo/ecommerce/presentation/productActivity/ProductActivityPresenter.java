package com.demo.ecommerce.presentation.productActivity;

import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.ProductDetailsActivity;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.models.product.Variants;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;

/**
 * Created by root on 3/12/17.
 */

public class ProductActivityPresenter implements ProductActivityContarctor.Presenter {

    private ProductActivityContarctor.View mView;

    public <T extends ProductDetailsActivity & ProductActivityContarctor.View> ProductActivityPresenter(T view) {
        this.mView = view;

        DependencyInjector.appComponent().inject(this) ;

    }

    @Override
    public void start() {

    }

    @Override
    public void setProductDetails(Variants variants) {
         mView.showProductDetails(variants) ;
    }
}
