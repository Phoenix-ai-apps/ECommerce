package com.demo.ecommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.demo.ecommerce.models.category.Categories;
import com.demo.ecommerce.models.product.Rankings;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class EcommerceModel implements Parcelable {

    private List<Categories> categories;
    private List<Rankings> rankings;



    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Rankings> getRankings() {
        return rankings;
    }

    public void setRankings(List<Rankings> rankings) {
        this.rankings = rankings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categories);
        dest.writeTypedList(this.rankings);
    }

    public EcommerceModel() {
    }

    protected EcommerceModel(Parcel in) {
        this.categories = in.createTypedArrayList(Categories.CREATOR);
        this.rankings = in.createTypedArrayList(Rankings.CREATOR);
    }

    public static final Creator<EcommerceModel> CREATOR = new Creator<EcommerceModel>() {
        @Override
        public EcommerceModel createFromParcel(Parcel source) {
            return new EcommerceModel(source);
        }

        @Override
        public EcommerceModel[] newArray(int size) {
            return new EcommerceModel[size];
        }
    };
}
