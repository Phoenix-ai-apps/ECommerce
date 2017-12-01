package com.demo.ecommerce;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ecommerce.adapters.RecyclerViewAdapter;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.fragments.homeActivity.HomeFragment;
import com.demo.ecommerce.models.Products;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityContractor;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> implements HomeActivityContractor.View, View.OnClickListener {

    public static final String TAG = HomeActivity   .class.getSimpleName();

    @BindView(R.id.toolbar)         Toolbar        toolbar;
    @BindView(R.id.drawer_layout)   DrawerLayout   drawerLayout;
    @BindView(R.id.nav_view)        NavigationView navigationView;

    @BindView(R.id.layout_main)     LinearLayout   layoutMain;

    @BindView(R.id.txt_verion)      TextView       txtVerion;
    @BindView(R.id.txt_sort)        TextView       txtSort;
    @BindView(R.id.edt_search)      EditText       edtSearch;

    @Inject
    ProductsDataSource productsDataSource;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_drawer_layout);

        DependencyInjector.appComponent().inject(this);

        ButterKnife.bind(this);

        initResources();

    }

    private void initResources(){

        txtSort.setOnClickListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawer, float slideOffset) {
                layoutMain.setX(navigationView.getWidth() * slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }
        });
        toggle.syncState();

        txtVerion.setText("v"+BuildConfig.APP_VERSION);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPresenter.refreshHomeProducts(s.toString().trim());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startMenuBinding();

    }

    private void startMenuBinding() {

        mPresenter.bindMenu();

        productsDataSource.open();
        List<Products> productsList = productsDataSource.getAllProducts();
        productsDataSource.close();

        addHomeFragment(productsList);

    }

    private void addHomeFragment(List<Products> productsList) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment(productsList);
        transaction.replace(R.id.frame_layout_main, fragment);
        transaction.commitAllowingStateLoss();

    }

    @Override
    protected HomeActivityPresenter getPresenter() {
        return new HomeActivityPresenter(this);
    }

    @Override
    public void populateMenuAndSubMenu() {

    }

    @Override
    public void refreshHomeProducts(List<Products> productsList) {

        addHomeFragment(productsList);

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();
        showExitDialog();

    }

    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit!");
        builder.setMessage("Do you want to close this app ?");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    @Override
    public void onClick(View view) {

        if(view == txtSort){

           showProductSortDialog(this);

        }

    }

    private Dialog showProductSortDialog(Context context) {

        Dialog alert = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertDlgView = inflater.inflate(R.layout.dialog_product_sort, null);

        TextView txtOrdered = (TextView) alertDlgView.findViewById(R.id.txt_ordered);
        TextView txtShared  = (TextView) alertDlgView.findViewById(R.id.txt_shared);
        TextView txtViewed  = (TextView) alertDlgView.findViewById(R.id.txt_viewed);

        Button btnApply = (Button) alertDlgView.findViewById(R.id.btn_apply);
        btnApply.setTransformationMethod(null);


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                initResources();

            }
        });

        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(alertDlgView);
        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        return alert;
    }
}
