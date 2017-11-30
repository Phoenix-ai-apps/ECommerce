package com.demo.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ecommerce.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 30/11/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private final int[] imageId;
    private final String[] name;

    public RecyclerViewAdapter(Context mContext, int[] imageId, String[] name) {
        this.mContext = mContext;
        this.imageId = imageId;
        this.name = name;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_image_view)             ImageView cardImage;
        @BindView(R.id.tvShortDesc)                 TextView tvDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_single_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.cardImage.setImageResource(imageId[position]);

            itemViewHolder.tvDesc.setText(name[position]);

        }

    }

    @Override
    public int getItemCount() {
        return imageId.length;
    }
}
