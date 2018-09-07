package com.lethaqlch.qlch5application;

import android.os.Parcel;
import android.os.Parcelable;

public class TaiKhoan extends Object implements Parcelable {
    String iD, pass, email;
    int quyen;

    public TaiKhoan() {
    }

    public TaiKhoan(String iD, String pass, String email, int quyen) {
        this.iD = iD;
        this.pass = pass;
        this.email = email;
        this.quyen = quyen;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iD);
        dest.writeString(this.pass);
        dest.writeString(this.email);
        dest.writeInt(this.quyen);
    }

    protected TaiKhoan(Parcel in) {
        this.iD = in.readString();
        this.pass = in.readString();
        this.email = in.readString();
        this.quyen = in.readInt();
    }

    public static final Parcelable.Creator<TaiKhoan> CREATOR = new Parcelable.Creator<TaiKhoan>() {
        @Override
        public TaiKhoan createFromParcel(Parcel source) {
            return new TaiKhoan(source);
        }

        @Override
        public TaiKhoan[] newArray(int size) {
            return new TaiKhoan[size];
        }
    };
}
