package com.lethaqlch.qlch5application;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SanPhamChiTietQuestActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgvCTSPQuest;
    private TextView tvMaCTSPQuest, tvGiaCTSPQuest, tvStatusCTSPQuest, tvTenCTSPQuest;
    private Button btnHuyCTSPQuest, btnAddToCartCTSPQuest, buttonXacNhan;
    private SanPham sanPham;
    private Cart cart;
    private Dialog dialog;
    private EditText editTextAddSoLuongSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_chi_tiet_quest);


        sanPham = new SanPham();
        if (getIntent() != null) {
            sanPham = getIntent().getParcelableExtra("SanPham");
        }
        init();

        imgvCTSPQuest.setImageResource(sanPham.getHinhSp());
        tvTenCTSPQuest.setText("Ten SP: " + sanPham.getTenSp());
        tvMaCTSPQuest.setText("Ma SP: " + sanPham.getMaSP());
        tvGiaCTSPQuest.setText("Gia SP: " + sanPham.getGiaSp());
        if (sanPham.getSoLuongTonKho() > 0) {
            tvStatusCTSPQuest.setText("Con hang");
        } else {
            tvStatusCTSPQuest.setText("Het hang");
            btnAddToCartCTSPQuest.setEnabled(false);
        }

        btnAddToCartCTSPQuest.setOnClickListener(this);
        btnHuyCTSPQuest.setOnClickListener(this);
    }

    private void init() {
        imgvCTSPQuest = findViewById(R.id.imgvCTSPQuest);
        tvMaCTSPQuest = findViewById(R.id.tvMaCTSPQuest);
        tvGiaCTSPQuest = findViewById(R.id.tvGiaCTSPQuest);
        tvStatusCTSPQuest = findViewById(R.id.tvStatusCTSPQuest);
        btnHuyCTSPQuest = findViewById(R.id.btnHuyCTSPQuest);
        btnAddToCartCTSPQuest = findViewById(R.id.btnAddToCartCTSPQuest);
        tvTenCTSPQuest = findViewById(R.id.tvTenCTSPQuest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHuyCTSPQuest:
               /* Intent intent1 = new Intent(SanPhamChiTietQuestActivity.this,MainActivity.class);
                setResult(Activity.RESULT_OK,intent1);*/
                finish();
                break;
            case R.id.btnAddToCartCTSPQuest:
                showDialog();
                break;
            case R.id.buttonXacNhan:
                if (!TextUtils.isEmpty(editTextAddSoLuongSP.getText())) {
                    if (Integer.parseInt(editTextAddSoLuongSP.getText().toString()) <= 0) {
                        Toast.makeText(getApplicationContext(), "so luong dat mua phai lon hon 0", Toast.LENGTH_SHORT).show();
                        editTextAddSoLuongSP.setText(1 + "");
                    } else {
                        if (Integer.parseInt(editTextAddSoLuongSP.getText().toString()) > sanPham.getSoLuongTonKho()) {
                            Toast.makeText(getApplicationContext(), "So luong hang trong kho chi con " + sanPham.getSoLuongTonKho(), Toast.LENGTH_SHORT).show();
                            editTextAddSoLuongSP.setText(sanPham.getSoLuongTonKho() + "");
                        } else {
                            sanPham.setSoLuongTonKho(sanPham.getSoLuongTonKho() - Integer.parseInt(editTextAddSoLuongSP.getText().toString()));
                            cart = new Cart(sanPham, Integer.parseInt(editTextAddSoLuongSP.getText().toString()));
                            Intent intent = new Intent(SanPhamChiTietQuestActivity.this, MainActivity.class);

                            intent.putExtra("cart", cart);
                            intent.putExtra("sanPhamUpdate", sanPham);
                            setResult(Activity.RESULT_OK, intent);

                            Toast.makeText(this, "San pham da duoc them vao gio hang", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();


                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "nhap vao so luong can mua", Toast.LENGTH_SHORT).show();

                }


                break;
        }
    }

    public void showDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_so_luong_sp);
        dialog.show();

        editTextAddSoLuongSP = dialog.findViewById(R.id.editTextAddSoLuongSP);
        editTextAddSoLuongSP.setText(1 + "");
        buttonXacNhan = dialog.findViewById(R.id.buttonXacNhan);

        buttonXacNhan.setOnClickListener(this);
    }
}
