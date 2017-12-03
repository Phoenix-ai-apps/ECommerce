package com.demo.ecommerce.presentation.homeActivity;

import android.util.Log;

import com.demo.ecommerce.HomeActivity;
import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.fragments.homeActivity.HomeFragment;
import com.demo.ecommerce.models.MenuCategoryNSubCategory;
import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.product.Products;

import java.util.ArrayList;
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
        productsDataSource.open();

        List<Categories> categoriesList = categoryDataSource.getAllCategories();
        List<MenuCategoryNSubCategory> mainCategory = new ArrayList<>();


            for(Categories categories : categoriesList){

                if (categories != null && categories.getChild_categories().length > 0) {

                    MenuCategoryNSubCategory menuCategoryNSubCategory = new MenuCategoryNSubCategory();

                    List<Products> productsList = new ArrayList<>();

                    menuCategoryNSubCategory.setCategory(categories);

                    List<Products> tempProd = new ArrayList<>();

                    for(int i = 0; i < categories.getChild_categories().length ; i++){

                            Categories categories1 = categoryDataSource.getCategoryById(categories.getChild_categories()[i]);

                            if(categories1 != null){

                                if(categories1.getChild_categories() != null && categories1.getChild_categories().length > 0){

                                    for(int j = 0; j< categories1.getChild_categories().length ; j++){
                                        productsList = productsDataSource.getAllProductsByCatergoryId(categories.getChild_categories()[j]);

                                        if(productsList != null && productsList.size() > 0){
                                            tempProd.addAll(productsList);
                                        }

                                    }

                                }else{
                                    tempProd.addAll(productsDataSource.getAllProductsByCatergoryId(String.valueOf(categories1.getId())));
                                }

                            }


                        menuCategoryNSubCategory.setProductsList(tempProd);

                    }
                    mainCategory.add(menuCategoryNSubCategory);

                }

            }

            categoryDataSource.close();
            productsDataSource.close();
            mView.populateMenuAndSubMenu(mainCategory);



    }

    @Override
    public void refreshProducts(String productName) {

        productsDataSource.open();
        List<Products> productsList = productsDataSource.getAllProducstsByName(productName);
        productsDataSource.close();

        if(mView != null){
            mView.refreshHomeProducts(productsList);
        }else {

           Log.e(TAG, "Null");
        }


    }

    @Override
    public void addProductDetailsActivity(Products products) {
         homeFragmentView.addProductActivity(products);
    }
}
