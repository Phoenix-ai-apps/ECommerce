package com.demo.ecommerce;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.demo.ecommerce.adapters.CustomGridAdapter;
import com.demo.ecommerce.presentation.gridView.GridViewFragmentContractor;
import com.demo.ecommerce.presentation.gridView.GridViewFragmentPresenter;

import butterknife.BindView;

public class GridFragment extends BaseFragment<GridViewFragmentPresenter> implements GridViewFragmentContractor.View {

    private Activity mActivity;

    @BindView(R.id.gridView)               GridView gridView;

    //testing purpose

    int[] imageId = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher

    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initResources();

    }

    private void initResources() {

        CustomGridAdapter customGridAdapter = new CustomGridAdapter(mActivity,imageId);
        gridView.setAdapter(customGridAdapter);
    }

    @Override
    protected GridViewFragmentPresenter getPresenter() {
        return new GridViewFragmentPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_grid;
    }
}
