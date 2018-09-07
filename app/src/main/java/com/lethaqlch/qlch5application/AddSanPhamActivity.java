package com.lethaqlch.qlch5application;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class AddSanPhamActivity extends AppCompatActivity {
    private EditText edtAddTenSP, edtAddMaSP, edtAddGiaSP, edtAddSoLuongSP;
    private ImageButton imgBtnAddHinhSP;
    private Button buttonAddSP, btnHuyAddSP;
    private Dialog dialogHinhSP;
    private ImageView imageViewLap, imageViewPhone, imageViewTaplet;
    private int idHinhChoose, maSpEnd;
    private ArrayList<SanPham> arrayListsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_san_pham);

        maSpEnd = 1;
        arrayListsp = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            maSpEnd = getIntent().getExtras().getInt("maSpEnd");
            arrayListsp = getIntent().getParcelableArrayListExtra("listSP");
        }


        init();

        edtAddMaSP.setText("SP" + (maSpEnd + 1));
        edtAddMaSP.setEnabled(false);

        imgBtnAddHinhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        buttonAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtAddTenSP.getText()) || TextUtils.isEmpty(edtAddMaSP.getText()) || TextUtils.isEmpty(edtAddGiaSP.getText()) || idHinhChoose == 0) {
                    Toast.makeText(getApplicationContext(), "Vui long dien du thong tin", Toast.LENGTH_SHORT).show();

                } else {

                    if (!checkTen()) {
                        Toast.makeText(getApplicationContext(), "Tên sản phẩm này đã tồn tại", Toast.LENGTH_SHORT).show();
                        edtAddTenSP.setText("");
                    } else {
                        SanPham sanPham = new SanPham(edtAddMaSP.getText().toString(), edtAddTenSP.getText().toString(), Integer.parseInt(edtAddGiaSP.getText().toString()), idHinhChoose, Integer.parseInt(edtAddSoLuongSP.getText().toString()));
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                        myRef.child("SanPham").push().setValue(sanPham, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Toast.makeText(getApplicationContext(), "Them san pham thanh cong", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Them san pham that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    }


                }
            }


        });
        btnHuyAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        edtAddMaSP = findViewById(R.id.edtAddMaSP);
        edtAddTenSP = findViewById(R.id.edtAddTenSP);
        edtAddGiaSP = findViewById(R.id.edtAddGiaSP);
        imgBtnAddHinhSP = findViewById(R.id.imageBtnAddHinhSP);
        buttonAddSP = findViewById(R.id.buttonAddSP);
        edtAddSoLuongSP = findViewById(R.id.edtAddSoLuongSP);
        btnHuyAddSP = findViewById(R.id.btnHuyAddSP);
        idHinhChoose = 0;
    }

    private void showDialog() {
        dialogHinhSP = new Dialog(AddSanPhamActivity.this);
        dialogHinhSP.setContentView(R.layout.dialog_hinhsp);
        dialogHinhSP.show();

        imageViewLap = dialogHinhSP.findViewById(R.id.imageViewLap);
        imageViewPhone = dialogHinhSP.findViewById(R.id.imageViewPhone);
        imageViewTaplet = dialogHinhSP.findViewById(R.id.imageViewTaplet);

        imageViewTaplet.setOnClickListener(chooseImage);
        imageViewPhone.setOnClickListener(chooseImage);
        imageViewLap.setOnClickListener(chooseImage);


    }

    View.OnClickListener chooseImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.imageViewLap:
                    imgBtnAddHinhSP.setImageResource(R.drawable.laptop);
                    idHinhChoose = R.drawable.laptop;
                    dialogHinhSP.cancel();
                    break;
                case R.id.imageViewPhone:
                    imgBtnAddHinhSP.setImageResource(R.drawable.phone);
                    idHinhChoose = R.drawable.phone;
                    dialogHinhSP.cancel();
                    break;
                case R.id.imageViewTaplet:
                    imgBtnAddHinhSP.setImageResource(R.drawable.taplet);
                    idHinhChoose = R.drawable.taplet;
                    dialogHinhSP.cancel();
                    break;
            }
        }
    };


    public boolean checkTen() {
        boolean result = true;
        Iterator<SanPham> iterator = arrayListsp.iterator();
        while (iterator.hasNext()) {
            SanPham sanPham = iterator.next();
            if (edtAddTenSP.getText().toString().equals(sanPham.getTenSp())) {
                result = false;
            }
        }
        return result;
    }
}
