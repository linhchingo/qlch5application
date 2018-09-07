package com.lethaqlch.qlch5application;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishBuyActivity extends AppCompatActivity {
    private Button buttonBackBuy;
    private TaiKhoan taiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_buy);


        taiKhoan = new TaiKhoan();
        if (getIntent() != null){
            taiKhoan = getIntent().getParcelableExtra("tai khoan");
        }
        buttonBackBuy = findViewById(R.id.buttonBackBuy);
        buttonBackBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("AccLog",taiKhoan);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
            }
        });
    }
}
