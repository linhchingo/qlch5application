package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lethaqlch.qlch5application.base.BaseActivity;

public class MainAdminActivity extends BaseActivity implements View.OnClickListener {
    private Button btnQLTK, btnQLSP,btnQLNV,btnQLDH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        btnQLTK = findViewById(R.id.btnQLTK);
        btnQLSP = findViewById(R.id.btnQLSP);
        btnQLNV = findViewById(R.id.btnQLNV);
        btnQLDH = findViewById(R.id.btnQLDH);
        btnQLTK.setOnClickListener(this);
        btnQLSP.setOnClickListener(this);
        btnQLNV.setOnClickListener(this);
        btnQLDH.setOnClickListener(this);

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
        }


    }


}
