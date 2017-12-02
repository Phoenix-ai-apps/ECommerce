package com.demo.ecommerce.models.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 28/11/17.
 */

public class Tax implements Parcelable {


    private String name;

    private String value;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", value = "+value+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public Tax() {
    }

    protected Tax(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Tax> CREATOR = new Creator<Tax>() {
        @Override
        public Tax createFromParcel(Parcel source) {
            return new Tax(source);
        }

        @Override
        public Tax[] newArray(int size) {
            return new Tax[size];
        }
    };
}
