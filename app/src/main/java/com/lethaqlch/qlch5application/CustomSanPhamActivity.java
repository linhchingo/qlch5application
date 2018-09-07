package com.lethaqlch.qlch5application;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.lethaqlch.qlch5application.mClass.Firebase;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomSanPhamActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgvCustomHinhSP;
    private EditText edtCustomMaSP, edtCustomGiaSP, edtCustomSLTonKhoSP, edtCustomTenSP;
    private ImageButton imgbCustomDelSP, imgbCustomUpdateSP;
    private Button btnCustomCancelSP;
    private ArrayList<SanPham> arrayListSP;
    private SanPham sanPham;
    private String stringKey, stringEdtTen;
    private int idHinhChoose;
    private Dialog dialogHinhSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_san_pham);

        init();

        if (getIntent() != null) {
            arrayListSP = getIntent().getParcelableArrayListExtra("listSP");
            sanPham = getIntent().getParcelableExtra("spSelected");
            stringKey = getIntent().getExtras().getString("mkey");
        }

        idHinhChoose = sanPham.getHinhSp();
        imgvCustomHinhSP.setImageResource(sanPham.getHinhSp());
        edtCustomMaSP.setText(sanPham.getMaSP());
        edtCustomMaSP.setEnabled(false);
        edtCustomGiaSP.setText(String.valueOf(sanPham.getGiaSp()));
        edtCustomSLTonKhoSP.setText(String.valueOf(sanPham.getSoLuongTonKho()));
        edtCustomTenSP.setText(String.valueOf(sanPham.getTenSp()));

        stringEdtTen = edtCustomTenSP.getText().toString();

        imgbCustomDelSP.setOnClickListener(this);
        imgbCustomUpdateSP.setOnClickListener(this);
        btnCustomCancelSP.setOnClickListener(this);
        imgvCustomHinhSP.setOnClickListener(this);



    }

    private void init() {
        imgvCustomHinhSP = findViewById(R.id.imgvCustomHinhSP);
        edtCustomMaSP = findViewById(R.id.edtCustomMaSP);
        edtCustomGiaSP = findViewById(R.id.edtCustomGiaSP);
        edtCustomSLTonKhoSP = findViewById(R.id.edtCustomSLTonKhoSP);
        edtCustomTenSP = findViewById(R.id.edtCustomTenSP);
        imgbCustomDelSP = findViewById(R.id.imgbCustomDelSP);
        btnCustomCancelSP = findViewById(R.id.btnCustomCancelSP);
        imgbCustomUpdateSP = findViewById(R.id.imgbCustomUpdateSP);
        arrayListSP = new ArrayList<>();
        sanPham = new SanPham();
        stringKey = new String();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbCustomDelSP:
                showDialogDel(stringKey,sanPham);

                break;
            case R.id.imgbCustomUpdateSP:
                if (TextUtils.isEmpty(edtCustomGiaSP.getText()) || TextUtils.isEmpty(edtCustomTenSP.getText()) || TextUtils.isEmpty(edtCustomSLTonKhoSP.getText())) {
                    Toast.makeText(getApplicationContext(), "Vui long dien du thong tin", Toast.LENGTH_SHORT).show();

                } else {
                    if (!checkTen() && !edtCustomTenSP.getText().toString().equals(stringEdtTen)) {
                        Toast.makeText(getApplicationContext(), "Tên sản phẩm này trùng với sản phẩm khác", Toast.LENGTH_SHORT).show();
                        edtCustomTenSP.setText("");
                    } else {
                        SanPham sanPham = new SanPham(edtCustomMaSP.getText().toString(), edtCustomTenSP.getText().toString(), Integer.parseInt(edtCustomGiaSP.getText().toString()), idHinhChoose,
                                Integer.parseInt(edtCustomSLTonKhoSP.getText().toString()));

                        Firebase.Update(this, stringKey, sanPham, "SanPham");
                        finish();
                    }

                }
                break;
            case R.id.btnCustomCancelSP:
                finish();
                break;
            case R.id.imageViewLap:
                imgvCustomHinhSP.setImageResource(R.drawable.laptop);
                idHinhChoose = R.drawable.laptop;
                dialogHinhSP.cancel();
                break;
            case R.id.imageViewPhone:
                imgvCustomHinhSP.setImageResource(R.drawable.phone);
                idHinhChoose = R.drawable.phone;
                dialogHinhSP.cancel();
                break;
            case R.id.imageViewTaplet:
                imgvCustomHinhSP.setImageResource(R.drawable.taplet);
                idHinhChoose = R.drawable.taplet;
                dialogHinhSP.cancel();
                break;
            case R.id.imgvCustomHinhSP:
                showDialog();
                break;
        }


    }

    private void showDialog() {
        dialogHinhSP = new Dialog(this);
        dialogHinhSP.setContentView(R.layout.dialog_hinhsp);
        dialogHinhSP.show();

        ImageView imageViewLap = dialogHinhSP.findViewById(R.id.imageViewLap);
        ImageView imageViewPhone = dialogHinhSP.findViewById(R.id.imageViewPhone);
        ImageView imageViewTaplet = dialogHinhSP.findViewById(R.id.imageViewTaplet);

        imageViewTaplet.setOnClickListener(this);
        imageViewPhone.setOnClickListener(this);
        imageViewLap.setOnClickListener(this);


    }

    private void showDialogDel(final String mkey, final SanPham sanPham){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete").setIcon(android.R.drawable.ic_menu_delete).setMessage("Ban co chac muon xoa san pham '"+sanPham.getTenSp()+"' nay khong?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("SanPham").child(mkey).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(getApplicationContext(), "Xoa san pham "+sanPham.getTenSp()+" thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Xoa san pham "+sanPham.getTenSp()+" that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);

    }

    public boolean checkTen() {
        boolean result = true;
        Iterator<SanPham> iterator = arrayListSP.iterator();
        while (iterator.hasNext()) {
            SanPham sanPham = iterator.next();
            if (edtCustomTenSP.getText().toString().equals(sanPham.getTenSp())) {
                result = false;
            }
        }
        return result;
    }
}
