package com.demo.ecommerce;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.demo.ecommerce.adapters.MenuRecAdapter;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.fragments.homeActivity.HomeFragment;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.models.MenuCategoryNSubCategory;
import com.demo.ecommerce.models.homeActivity.HomeView;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityContractor;
import com.demo.ecommerce.presentation.homeActivity.HomeActivityPresenter;
import com.demo.ecommerce.utils.ProductSorter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> implements HomeActivityContractor.View, View.OnClickListener,HomeView {

    public static final String TAG = HomeActivity   .class.getSimpleName();

    @BindView(R.id.toolbar)         Toolbar        toolbar;
    @BindView(R.id.drawer_layout)   DrawerLayout   drawerLayout;
    @BindView(R.id.nav_view)        NavigationView navigationView;

    @BindView(R.id.layout_main)     LinearLayout   layoutMain;
    @BindView(R.id.layout_menu)     LinearLayout   layoutMenu;

    @BindView(R.id.txt_verion)      TextView       txtVerion;
    @BindView(R.id.txt_sort)        TextView       txtSort;
    @BindView(R.id.edt_search)      EditText       edtSearch;
    @BindView(R.id.rec_view_menu)   RecyclerView   recViewMenu;

    @Inject
    ProductsDataSource productsDataSource;

    private ActionBarDrawerToggle toggle;

    private List<Products> productsList;

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
        layoutMenu.setOnClickListener(this);

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

                if(s.toString().trim().length() > 0){
                    mPresenter.refreshProducts(s.toString().trim());
                }else {
                    addHomeFragment(productsList);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startHomeFragmentBinding();

    }

    private void startHomeFragmentBinding() {

        mPresenter.bindMenu();

        productsDataSource.open();
        List<Products> productsList = productsDataSource.getAllProducts();
        productsDataSource.close();

        addHomeFragment(productsList);

    }

    private void addHomeFragment(List<Products> productsList) {

        if(productsList != null && productsList.size() > 0){
            this.productsList = productsList;
        }


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
    public void populateMenuAndSubMenu(List<MenuCategoryNSubCategory> subCategoryList) {

        if(subCategoryList != null && subCategoryList.size() > 0){
            recViewMenu.setHasFixedSize(true);
            recViewMenu.setNestedScrollingEnabled(false);
            recViewMenu.setLayoutManager(new LinearLayoutManager(this));

            MenuRecAdapter recyclerViewAdapter = new MenuRecAdapter(this,subCategoryList);
            recViewMenu.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void refreshHomeProducts(List<Products> productsList) {

        addHomeFragment(productsList);

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

    @Override
    public void addViewHomeFragment(List<Products> productsList) {

        this.productsList = productsList;
        drawerLayout.closeDrawer(GravityCompat.START);

        addHomeFragment(productsList);


    }

    private Dialog showProductSortDialog(Context context) {

        Dialog alert = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertDlgView = inflater.inflate(R.layout.dialog_product_sort, null);

        final String[] order = {""},  share ={""}, viewed= {""};

        TextView txtOrdered = (TextView) alertDlgView.findViewById(R.id.txt_ordered);
        TextView txtShared  = (TextView) alertDlgView.findViewById(R.id.txt_shared);
        TextView txtViewed  = (TextView) alertDlgView.findViewById(R.id.txt_viewed);

        Button btnApply = (Button) alertDlgView.findViewById(R.id.btn_apply);
        btnApply.setTransformationMethod(null);

        txtOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    order[0]  = SORT_ORDER;
                    share[0]  = "";
                    viewed[0] = "";
                    txtOrdered.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check), null, null, null);
                    txtShared.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    txtViewed.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);



            }
        });


        txtShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    share[0] = SORT_SHARED;
                    order[0]  = "";
                    viewed[0] = "";
                    txtShared.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check), null, null, null);
                    txtOrdered.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    txtViewed.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            }
        });


        txtViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    viewed[0] = SORT_VIEWED;
                    order[0]  = "";
                    share[0] = "";
                    txtViewed.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check), null, null, null);
                    txtShared.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    txtOrdered.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            }
        });



        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                sortProductList(order[0], share[0], viewed[0]);

            }
        });

        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(alertDlgView);
        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        return alert;
    }

    private void sortProductList(String order, String share, String viewed) {

        Collections.sort(productsList, new ProductSorter(order, share, viewed));

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
        showExitDialog();

    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }

}
