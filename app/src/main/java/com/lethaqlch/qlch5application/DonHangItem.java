package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class DonHangItem implements Parcelable {
    private String maDH,iDKhach,maNV,ngayDat,gioDat;
    private int tongTien;

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getiDKhach() {
        return iDKhach;
    }

    public void setiDKhach(String iDKhach) {
        this.iDKhach = iDKhach;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getGioDat() {
        return gioDat;
    }

    public void setGioDat(String gioDat) {
        this.gioDat = gioDat;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public DonHangItem(String maDH, String iDKhach, String maNV, String ngayDat, String gioDat, int tongTien) {

        this.maDH = maDH;
        this.iDKhach = iDKhach;
        this.maNV = maNV;
        this.ngayDat = ngayDat;
        this.gioDat = gioDat;
        this.tongTien = tongTien;
    }

    public DonHangItem() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maDH);
        dest.writeString(this.iDKhach);
        dest.writeString(this.maNV);
        dest.writeString(this.ngayDat);
        dest.writeString(this.gioDat);
        dest.writeInt(this.tongTien);
    }

    protected DonHangItem(Parcel in) {
        this.maDH = in.readString();
        this.iDKhach = in.readString();
        this.maNV = in.readString();
        this.ngayDat = in.readString();
        this.gioDat = in.readString();
        this.tongTien = in.readInt();
    }

    public static final Creator<DonHangItem> CREATOR = new Creator<DonHangItem>() {
        @Override
        public DonHangItem createFromParcel(Parcel source) {
            return new DonHangItem(source);
        }

        @Override
        public DonHangItem[] newArray(int size) {
            return new DonHangItem[size];
        }
    };
}
