package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {
    private ListView lvDonHangChuaConfirm;
    private ArrayList<DonHangItem> donHangItemArrayListChuaConfirm;
    private AdapterListDonHang adapterListDonHangChuaConfirm;
    private ArrayList<String> keyDHChuacof;
    private Button btnToDonHanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        init();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DonHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHangItem donHang = dataSnapshot.getValue(DonHangItem.class);
                if (donHang.getMaNV().equals("null")) {
                    keyDHChuacof.add(dataSnapshot.getKey());
                    donHangItemArrayListChuaConfirm.add(donHang);
                    adapterListDonHangChuaConfirm.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHangItem donHang = dataSnapshot.getValue(DonHangItem.class);
                String keyChange = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < keyDHChuacof.size(); i++) {
                    if (keyChange.equals(keyDHChuacof.get(i))) {
                        index = i;
                    }
                }
                if (donHang.getMaNV().equals("null")) {



                    donHangItemArrayListChuaConfirm.set(index, donHang);
                    adapterListDonHangChuaConfirm.notifyDataSetChanged();
                }else {
                    keyDHChuacof.remove(index);
                    donHangItemArrayListChuaConfirm.remove(index);
                    adapterListDonHangChuaConfirm.notifyDataSetChanged();
                }
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

        adapterListDonHangChuaConfirm = new AdapterListDonHang(ManagerActivity.this, R.layout.item_don_hang, donHangItemArrayListChuaConfirm);
        lvDonHangChuaConfirm.setAdapter(adapterListDonHangChuaConfirm);


        lvDonHangChuaConfirm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CTDonHangActivity.class);
                intent.putExtra("DonHang", donHangItemArrayListChuaConfirm.get(position));
                intent.putExtra("mkey",keyDHChuacof.get(position));
                startActivity(intent);

            }
        });


        btnToDonHanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QlDonHangedActivity.class);
                startActivity(intent);

            }
        });
    }

    private void init() {
        donHangItemArrayListChuaConfirm = new ArrayList<>();
        lvDonHangChuaConfirm = findViewById(R.id.lvDonHangChuaConfirm);
        keyDHChuacof = new ArrayList<>();
        btnToDonHanged = findViewById(R.id.btnToDonHanged);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
