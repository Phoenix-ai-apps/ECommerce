package com.demo.ecommerce.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ecommerce.R;
import com.demo.ecommerce.models.product.Products;
import com.demo.ecommerce.models.product.Variants;
import com.demo.ecommerce.presentation.productActivity.ProductActivityContarctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 30/11/17.
 */

public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity mContext;
    List<Variants> variantsList;
    private ProductActivityContarctor.Presenter presenter ;

    public ProductDetailsAdapter(ProductActivityContarctor.Presenter presenter, AppCompatActivity mContext, Products products) {
        this.presenter    = presenter;
        this.mContext     = mContext;
        this.variantsList = products.getVariants() ;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_product_color)            TextView txtProductColor;
        @BindView(R.id.layout_color)                 LinearLayout layoutColor;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product_details, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            if(variantsList != null && variantsList.size() > 0){
                Variants variants = variantsList.get(position);

                if(variants != null && variants.getColor().trim().length() > 0){
                    itemViewHolder.txtProductColor.setText(variants.getColor().trim());
                }

                itemViewHolder.layoutColor.setOnClickListener(view -> addProductDetailsInActivity(variants));

            }
        }
    }

    private void addProductDetailsInActivity(Variants variants){
         presenter.setProductDetails(variants);
    }


    @Override
    public int getItemCount() {
        return variantsList.size();
    }
}
