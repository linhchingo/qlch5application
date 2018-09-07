package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QLNhanVienActivity extends AppCompatActivity implements Parcelable {
    private ListView lvNhanVien;
    private Button btnAdNV;
    private ArrayList<NhanVien> listNhanVien;
    private AdapterLvNhanVien adapterLvNhanVien;
    private ArrayList<String> mkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnhan_vien);

        init();
        lvNhanVien.setAdapter(adapterLvNhanVien);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("NhanVien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                mkey.add(dataSnapshot.getKey());
                listNhanVien.add(nhanVien);
                adapterLvNhanVien.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                String keyChange = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < mkey.size(); i++) {
                    if (keyChange.equals(mkey.get(i))) {
                        index = i;
                    }
                }
                listNhanVien.set(index,nhanVien);
                adapterLvNhanVien.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String keyRemove = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < mkey.size(); i++) {
                    if (keyRemove.equals(mkey.get(i))) {
                        index = i;
                    }
                }
                listNhanVien.remove(index);
                adapterLvNhanVien.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAdNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddNhanVienActivity.class);
                intent.putParcelableArrayListExtra("listNhanVien",listNhanVien);
                startActivity(intent);
            }
        });
    }

    private void init() {
        lvNhanVien = findViewById(R.id.lvNhanVien);
        btnAdNV = findViewById(R.id.btnAddNV);
        listNhanVien = new ArrayList<>();
        adapterLvNhanVien = new AdapterLvNhanVien(getApplicationContext(),R.layout.item_nhan_vien,listNhanVien);
        mkey = new ArrayList<>();

}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listNhanVien);
    }

    public QLNhanVienActivity() {
    }

    protected QLNhanVienActivity(Parcel in) {
        this.listNhanVien = in.createTypedArrayList(NhanVien.CREATOR);
    }

    public static final Parcelable.Creator<QLNhanVienActivity> CREATOR = new Parcelable.Creator<QLNhanVienActivity>() {
        @Override
        public QLNhanVienActivity createFromParcel(Parcel source) {
            return new QLNhanVienActivity(source);
        }

        @Override
        public QLNhanVienActivity[] newArray(int size) {
            return new QLNhanVienActivity[size];
        }
    };
}
