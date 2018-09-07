package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class SanPham implements Parcelable {
    String maSP, tenSp;
    int hinhSp, giaSp, soLuongTonKho;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSp, int giaSp, int hinhSp, int soLuongTonKho) {
        this.maSP = maSP;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.hinhSp = hinhSp;
        this.soLuongTonKho = soLuongTonKho;

    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(int hinhSp) {
        this.hinhSp = hinhSp;
    }

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maSP);
        dest.writeString(this.tenSp);
        dest.writeInt(this.hinhSp);
        dest.writeInt(this.giaSp);
        dest.writeInt(this.soLuongTonKho);
    }

    protected SanPham(Parcel in) {
        this.maSP = in.readString();
        this.tenSp = in.readString();
        this.hinhSp = in.readInt();
        this.giaSp = in.readInt();
        this.soLuongTonKho = in.readInt();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel source) {
            return new SanPham(source);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };
}
