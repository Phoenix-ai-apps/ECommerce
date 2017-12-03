package com.demo.ecommerce.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ecommerce.HomeActivity;
import com.demo.ecommerce.R;
import com.demo.ecommerce.database.datasource.ProductsDataSource;
import com.demo.ecommerce.fragments.homeActivity.HomeFragment;
import com.demo.ecommerce.models.MenuCategoryNSubCategory;
import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.homeActivity.HomeView;
import com.demo.ecommerce.models.product.Products;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 30/11/17.
 */

public class MenuRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity      mContext;
    List<MenuCategoryNSubCategory> subCategoryList;

    public MenuRecAdapter(AppCompatActivity mContext, List<MenuCategoryNSubCategory> subCategoryList) {
        this.mContext        = mContext;
        this.subCategoryList = subCategoryList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_container)           LinearLayout layoutContainer;
        @BindView(R.id.txt_category)               TextView txtCategory;


        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            if(subCategoryList != null && subCategoryList.size() > 0){
                MenuCategoryNSubCategory categoryNSubCategory = subCategoryList.get(position);


                if(categoryNSubCategory != null && categoryNSubCategory.getCategory() != null && categoryNSubCategory.getCategory().getName() != null
                        && categoryNSubCategory.getCategory().getName().trim().length() > 0 &&
                        categoryNSubCategory.getProductsList() != null && categoryNSubCategory.getProductsList().size() > 0){

                    itemViewHolder.txtCategory.setText(categoryNSubCategory.getCategory().getName().trim());

                    itemViewHolder.layoutContainer.setOnClickListener(v -> addHomeFragment(categoryNSubCategory.getProductsList()));

                    itemViewHolder.layoutContainer.setVisibility(View.VISIBLE);

                }else {
                    itemViewHolder.layoutContainer.setVisibility(View.GONE);
                }

            }

        }

    }

    private void addHomeFragment(List<Products> productsList) {

        ((HomeView)mContext).addViewHomeFragment(productsList);

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }
}
