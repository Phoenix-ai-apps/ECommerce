package com.demo.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ecommerce.adapters.HomeFragmentRecAdapter;
import com.demo.ecommerce.adapters.ProductDetailsAdapter;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.models.product.Variants;
import com.demo.ecommerce.presentation.productActivity.ProductActivityContarctor;
import com.demo.ecommerce.presentation.productActivity.ProductActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends BaseActivity<ProductActivityPresenter> implements ProductActivityContarctor.View  {

    private static final String TAG       = "ProductDetailsActivity";

    @BindView(R.id.toolbar)                  Toolbar toolbar;
    @BindView(R.id.rec_view_product)         RecyclerView recViewProduct;
    @BindView(R.id.layout_price)             LinearLayout layoutPrice;

    @BindView(R.id.txt_product_name)         TextView txtProductName;
    @BindView(R.id.txt_price)                TextView txtPrice;
    @BindView(R.id.txt_size)                 TextView txtSize;
    @BindView(R.id.txt_tax)                  TextView txtTax;

    private Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        DependencyInjector.appComponent().inject(this);

        ButterKnife.bind(this);

        products = getIntent().getParcelableExtra(PRODUCT_OBJECT);

        initResources();;

    }

    private void initResources() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true) ;


       if(products != null && products.getVariants() != null && products.getVariants().size() > 0){

           txtProductName.setText(products.getName().trim());


           recViewProduct.setHasFixedSize(true);
           recViewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

           ProductDetailsAdapter recyclerViewAdapter = new ProductDetailsAdapter(mPresenter,this,products);
           recViewProduct.setAdapter(recyclerViewAdapter);
           recyclerViewAdapter.notifyDataSetChanged();


       }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }

    @Override
    protected ProductActivityPresenter getPresenter() {
        return new ProductActivityPresenter(this);
    }

    @Override
    public void showProductDetails(Variants variants) {

        if(variants != null){

            layoutPrice.setVisibility(View.VISIBLE);

            if(variants.getPrice() != null && variants.getPrice().trim().length() > 0){
                txtPrice.setText("Price - "+variants.getPrice().trim());

            }else{
                txtPrice.setVisibility(View.GONE);
            }

            if(variants.getSize() != null && variants.getSize().trim().length() > 0){
                txtSize.setText("Size - "+variants.getSize().trim());
            }else{
                txtSize.setVisibility(View.GONE);
            }

            if(products.getTax() != null && products.getTax().getName() != null && products.getTax().getName().trim().length() > 0){

                if(products.getTax().getValue() != null && products.getTax().getValue().trim().length() > 0){
                    txtTax.setText("Tax - "+products.getTax().getName().trim()+" - "+products.getTax().getValue().trim()+"%");
                }else {
                    txtTax.setText("Tax - "+products.getTax().getName().trim());
                }

            }else{
                txtTax.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
