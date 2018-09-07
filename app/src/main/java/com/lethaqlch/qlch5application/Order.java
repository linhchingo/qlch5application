package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    String maDH,maSP;
    int soluongOrder;

    public Order() {
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoluongOrder() {
        return soluongOrder;
    }

    public void setSoluongOrder(int soluongOrder) {
        this.soluongOrder = soluongOrder;
    }

    public Order(String maDH, String maSP, int soluongOrder) {

        this.maDH = maDH;
        this.maSP = maSP;
        this.soluongOrder = soluongOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maDH);
        dest.writeString(this.maSP);
        dest.writeInt(this.soluongOrder);
    }

    protected Order(Parcel in) {
        this.maDH = in.readString();
        this.maSP = in.readString();
        this.soluongOrder = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
