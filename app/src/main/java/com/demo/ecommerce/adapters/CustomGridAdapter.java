package com.demo.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.ecommerce.R;

/**
 * Created by root on 30/11/17.
 */

public class CustomGridAdapter extends BaseAdapter {


    private Context mContext;
    private final int[] imageId;

    public CustomGridAdapter(Context c , int[] imageId ) {
        mContext = c;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {

        return imageId.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_layout, null);
            ImageView gridImage = (ImageView)grid.findViewById(R.id.grid_image);

            gridImage.setImageResource(imageId[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
