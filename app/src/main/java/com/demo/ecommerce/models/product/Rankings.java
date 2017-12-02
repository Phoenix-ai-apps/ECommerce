package com.demo.ecommerce.models.product;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class Rankings implements Parcelable {


    private String ranking;
    private List<ProductRanking> products;



    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<ProductRanking> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRanking> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ranking);
        dest.writeTypedList(this.products);
    }

    public Rankings() {
    }

    protected Rankings(Parcel in) {
        this.ranking = in.readString();
        this.products = in.createTypedArrayList(ProductRanking.CREATOR);
    }

    public static final Creator<Rankings> CREATOR = new Creator<Rankings>() {
        @Override
        public Rankings createFromParcel(Parcel source) {
            return new Rankings(source);
        }

        @Override
        public Rankings[] newArray(int size) {
            return new Rankings[size];
        }
    };
}
