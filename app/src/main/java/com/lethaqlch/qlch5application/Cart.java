package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private SanPham sanPham;
    private int soLuongOrder;

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuongOrder() {
        return soLuongOrder;
    }

    public void setSoLuongOrder(int soLuongOrder) {
        this.soLuongOrder = soLuongOrder;
    }

    public Cart() {

    }

    public Cart(SanPham sanPham, int soLuongOrder) {

        this.sanPham = sanPham;
        this.soLuongOrder = soLuongOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sanPham, flags);
        dest.writeInt(this.soLuongOrder);
    }

    protected Cart(Parcel in) {
        this.sanPham = in.readParcelable(SanPham.class.getClassLoader());
        this.soLuongOrder = in.readInt();
    }

    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
