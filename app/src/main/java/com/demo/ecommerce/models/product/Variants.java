package com.demo.ecommerce.models.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 28/11/17.
 */

public class Variants implements Parcelable {


    private String id;
    private String color;
    private String size;
    private String price;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", price = "+price+", color = "+color+", size = "+size+"]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.price);
        dest.writeString(this.color);
        dest.writeString(this.size);
    }

    public Variants() {
    }

    protected Variants(Parcel in) {
        this.id = in.readString();
        this.price = in.readString();
        this.color = in.readString();
        this.size = in.readString();
    }

    public static final Creator<Variants> CREATOR = new Creator<Variants>() {
        @Override
        public Variants createFromParcel(Parcel source) {
            return new Variants(source);
        }

        @Override
        public Variants[] newArray(int size) {
            return new Variants[size];
        }
    };
}
