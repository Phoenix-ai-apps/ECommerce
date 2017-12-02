package com.demo.ecommerce.models.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class Products implements Parcelable {

    @SerializedName("id")
    private int            rankingID;
    private int            categoryID;
    private String         name;
    private String         date_added;
    private List<Variants> variants;
    private Tax            tax;
    private long           viewId;
    private long           shareId;
    private long           orderId;




    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getRankingID() {
        return rankingID;
    }

    public void setRankingID(int rankingID) {
        this.rankingID = rankingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public List<Variants> getVariants() {
        return variants;
    }

    public void setVariants(List<Variants> variants) {
        this.variants = variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public long getViewId() {
        return viewId;
    }

    public void setViewId(long viewId) {
        this.viewId = viewId;
    }

    public long getShareId() {
        return shareId;
    }

    public void setShareId(long shareId) {
        this.shareId = shareId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Products() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rankingID);
        dest.writeInt(this.categoryID);
        dest.writeString(this.name);
        dest.writeString(this.date_added);
        dest.writeTypedList(this.variants);
        dest.writeParcelable(this.tax, flags);
        dest.writeLong(this.viewId);
        dest.writeLong(this.shareId);
        dest.writeLong(this.orderId);
    }

    protected Products(Parcel in) {
        this.rankingID = in.readInt();
        this.categoryID = in.readInt();
        this.name = in.readString();
        this.date_added = in.readString();
        this.variants = in.createTypedArrayList(Variants.CREATOR);
        this.tax = in.readParcelable(Tax.class.getClassLoader());
        this.viewId = in.readLong();
        this.shareId = in.readLong();
        this.orderId = in.readLong();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
