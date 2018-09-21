package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lethaqlch.qlch5application.base.BaseActivity;

import java.util.ArrayList;

public class MainAdminActivity extends BaseActivity implements View.OnClickListener, Parcelable {
    public static final Creator<MainAdminActivity> CREATOR = new Creator<MainAdminActivity>() {
        @Override
        public MainAdminActivity createFromParcel(Parcel source) {
            return new MainAdminActivity(source);
        }

        @Override
        public MainAdminActivity[] newArray(int size) {
            return new MainAdminActivity[size];
        }
    };
    private Button btnQLTK, btnQLSP, btnQLNV, btnQLDH, btnChart, btnChartLocation;
    private ArrayList<DonHangItem> donHangItemArrayList;
    private ArrayList<String> dateArr;
    private ArrayList<TaiKhoan> listTaiKhoan;

    public MainAdminActivity() {
    }


    protected MainAdminActivity(Parcel in) {

        this.btnChart = in.readParcelable(Button.class.getClassLoader());
        this.btnChartLocation = in.readParcelable(Button.class.getClassLoader());
        this.donHangItemArrayList = in.createTypedArrayList(DonHangItem.CREATOR);
        this.dateArr = in.createStringArrayList();
        this.listTaiKhoan = in.createTypedArrayList(TaiKhoan.CREATOR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        btnQLTK = findViewById(R.id.btnQLTK);
        btnQLSP = findViewById(R.id.btnQLSP);
        btnQLNV = findViewById(R.id.btnQLNV);
        btnQLDH = findViewById(R.id.btnQLDH);
        btnChart = findViewById(R.id.btnChart);
        btnChartLocation = findViewById(R.id.btnChartLocation);
        btnQLTK.setOnClickListener(this);
        btnQLSP.setOnClickListener(this);
        btnQLNV.setOnClickListener(this);
        btnQLDH.setOnClickListener(this);
        btnChart.setOnClickListener(this);
        btnChartLocation.setOnClickListener(this);

        donHangItemArrayList = new ArrayList<>();
        dateArr = new ArrayList<>();
        listTaiKhoan = new ArrayList<>();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DonHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHangItem donHangItem = dataSnapshot.getValue(DonHangItem.class);
                donHangItemArrayList.add(donHangItem);
                String[] mounthDH = donHangItem.getNgayDat().split("/");
                dateArr.add(mounthDH[1]);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                listTaiKhoan.add(taiKhoan);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnQLTK:
                Intent intent = new Intent(MainAdminActivity.this, QLTaiKhoanActivity.class);
                startActivity(intent);
                break;
            case R.id.btnQLSP:
                Intent intentQLSP = new Intent(MainAdminActivity.this, QLSanPhamActivity.class);
                startActivity(intentQLSP);
                break;
            case R.id.btnQLNV:
                Intent intentQLNV = new Intent(MainAdminActivity.this, QLNhanVienActivity.class);
                startActivity(intentQLNV);
                break;
            case R.id.btnQLDH:
                Intent intentQLDH = new Intent(MainAdminActivity.this, ManagerActivity.class);
                startActivity(intentQLDH);
                break;
            case R.id.btnChart:
                Intent intentChart = new Intent(MainAdminActivity.this, ChartActivity.class);
                intentChart.putParcelableArrayListExtra("listDonHang", donHangItemArrayList);
                intentChart.putStringArrayListExtra("monthDH", dateArr);
                startActivity(intentChart);
                break;
            case R.id.btnChartLocation:
                Intent intentChartLocation = new Intent(MainAdminActivity.this, ChartLocationActivity.class);
                intentChartLocation.putParcelableArrayListExtra("listAccount", listTaiKhoan);
                startActivity(intentChartLocation);
                break;
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.donHangItemArrayList);
        dest.writeStringList(this.dateArr);
        dest.writeTypedList(this.listTaiKhoan);
    }
}
