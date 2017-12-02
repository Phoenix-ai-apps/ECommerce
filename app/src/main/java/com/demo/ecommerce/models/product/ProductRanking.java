package com.demo.ecommerce.models.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 29/11/17.
 */

public class ProductRanking implements Parcelable {

    private int id;
    private long view_count;
    private long order_count;
    private long shares;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getView_count() {
        return view_count;
    }

    public void setView_count(long view_count) {
        this.view_count = view_count;
    }

    public long getOrder_count() {
        return order_count;
    }

    public void setOrder_count(long order_count) {
        this.order_count = order_count;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.view_count);
        dest.writeLong(this.order_count);
        dest.writeLong(this.shares);
    }

    public ProductRanking() {
    }

    protected ProductRanking(Parcel in) {
        this.id = in.readInt();
        this.view_count = in.readLong();
        this.order_count = in.readLong();
        this.shares = in.readLong();
    }

    public static final Parcelable.Creator<ProductRanking> CREATOR = new Parcelable.Creator<ProductRanking>() {
        @Override
        public ProductRanking createFromParcel(Parcel source) {
            return new ProductRanking(source);
        }

        @Override
        public ProductRanking[] newArray(int size) {
            return new ProductRanking[size];
        }
    };
}
