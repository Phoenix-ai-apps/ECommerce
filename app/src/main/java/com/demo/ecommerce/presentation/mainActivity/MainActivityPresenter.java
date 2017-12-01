package com.demo.ecommerce.presentation.mainActivity;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.BuildConfig;
import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.database.datasource.AppVersionDataSource;
import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.managers.CustomVolleyPostRequestWithTextPlain;
import com.demo.ecommerce.models.AppVersion;
import com.demo.ecommerce.models.Categories;
import com.demo.ecommerce.models.EcommerceModel;
import com.demo.ecommerce.models.ProductRanking;
import com.demo.ecommerce.models.Products;
import com.demo.ecommerce.models.Rankings;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.DeserializeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;
import static com.demo.ecommerce.constants.AppConstants.CONNECTION_TIMEOUT_20_SEC;
import static com.demo.ecommerce.constants.UrlConstants.SERVICE_JSON;

/**
 * Created by root on 27/11/17.
 */

public class MainActivityPresenter implements MainActivityContractor.Presenter {

    private MainActivityContractor.View mView;

    @Inject
    CategoryDataSource categoryDataSource;

    @Inject
    ProductsDataSource productsDataSource;

    public <T extends MainActivity & MainActivityContractor.View> MainActivityPresenter(T view) {
        this.mView = view;

        DependencyInjector.appComponent().inject(this);

    }

    @Override
    public void start() {


    }

    @Override
    public void doServerCall() {

        mView.showProgress();

        String URL = BuildConfig.BASE_URL + SERVICE_JSON;

        try {

            CustomVolleyPostRequestWithTextPlain jsonObjectRequest = new CustomVolleyPostRequestWithTextPlain(Request.Method.GET,URL,"", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    if(response != null && response.trim().length() > 0 && ApplicationUtils.isValidJson(response)){

                        EcommerceModel ecommerceModel = DeserializeUtils.deserializeEcommerceResponse(response);

                            // test
                            if(ecommerceModel != null && ecommerceModel.getCategories() != null
                                    && ecommerceModel.getRankings() != null && ecommerceModel.getCategories().size() > 0
                                    && ecommerceModel.getRankings().size() > 0){

                                categoryDataSource.open();
                                productsDataSource.open();
                                categoryDataSource.deleteCategory(); // deleting Old Category Data
                                productsDataSource.deleteProducts(); // deleting Old Products Data

                                List<Products> productsList = new ArrayList();

                                for(Categories categories : ecommerceModel.getCategories()){
                                    Log.e(TAG, "category name : "+ categories.getName() + "\n category ID : "+ categories.getId() );

                                    categoryDataSource.createCategory(categories);

                                    for(Products products : categories.getProducts()){
                                        productsList.add(products);
                                    }

                                }


                                // creating Products Table as per the Order, View and Share Id : Start

                                for(Products products : productsList){

                                    Products productsLocal = new Products();
                                    productsLocal.setRankingID(products.getRankingID());

                                    for(Rankings rankings : ecommerceModel.getRankings()){

                                        for(ProductRanking ranking : rankings.getProducts() ){

                                            if(ranking.getId() == products.getRankingID()){

                                                if(ranking.getOrder_count() != 0){
                                                    productsLocal.setOrderId(ranking.getOrder_count());

                                                }else if(ranking.getView_count() != 0){
                                                    productsLocal.setViewId(ranking.getView_count());

                                                }else if(ranking.getShares() != 0){
                                                    productsLocal.setShareId(ranking.getShares());

                                                }

                                            }

                                        }

                                    }

                                    if(products.getRankingID() == productsLocal.getRankingID()){

                                        products.setOrderId(productsLocal.getOrderId());
                                        products.setShareId(productsLocal.getShareId());
                                        products.setViewId(productsLocal.getViewId());

                                        productsDataSource.createProducts(products);

                                    }

                                }
                                // creating Products Table as per the Order, View and Share Id : ens


           /* for(Categories categories : categoryDataSource.getAllCategories()){

                String[] child = categories.getChild_categories();

                if(child != null && child.length > 0){
                    Log.e(TAG, " "+categories.getId()+ " - "+categories.getName()+" - "+ categories.getChild_categories()[0]);

                }else {
                    Log.e(TAG, " "+categories.getId()+ " - "+categories.getName()+" - NULL");
                }

            }*/

                                categoryDataSource.close();
                                productsDataSource.close();

                                mView.hideProgress();

                                mView.addHomeActivity();

                            }

                            // test

                        else {

                            //error msg shown in main activity
                            mView.showErrorInView("No response from the server");
                        }

                        mView.hideProgress();

                    }else {

                        mView.hideProgress();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e(TAG, error.getMessage());
                    mView.hideProgress();
                }
            });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT_20_SEC,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppContext appContext = new AppContext();
            appContext.cancelPendingRequests(URL);
            appContext.addToRequestQueue(jsonObjectRequest, URL);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showVolleyException("Exception occured while fetching data from server");
            mView.hideProgress();
        }

    }
}
