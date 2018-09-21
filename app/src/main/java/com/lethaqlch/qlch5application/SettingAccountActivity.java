package com.lethaqlch.qlch5application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TaiKhoan taiKhoan;
    private EditText editTextSTIDACC, editTextSTEmail, editTextSTPass,editTextSTDiaChi;
    private CheckBox checkBoxSTQuyen;
    private Button btnUpdate, btnCancel;
    private String mkey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);


        init();

        taiKhoan = new TaiKhoan();
        mkey = new String();
        if (getIntent() != null) {
            taiKhoan = getIntent().getParcelableExtra("taikhoan");
            mkey = getIntent().getExtras().getString("key");
        }

        editTextSTIDACC.setText(taiKhoan.getiD());
        editTextSTEmail.setText(taiKhoan.getEmail());
        editTextSTPass.setText(taiKhoan.getPass());
        editTextSTDiaChi.setText(taiKhoan.getDiaChi());
        editTextSTIDACC.setEnabled(false);
        switch (taiKhoan.getQuyen()) {
            case 1:
                checkBoxSTQuyen.setChecked(false);
                break;
            case 2:
                checkBoxSTQuyen.setChecked(true);
                break;
        }

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    private void init() {
        editTextSTIDACC = findViewById(R.id.editTextSTIDACC);
        editTextSTEmail = findViewById(R.id.editTextSTEmail);
        editTextSTPass = findViewById(R.id.editTextSTPass);
        checkBoxSTQuyen = findViewById(R.id.checkBoxSTQuyen);
        editTextSTDiaChi = findViewById(R.id.editTextSTDiaChi);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                int checkBoxchecked = 1;
                if (checkBoxSTQuyen.isChecked()) {
                    checkBoxchecked = 2;
                }
                taiKhoan = new TaiKhoan(editTextSTIDACC.getText().toString(), editTextSTPass.getText().toString(), editTextSTEmail.getText().toString(),editTextSTDiaChi.getText().toString(), checkBoxchecked);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("TaiKhoan").child(mkey).setValue(taiKhoan, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(getApplicationContext(), "Update thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Update thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                Intent intent = new Intent(getApplicationContext(), QLTaiKhoanActivity.class);
//                startActivity(intent);
                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
