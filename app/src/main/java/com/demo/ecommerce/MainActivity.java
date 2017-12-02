package com.demo.ecommerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.helper.ApplicationHelper;
import com.demo.ecommerce.presentation.mainActivity.MainActivityContractor;
import com.demo.ecommerce.presentation.mainActivity.MainActivityPresenter;
import com.demo.ecommerce.utils.ApplicationUtils;
import com.demo.ecommerce.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContractor.View {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.layout_progress)  LinearLayout layoutProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DependencyInjector.appComponent().inject(this);

        ButterKnife.bind(this);

        initResources();

    }

    private void initResources() {

        if(NetworkUtils.isConnected(this)){
            mPresenter.doServerCall();

        }else {
           showNoInternetDailog(this);
        }

    }



    public Dialog showNoInternetDailog(Context context) {

        Dialog alert = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertDlgView = inflater.inflate(R.layout.dialog_validation, null);

        TextView txtMessage = (TextView) alertDlgView.findViewById(R.id.txt_validation);
        TextView txtHeader = (TextView) alertDlgView.findViewById(R.id.txt_header);
        //   txtMessage.setTransformationMethod(null);

        Button btnClose = (Button) alertDlgView.findViewById(R.id.btn_close);
        btnClose.setTransformationMethod(null);
        btnClose.setText("Try Again");

        txtMessage.setText("Please connect to inetrnet.");
        txtHeader.setText("No Internert!");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                initResources();

            }
        });

        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(alertDlgView);
        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        return alert;
    }


    @Override
    public void showProgress() {

        layoutProgress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
       layoutProgress.setVisibility(View.GONE);
    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter(this);
    }


    @Override
    public void showErrorInView(String error) {

        ApplicationUtils.showToast(this,error);
    }

    @Override
    public void showVolleyException(String e) {

        ApplicationUtils.showToast(this,e);
    }


    @Override
    public void addHomeActivity() {

       // Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HomeActivity.class);
        finish();
        startActivity(intent);

    }

    @Override
    public ApplicationHelper getHelper() {
        return ApplicationHelper.getInstance();
    }
}
