package com.demo.ecommerce.models;

import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.product.Products;

import java.util.List;

/**
 * Created by root on 2/12/17.
 */

public class MenuCategoryNSubCategory {

    private Categories       category;
    private List<Categories> subCategory;
    private List<Products>   productsList;


    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<Categories> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<Categories> subCategory) {
        this.subCategory = subCategory;
    }
}
