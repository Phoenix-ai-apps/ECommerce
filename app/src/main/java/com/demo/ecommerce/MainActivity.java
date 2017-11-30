package com.demo.ecommerce;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.models.Categories;
import com.demo.ecommerce.models.EcommerceModel;
import com.demo.ecommerce.models.ProductRanking;
import com.demo.ecommerce.models.Products;
import com.demo.ecommerce.models.Rankings;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;
import com.demo.ecommerce.presentation.mainActivity.MainActivityPresenter;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContractor.View {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    CategoryDataSource categoryDataSource;

    @Inject
    ProductsDataSource productsDataSource;

    @BindView(R.id.sliding_tabs)           TabLayout tabLayout;

    private GridFragment                   GridFragment;
    private RecyclerFragment               recyclerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();

        setupTabLayout();

        bindWidgetsWithAnEvent();

    }

    private void bindWidgetsWithAnEvent() {

        replaceFragment(GridFragment);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setCurrentTabFragment(int position) {

        switch (position)
        {
            case 0 :
                replaceFragment(GridFragment);
                break;

            case 1 :
                replaceFragment(recyclerFragment);
                break;

            default:

        }
    }


    private void replaceFragment(Fragment  fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void setupTabLayout() {

        GridFragment     = new GridFragment();
        recyclerFragment = new RecyclerFragment();
        tabLayout.addTab(tabLayout.newTab().setText("GRID"),true);
        tabLayout.addTab(tabLayout.newTab().setText("RECYCLER"));

        tabLayout.setTabTextColors(R.color.black,R.color.white);

        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#ffffff"));

    }

    private void initResources() {

        DependencyInjector.appComponent().inject(this);

        if(NetworkUtils.isConnected(this)){
            mPresenter.doServerCall();

        }else {

            ApplicationUtils.showToast(this,"Please connect to Internet");
        }

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter(this);
    }


    @Override
    public void showErrorInView(String error) {

        ApplicationUtils.showToast(this,error);
    }

    @Override
    public void showVolleyException(String e) {

        ApplicationUtils.showToast(this,e);
    }

    @Override
    public void storeDataToDB(EcommerceModel ecommerceModel) {


        //response from presenter is displayed here

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

            List<Products> productsListUpdated = new ArrayList<Products>();

            for(Rankings rankings : ecommerceModel.getRankings()){

                for(Products products : productsList) {

                    for (ProductRanking productRanking : rankings.getProducts()) {
                        if (productRanking.getOrder_count() != 0 && productRanking.getId() == products.getRankingID()) {
                                products.setOrderId(productRanking.getOrder_count());
                                productsListUpdated.add(products);

                        }else  if (productRanking.getView_count() != 0 && productRanking.getId() == products.getRankingID()) {
                                products.setViewId(productRanking.getView_count());
                                productsListUpdated.add(products);

                        }else if (productRanking.getShares() != 0 && productRanking.getId() == products.getRankingID()) {
                                products.setShareId(productRanking.getShares());
                                productsListUpdated.add(products);

                        }

                    }

                }

            }



            for(Products products : productsListUpdated){

                Products productsFromDB = productsDataSource.getProductById(products.getRankingID());

                if(productsFromDB != null){
                    if(productsFromDB.getOrderId() == 0 && products.getOrderId() != 0){
                        productsFromDB.setOrderId(products.getOrderId());

                    }
                    if(productsFromDB.getShareId() == 0 && products.getShareId() != 0){
                        productsFromDB.setShareId(products.getShareId());

                    }
                    if(productsFromDB.getViewId() == 0 && products.getViewId() != 0){
                        productsFromDB.setViewId(products.getViewId());

                    }

                    productsDataSource.updateProducts(productsFromDB);

                }else {
                    productsDataSource.createProducts(products);
                }

            }

            List<Products> productDBList = productsDataSource.getAllProducts();

            for(Products products : productDBList){
                Log.e(TAG, "Products : "+products.getRankingID()+"-"+products.getOrderId()+"-"+products.getViewId()+"-"+products.getShareId());
            }

            Log.e(TAG, "Products Count :"+productsListUpdated.size()+", Products from Category : "+productsList.size()+" Products DB Size :"+productDBList.size());


            for(Categories categories : categoryDataSource.getAllCategories()){

                String[] child = categories.getChild_categories();

                if(child != null && child.length > 0){
                    Log.e(TAG, " "+categories.getId()+ " - "+categories.getName()+" - "+ categories.getChild_categories()[0]);

                }else {
                    Log.e(TAG, " "+categories.getId()+ " - "+categories.getName()+" - NULL");
                }

            }




            categoryDataSource.close();
            productsDataSource.close();


        }


    }
}
