package com.demo.ecommerce.presentation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.demo.ecommerce.AppContext;
import com.demo.ecommerce.BuildConfig;
import com.demo.ecommerce.MainActivity;
import com.demo.ecommerce.database.datasource.AppVersionDataSource;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.managers.CustomVolleyPostRequestWithTextPlain;
import com.demo.ecommerce.models.AppVersion;
import com.demo.ecommerce.models.EcommerceModel;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.DeserializeUtils;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;
import static com.demo.ecommerce.constants.AppConstants.CONNECTION_TIMEOUT_20_SEC;
import static com.demo.ecommerce.constants.UrlConstants.SERVICE_JSON;

/**
 * Created by root on 27/11/17.
 */

public class MainActivityPresenter implements MainActivityContractor.Presenter {

    private MainActivityContractor.View mView;
    private MainActivityPresenter presenter;


    @Inject
    AppVersionDataSource dataSource;

    public <T extends MainActivity & MainActivityContractor.View> MainActivityPresenter(T view) {
        this.mView = view;

        DependencyInjector.appComponent().inject(this);

    }

    @Override
    public void start() {


    }

    @Override
    public void doServerCall() {

        mView.showProgress();

        String URL = BuildConfig.BASE_URL + SERVICE_JSON;

        try {

            CustomVolleyPostRequestWithTextPlain jsonObjectRequest = new CustomVolleyPostRequestWithTextPlain(Request.Method.GET,URL,"", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    if(response != null && response.trim().length() > 0 && ApplicationUtils.isValidJson(response)){

                        EcommerceModel ecommerceModel = DeserializeUtils.deserializeEcommerceResponse(response);

                        if(ecommerceModel != null){

                            //response passed on to the main activity
                            mView.storeDataToDB(ecommerceModel);

                        }else {

                            //error msg shown in main activity
                            mView.showErrorInView("No response from the server");
                        }

                        mView.hideProgress();

                    }else {

                        mView.hideProgress();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e(TAG, error.getMessage());
                    mView.hideProgress();
                }
            });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(CONNECTION_TIMEOUT_20_SEC,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppContext appContext = new AppContext();
            appContext.cancelPendingRequests(URL);
            appContext.addToRequestQueue(jsonObjectRequest, URL);

        } catch (Exception e) {
            e.printStackTrace();
            mView.showVolleyException("Exception occured while fetching data from server");
            mView.hideProgress();
        }

    }
}
