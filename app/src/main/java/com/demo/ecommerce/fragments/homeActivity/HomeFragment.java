package com.demo.ecommerce.fragments.homeActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.ecommerce.R;
import com.demo.ecommerce.adapters.HomeFragmentRecAdapter;
import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.fragments.BaseFragment;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityContractor;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by root on 1/12/17.
 */

public class HomeFragment extends BaseFragment<HomeActivityPresenter> implements HomeActivityContractor.HomeFragmentView {


    @BindView(R.id.recyclerView)     RecyclerView recyclerView;
    @BindView(R.id.layout_no_items)  LinearLayout layoutNoItems;

    @Inject
    CategoryDataSource categoryDataSource;

    @Inject
    ProductsDataSource productsDataSource;

    List<Products> productsList;

    private Activity mActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;

    }

    @SuppressLint("ValidFragment")
    public HomeFragment(List<Products> productsList){
        this.productsList = productsList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initResources();

    }

    private void initResources() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        if(productsList !=null && productsList.size() > 0){
            HomeFragmentRecAdapter recyclerViewAdapter = new HomeFragmentRecAdapter(mActivity,productsList);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();

        }else {
             recyclerView.setVisibility(View.GONE);
             layoutNoItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected HomeActivityPresenter getPresenter() {
        return new HomeActivityPresenter(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }



    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }


}
