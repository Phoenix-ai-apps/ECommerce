package com.demo.ecommerce.fragments.mainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.ecommerce.R;
import com.demo.ecommerce.database.datasource.CategoryDataSource;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.fragments.BaseFragment;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.models.EcommerceModel;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;
import com.demo.ecommerce.presentation.mainActivity.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 30/11/17.
 */

public class SplashFragment extends BaseFragment<MainActivityPresenter> implements MainActivityContractor.View {


    @BindView(R.id.layout_progress)  LinearLayout layoutProgress;

    @Inject
    CategoryDataSource categoryDataSource;

    @Inject
    ProductsDataSource productsDataSource;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        DependencyInjector.appComponent().inject(this);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void showErrorInView(String error) {

    }

    @Override
    public void showVolleyException(String e) {

    }

    @Override
    public void addHomeActivity() {

    }
    
    @Override
    protected MainActivityPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }

}
