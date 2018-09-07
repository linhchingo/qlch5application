package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lethaqlch.qlch5application.mClass.WriteReadFireBase;

import java.util.ArrayList;
import java.util.Iterator;

public class CreateAccoutActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtCreateID, edtCreatePass, edtCreateEmail;
    private CheckBox checkBoxAdmin;
    private Button buttonCreateAccount, buttonBackLogin;
    private WriteReadFireBase writeReadFireBase;
    private ArrayList<String> idArrList, emailArrList;
    private ArrayList<Object> readLineArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accout);

        anhXa();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        myRef.child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                idArrList.add(taiKhoan.getiD());
                emailArrList.add(taiKhoan.getEmail());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        buttonCreateAccount.setOnClickListener(this);
        buttonBackLogin.setOnClickListener(this);
    }

    private void anhXa() {
        edtCreateID = findViewById(R.id.edtCreateID);
        edtCreatePass = findViewById(R.id.edtCreatePass);
        checkBoxAdmin = findViewById(R.id.checkBoxAdmin);
        buttonCreateAccount = findViewById(R.id.btnCreateAccout);
        buttonBackLogin = findViewById(R.id.btnBackLogin);
        edtCreateEmail = findViewById(R.id.edtCreateEmail);

        idArrList = new ArrayList<>();
        emailArrList = new ArrayList<>();
        readLineArr = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCreateAccout) {
            if (TextUtils.isEmpty(edtCreateID.getText()) || TextUtils.isEmpty(edtCreatePass.getText())) {
                Toast.makeText(CreateAccoutActivity.this, "Vui Long Dien Du Thong Tin", Toast.LENGTH_SHORT).show();
            } else {
                Iterator iterator = readLineArr.iterator();
                while (iterator.hasNext()) {
                    TaiKhoan taiKhoan = (TaiKhoan) iterator.next();
                    idArrList.add(taiKhoan.getiD());
                    emailArrList.add(taiKhoan.getEmail());
                }
                if (!ktID(edtCreateID.getText().toString())) {
                    Toast.makeText(CreateAccoutActivity.this, "Ten tai khoan nay da ton tai", Toast.LENGTH_LONG).show();
                    edtCreateID.setText("");

                } else if (!ktEmail(edtCreateEmail.getText().toString())) {
                    Toast.makeText(CreateAccoutActivity.this, "Email nay da co nguoi su dung", Toast.LENGTH_LONG).show();
                    edtCreateEmail.setText("");
                } else {
                    int index = 1;
                    if (checkBoxAdmin.isChecked()) {
                        index = 2;
                    }

                    writeReadFireBase = new WriteReadFireBase(CreateAccoutActivity.this);
                    TaiKhoan taiKhoan = new TaiKhoan(edtCreateID.getText().toString(), edtCreatePass.getText().toString(), edtCreateEmail.getText().toString(), index);
                    writeReadFireBase.writeData("TaiKhoan", taiKhoan);
                    backLogin();

                }


            }
        } else if (v.getId() == R.id.btnBackLogin) {
            backLogin();
        }


    }

    public void backLogin() {
        Intent intent = new Intent(CreateAccoutActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean ktID(String iD) {
        boolean result = true;


        Iterator<String> idIterator = idArrList.iterator();
        while (idIterator.hasNext()) {

            if (iD.equals(idIterator.next()))
                result = false;

        }
        return result;
    }

    public boolean ktEmail(String eMail) {
        boolean result = true;
        Iterator<String> emailIterator = emailArrList.iterator();
        while (emailIterator.hasNext()) {
            if (eMail.equals(emailIterator.next())) {
                return false;
            }
        }
        return result;
    }
}




