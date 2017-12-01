package com.demo.ecommerce.presentation.mainActivity;

import com.demo.ecommerce.BasePresenter;
import com.demo.ecommerce.BaseView;
import com.demo.ecommerce.models.EcommerceModel;

/**
 * Created by root on 27/11/17.
 */

public interface MainActivityContractor {

    interface View extends BaseView {

        void showErrorInView(String error);
        void showVolleyException(String e);
        void addHomeActivity();
    }

    interface Presenter extends BasePresenter {

        void doServerCall();

    }



}
