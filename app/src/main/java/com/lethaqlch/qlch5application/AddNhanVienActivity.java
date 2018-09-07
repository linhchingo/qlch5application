package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddNhanVienActivity extends AppCompatActivity {
    private EditText edtAddEmailNV,edtAddMaNV,edtAddTenNV;
    private Button btnAddNhanVien;
    private ArrayList<NhanVien> listNhanVien;
    private int indexMaNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);


        listNhanVien = new ArrayList<>();
        if (getIntent() != null){
            listNhanVien = getIntent().getParcelableArrayListExtra("listNhanVien");
        }

        init();
        edtAddMaNV.setEnabled(false);
        if (listNhanVien.size() == 0){
            edtAddMaNV.setText("NV"+indexMaNV);
        }else {
            edtAddMaNV.setText("NV"+(Integer.parseInt(listNhanVien.get(listNhanVien.size()-1).getMaNV().substring(2))+1));
        }

        btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtAddTenNV.getText())|| TextUtils.isEmpty(edtAddEmailNV.getText())){
                    Toast.makeText(getApplicationContext(),"Vui long dien day du thong tin",Toast.LENGTH_SHORT).show();
                }else {
                    if (!checkEmail(edtAddEmailNV.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Email nay da co nhan vien khac su dung",Toast.LENGTH_SHORT).show();
                        edtAddEmailNV.setText("");
                    }else {
                        NhanVien nhanVien = new NhanVien(edtAddMaNV.getText().toString(),edtAddTenNV.getText().toString(),edtAddEmailNV.getText().toString());
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                        myRef.child("NhanVien").push().setValue(nhanVien, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Toast.makeText(getApplicationContext(), "Them nhan vien moi thanh cong", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Them nhan vien moi that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    }

                }
            }
        });
    }

    private void init() {
        edtAddEmailNV = findViewById(R.id.edtAddEmailNV);
        edtAddMaNV = findViewById(R.id.edtAddMaNV);
        edtAddTenNV = findViewById(R.id.edtAddTenNV);
        btnAddNhanVien = findViewById(R.id.btnAddNhanVien);
        indexMaNV = 1;
    }

    public boolean checkEmail(String email){
        boolean flag = true;
        for (int i =0; i < listNhanVien.size();i++)
        {
            if (email.equals(listNhanVien.get(i).getEmailNV())){
                flag = false;
            }
        }
        return  flag;
    }
}
