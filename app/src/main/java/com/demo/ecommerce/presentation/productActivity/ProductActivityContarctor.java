package com.demo.ecommerce.presentation.productActivity;

import com.demo.ecommerce.BasePresenter;
import com.demo.ecommerce.BaseView;
import com.demo.ecommerce.models.product.Variants;

/**
 * Created by root on 3/12/17.
 */

public interface ProductActivityContarctor {

    interface View extends BaseView{
      void showProductDetails(Variants variants);
    }


    interface Presenter extends BasePresenter{

        void setProductDetails(Variants variants);

    }

}
