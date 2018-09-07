package com.lethaqlch.qlch5application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CTDonHangActivity extends AppCompatActivity {
    private Spinner spinnerNVTiepNhan;
    private ArrayList<NhanVien> listNhanVien;
    private ArrayList<String> listTenNV;
    private ArrayList<Cart> listSPed;
    private ArrayList<Order> listOrderlistOrder;
    private ArrayAdapter adapter;
    private TextView tvCTMaDH, tvCTIdKhachDH, tvCTNgayDat, tvCTGioDat, tvCTTongTien, tvCTTinhTrang;
    private Button btnXacNhanDonHang, btnCancel;
    private ListView lvSPed;
    private DonHangItem donHang;
    private AdapterListCast adapterListCast;
    private String mkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_don_hang);
        mkey = new String();
        donHang = new DonHangItem();
        if (getIntent() != null) {
            donHang = getIntent().getParcelableExtra("DonHang");
            mkey = getIntent().getStringExtra("mkey");
        }


        init();
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("NhanVien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                listTenNV.add(nhanVien.getTenNV());
                adapter.notifyDataSetChanged();
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

        spinnerNVTiepNhan.setAdapter(adapter);

        tvCTMaDH.setText(donHang.getMaDH());
        tvCTIdKhachDH.setText(donHang.getiDKhach());
        tvCTNgayDat.setText(donHang.getNgayDat());
        tvCTGioDat.setText(donHang.getGioDat());
        tvCTTongTien.setText(donHang.getTongTien() + "");
        if (donHang.getMaNV().equals("null")) {
            tvCTTinhTrang.setText("Chua xac nhan");
        } else {
            tvCTTinhTrang.setText("Da xac nhan");
        }

        myRef.child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Order order = dataSnapshot.getValue(Order.class);
                if (order.getMaDH().equals(donHang.getMaDH())) {
                    listOrderlistOrder.add(order);
                    myRef.child("SanPham").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                            if (sanPham.getMaSP().equals(order.getMaSP())) {
                                listSPed.add(new Cart(sanPham, order.soluongOrder));
                                adapterListCast.notifyDataSetChanged();
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


        lvSPed.setAdapter(adapterListCast);

        btnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerNVTiepNhan.getSelectedItem().toString().equals("Chon nhan vien tiep nhan")){
                    Toast.makeText(getApplicationContext(),"vui long chon nguoi tiep nhan",Toast.LENGTH_SHORT).show();
                }else {
                    myRef.child("NhanVien").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                            if (nhanVien.getTenNV().equals(spinnerNVTiepNhan.getSelectedItem().toString())){
                                myRef.child("DonHang").child(mkey).child("maNV").setValue(nhanVien.getTenNV());
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
        });



    }

    private void init() {
        spinnerNVTiepNhan = findViewById(R.id.spinnerNVTiepNhan);
        listNhanVien = new ArrayList<>();
        listTenNV = new ArrayList<>();
        listSPed = new ArrayList<>();
        listOrderlistOrder = new ArrayList<>();
        listTenNV.add("Chon nhan vien tiep nhan");
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listTenNV);
        tvCTMaDH = findViewById(R.id.tvCTMaDH);
        tvCTIdKhachDH = findViewById(R.id.tvCTIdKhachDH);
        tvCTNgayDat = findViewById(R.id.tvCTNgayDat);
        tvCTGioDat = findViewById(R.id.tvCTGioDat);
        tvCTTongTien = findViewById(R.id.tvCTTongTien);
        tvCTTinhTrang = findViewById(R.id.tvCTTinhTrang);
        btnXacNhanDonHang = findViewById(R.id.btnXacNhanDonHang);
        btnCancel = findViewById(R.id.btnCancel);
        lvSPed = findViewById(R.id.lvSPed);
        adapterListCast = new AdapterListCast(getApplicationContext(), R.layout.item_cart, listSPed);
    }
}
