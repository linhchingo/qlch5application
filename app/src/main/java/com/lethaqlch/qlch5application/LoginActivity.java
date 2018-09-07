package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Parcelable {
    private EditText editTextID, editTextPass;
    private Button buttonLogin, buttonCreate;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private ArrayList<TaiKhoan> taiKhoanArrayList;
    private TaiKhoan taiKhoanLog;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        anhXa();


        myRef.child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                taiKhoanArrayList.add(taiKhoan);
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

        buttonCreate.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

    }

    private void anhXa() {
        editTextID = findViewById(R.id.edtID);
        editTextPass = findViewById(R.id.edtPass);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonCreate = findViewById(R.id.btnCreate);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanLog = new TaiKhoan();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:
                Intent intent = new Intent(LoginActivity.this, CreateAccoutActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogin:
                if (TextUtils.isEmpty(editTextID.getText()) || TextUtils.isEmpty(editTextPass.getText())) {
                    Toast.makeText(LoginActivity.this, "Dien Day du thong tin", Toast.LENGTH_SHORT).show();
                } else {
                    switch (ktAccout(editTextID.getText().toString(), editTextPass.getText().toString())) {
                        case 0:
                            Toast.makeText(LoginActivity.this, "Sai Id hoac Password", Toast.LENGTH_SHORT).show();
                            editTextID.setText("");
                            editTextPass.setText("");
                            break;
                        case 1:
                            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                            intent1.putExtra("AccLog",taiKhoanLog);
                            startActivity(intent1);
                            finish();
                            break;
                        case 2:
                            Intent intent2 = new Intent(LoginActivity.this, MainAdminActivity.class);
                            startActivity(intent2);
                            finish();
                            break;
                    }


                }

                break;
        }
    }


    public int ktAccout(String iD, String pass) {
        int result = 0;


        Iterator<TaiKhoan> iterator = taiKhoanArrayList.iterator();
        while (iterator.hasNext()) {
            TaiKhoan taiKhoan = iterator.next();
            if (iD.equals(taiKhoan.getiD()) && pass.equals(taiKhoan.getPass())) {
                switch (taiKhoan.getQuyen()) {
                    case 1:
                        result = 1;
                        break;
                    case 2:
                        result = 2;
                        break;
                }
                taiKhoanLog = taiKhoan;
            }
        }

        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.taiKhoanLog, flags);
    }

    public LoginActivity() {
    }

    protected LoginActivity(Parcel in) {
        this.taiKhoanLog = in.readParcelable(TaiKhoan.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginActivity> CREATOR = new Parcelable.Creator<LoginActivity>() {
        @Override
        public LoginActivity createFromParcel(Parcel source) {
            return new LoginActivity(source);
        }

        @Override
        public LoginActivity[] newArray(int size) {
            return new LoginActivity[size];
        }
    };
}
