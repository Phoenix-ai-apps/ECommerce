package com.demo.ecommerce;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.ecommerce.adapters.RecyclerViewAdapter;
import com.demo.ecommerce.presentation.recyclerView.RecyclerViewFragmentContractor;
import com.demo.ecommerce.presentation.recyclerView.RecyclerViewFragmentPresenter;

import butterknife.BindView;

public class RecyclerFragment extends BaseFragment<RecyclerViewFragmentPresenter> implements RecyclerViewFragmentContractor.View {


    private Activity mActivity;

    @BindView(R.id.recyclerView)                RecyclerView recyclerView;

    //testing purpose

    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
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

        initResource();

    }

    private void initResource() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mActivity,imageId,web);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected RecyclerViewFragmentPresenter getPresenter() {
        return new RecyclerViewFragmentPresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recycler;
    }
}
