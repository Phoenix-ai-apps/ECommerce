package com.demo.ecommerce.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 28/11/17.
 */

public class GlobalResponse implements Parcelable {

    private boolean result;
    private String message;
    private String messageCode;


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.result ? (byte) 1 : (byte) 0);
        dest.writeString(this.message);
        dest.writeString(this.messageCode);
    }

    public GlobalResponse() {
    }

    protected GlobalResponse(Parcel in) {
        this.result = in.readByte() != 0;
        this.message = in.readString();
        this.messageCode = in.readString();
    }

    public static final Creator<GlobalResponse> CREATOR = new Creator<GlobalResponse>() {
        @Override
        public GlobalResponse createFromParcel(Parcel source) {
            return new GlobalResponse(source);
        }

        @Override
        public GlobalResponse[] newArray(int size) {
            return new GlobalResponse[size];
        }
    };

}
