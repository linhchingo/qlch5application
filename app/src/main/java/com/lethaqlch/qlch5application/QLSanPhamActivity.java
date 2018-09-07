package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class QLSanPhamActivity extends AppCompatActivity implements Parcelable {
    private ListView lvSPQL;
    private Button btnToAddSP;
    private ArrayList<SanPham> arrayListSanPham;
    private ArrayList<String> mkey;
    private AdapterListSP adapterListSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsan_pham);

        init();
//        SanPham sanPham = new SanPham("SP2","Phone",500,R.drawable.phone,27);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
//        myRef.child("SanPham").push().setValue(sanPham);

        myRef.child("SanPham").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SanPham sp = dataSnapshot.getValue(SanPham.class);
                mkey.add(dataSnapshot.getKey());
                arrayListSanPham.add(sp);
                adapterListSP.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SanPham sp = dataSnapshot.getValue(SanPham.class);
                String keyChange = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < mkey.size(); i++) {
                    if (keyChange.equals(mkey.get(i))) {
                        index = i;
                    }
                }
                arrayListSanPham.set(index,sp);
                adapterListSP.notifyDataSetChanged();

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
                arrayListSanPham.remove(index);
                adapterListSP.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnToAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSpEnd = "0";
                if (arrayListSanPham.size()>0){
                    maSpEnd = arrayListSanPham.get(arrayListSanPham.size() - 1).getMaSP().substring(2);
                }


                Intent intent = new Intent(getApplicationContext(), AddSanPhamActivity.class);
                intent.putParcelableArrayListExtra("listSP", arrayListSanPham);
                intent.putExtra("maSpEnd", Integer.parseInt(maSpEnd));
                startActivity(intent);

            }
        });
        lvSPQL.setAdapter(adapterListSP);
        lvSPQL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QLSanPhamActivity.this,CustomSanPhamActivity.class);
                intent.putParcelableArrayListExtra("listSP",arrayListSanPham);
                intent.putExtra("spSelected",arrayListSanPham.get(position));
                intent.putExtra("mkey",mkey.get(position));
                startActivity(intent);
            }
        });

    }

    private void init() {
        lvSPQL = findViewById(R.id.lvSPQL);
        btnToAddSP = findViewById(R.id.btnToAddSP);
        arrayListSanPham = new ArrayList<>();
        adapterListSP = new AdapterListSP(QLSanPhamActivity.this, R.layout.item_sanpham, arrayListSanPham);
        mkey = new ArrayList<>();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.arrayListSanPham);
    }

    public QLSanPhamActivity() {
    }

    protected QLSanPhamActivity(Parcel in) {
        this.arrayListSanPham = in.createTypedArrayList(SanPham.CREATOR);
    }

    public static final Parcelable.Creator<QLSanPhamActivity> CREATOR = new Parcelable.Creator<QLSanPhamActivity>() {
        @Override
        public QLSanPhamActivity createFromParcel(Parcel source) {
            return new QLSanPhamActivity(source);
        }

        @Override
        public QLSanPhamActivity[] newArray(int size) {
            return new QLSanPhamActivity[size];
        }
    };


}
