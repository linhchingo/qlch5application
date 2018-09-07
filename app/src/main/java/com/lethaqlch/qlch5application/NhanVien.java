package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class NhanVien implements Parcelable {
    String maNV,tenNV,emailNV;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String emailNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.emailNV = emailNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getEmailNV() {
        return emailNV;
    }

    public void setEmailNV(String emailNV) {
        this.emailNV = emailNV;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maNV);
        dest.writeString(this.tenNV);
        dest.writeString(this.emailNV);
    }

    protected NhanVien(Parcel in) {
        this.maNV = in.readString();
        this.tenNV = in.readString();
        this.emailNV = in.readString();
    }

    public static final Parcelable.Creator<NhanVien> CREATOR = new Parcelable.Creator<NhanVien>() {
        @Override
        public NhanVien createFromParcel(Parcel source) {
            return new NhanVien(source);
        }

        @Override
        public NhanVien[] newArray(int size) {
            return new NhanVien[size];
        }
    };
}
