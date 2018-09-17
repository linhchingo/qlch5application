package com.lethaqlch.qlch5application;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QlDonHangedActivity extends AppCompatActivity {
    private ListView lvDonHanged;
    private AdapterDonHanged adapterDonHanged;
    private ArrayList<DonHangItem> listDonHanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_don_hanged);

        init();
        readData();



    }

    private void init() {
        listDonHanged = new ArrayList<>();
        lvDonHanged = findViewById(R.id.lvDonHanged);
        adapterDonHanged = new AdapterDonHanged(getApplicationContext(),R.layout.item_donhhanged,listDonHanged);
        lvDonHanged.setAdapter(adapterDonHanged);

    }

    private void readData(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DonHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHangItem donHang = dataSnapshot.getValue(DonHangItem.class);
                if (!donHang.getMaNV().equals("null")){
                    listDonHanged.add(donHang);
                    adapterDonHanged.notifyDataSetChanged();
                }
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



}
