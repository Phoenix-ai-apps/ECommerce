package com.demo.ecommerce.presentation.homeActivity;

import com.demo.ecommerce.BasePresenter;
import com.demo.ecommerce.BaseView;
import com.demo.ecommerce.models.MenuCategoryNSubCategory;
import com.demo.ecommerce.models.product.Products;

import java.util.List;

/**
 * Created by root on 1/12/17.
 */

public interface HomeActivityContractor {

    interface View extends BaseView{

        void populateMenuAndSubMenu(List<MenuCategoryNSubCategory> subCategoryList);

        void refreshHomeProducts(List<Products> productsList);

    }

    interface HomeFragmentView extends BaseView{

      //  void refreshProdusts(List<Products> productsList);

    }


    interface Presenter extends BasePresenter{

        void bindMenu();

        void refreshProducts(String productName);

    }

}
