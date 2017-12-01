package com.demo.ecommerce.presentation.homeActivity;

import android.util.Log;
import android.widget.Toast;

import com.demo.ecommerce.HomeActivity;
import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.fragments.homeActivity.HomeFragment;
import com.demo.ecommerce.models.Categories;
import com.demo.ecommerce.models.Products;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by root on 1/12/17.
 */

public class HomeActivityPresenter implements HomeActivityContractor.Presenter{

    public static final String TAG = HomeActivityPresenter.class.getSimpleName();


    @Inject
    CategoryDataSource categoryDataSource;

    @Inject
    ProductsDataSource productsDataSource;


    private HomeActivityContractor.View mView;

    private HomeActivityContractor.HomeFragmentView homeFragmentView;

    public <T extends HomeActivity & HomeActivityContractor.View> HomeActivityPresenter(T view) {
        this.mView = view;

        DependencyInjector.appComponent().inject(this);

    }

    public <T extends HomeFragment & HomeActivityContractor.HomeFragmentView> HomeActivityPresenter(T view) {
        this.homeFragmentView = view;

        DependencyInjector.appComponent().inject(this);

    }



    @Override
    public void start() {

    }

    @Override
    public void bindMenu() {

        categoryDataSource.open();
        List<Categories> categoriesList = categoryDataSource.getAllCategories();
        categoryDataSource.close();

        if(categoriesList != null && categoriesList.size() > 0){

            for(Categories categories : categoriesList){

               String[] subCatergory = categories.getChild_categories();



            }

        }


    }

    @Override
    public void refreshHomeProducts(String productName) {

        productsDataSource.open();
        List<Products> productsList = productsDataSource.getAllProducstsByName(productName);
        productsDataSource.close();

        if(mView != null){
            mView.refreshHomeProducts(productsList);
        }else {

           Log.e(TAG, "Null");
        }


    }
}
