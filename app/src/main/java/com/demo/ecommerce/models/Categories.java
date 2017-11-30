package com.demo.ecommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class Categories implements Parcelable {

    private int id;
    private String name;
    private List<Products> products;
    private String[] child_categories;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public String[] getChild_categories() {
        return child_categories;
    }

    public void setChild_categories(String[] child_categories) {
        this.child_categories = child_categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.products);
        dest.writeStringArray(this.child_categories);
    }

    public Categories() {
    }

    protected Categories(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.products = in.createTypedArrayList(Products.CREATOR);
        this.child_categories = in.createStringArray();
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel source) {
            return new Categories(source);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}