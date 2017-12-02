package com.demo.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.ecommerce.R;
import com.demo.ecommerce.models.product.Products;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 30/11/17.
 */

public class HomeFragmentRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<Products> productsList;

    public HomeFragmentRecAdapter(Context mContext, List<Products> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_product_name)            TextView txtProductName;
        @BindView(R.id.txt_order)                   TextView txtOrder;
        @BindView(R.id.txt_share)                   TextView txtShare;
        @BindView(R.id.txt_viewed)                  TextView txtViewed;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            if(productsList != null && productsList.size() > 0){
                Products products = productsList.get(position);

                if(products != null && products.getName() != null && products.getName().trim().length() > 0){
                    itemViewHolder.txtProductName.setText(products.getName().trim());
                }

                if(products != null && products.getOrderId() != 0){
                    itemViewHolder.txtOrder.setText("Ordered: "+products.getOrderId());
                }else {
                    itemViewHolder.txtOrder.setText("Ordered: 0");
                }

                if(products != null && products.getShareId() != 0){
                    itemViewHolder.txtShare.setText("Shared: "+products.getShareId());
                }else{
                    itemViewHolder.txtShare.setText("Shared: 0");
                }

                if(products != null && products.getViewId() != 0){
                    itemViewHolder.txtViewed.setText("Viewed: "+products.getViewId());
                }else {
                    itemViewHolder.txtViewed.setText("Viewed: 0");
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}