package com.lethaqlch.qlch5application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lethaqlch.qlch5application.mClass.FileInternal;
import com.lethaqlch.qlch5application.mClass.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class CartActivity extends AppCompatActivity {
    private TextView tvIdAccCart, tvTongTien;
    private ListView lvCart;
    private Button btnHuy, btnDatMua;
    private ArrayList<Cart> cartItemArrayList;
    private AdapterListCast adapterListCast;
    private int tongTien;
    private Calendar calendarNow;
    private SimpleDateFormat simpleDateFormat;
    private TaiKhoan taiKhoan;
    private ArrayList<DonHangItem> arrListDonHang;
    private ArrayList<Order> arraylistOrder;
    private  DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        taiKhoan = new TaiKhoan();
        cartItemArrayList = new ArrayList<>();
        if (getIntent() != null) {
           taiKhoan = getIntent().getParcelableExtra("tai khoan");
           cartItemArrayList = getIntent().getParcelableArrayListExtra("cardList");

        }

        init();
        if (cartItemArrayList.size() == 0){
            btnDatMua.setEnabled(false);
        }
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DonHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHangItem donHang= dataSnapshot.getValue(DonHangItem.class);
                arrListDonHang.add(donHang);

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
        tvIdAccCart.setText(taiKhoan.getiD());

        adapterListCast = new AdapterListCast(CartActivity.this, R.layout.item_cart, cartItemArrayList);

        lvCart.setAdapter(adapterListCast);



        //cartItemArrayList.add(new CartItem("SP03","May Tinh",800,3));
        final Iterator<Cart> iterator = cartItemArrayList.iterator();
        while (iterator.hasNext()) {
            Cart cartItem = iterator.next();
            int soLuong = cartItem.getSoLuongOrder();
            int gia = cartItem.getSanPham().getGiaSp();
            tongTien += (soLuong * gia);
        }

        tvTongTien.setText("Tong tien: " + tongTien);




        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] ngayGioLap =  simpleDateFormat.format(calendarNow.getTime()).toString().split(",");
                String ngayLap = ngayGioLap[0];
                String gioLap = ngayGioLap[1];
                String maNv = "null";
                int indexMaDH = 1;


                if (arrListDonHang.size() == 0){
                    DonHangItem donHang = new DonHangItem("DH"+indexMaDH,taiKhoan.getiD(),maNv,ngayLap,gioLap,tongTien);
                    myRef.child("DonHang").push().setValue(donHang);
                }else {
                    indexMaDH = Integer.parseInt(arrListDonHang.get(arrListDonHang.size()-1).getMaDH().substring(2))+1;
                    DonHangItem donHang = new DonHangItem("DH"+indexMaDH,taiKhoan.getiD(),maNv,ngayLap,gioLap,tongTien);
                    myRef.child("DonHang").push().setValue(donHang);
                }

//                Iterator<Cart> cartIterator = cartItemArrayList.iterator();
//                while (cartIterator.hasNext()){
//                    Order order = new Order("DH"+indexMaDH,cartIterator.next().getSanPham().getMaSP(),cartIterator.next().getSoLuongOrder());
//                    myRef.child("Order").push().setValue(order);
//                }

                for (int i = 0 ; i < cartItemArrayList.size();i++){
                    Order order = new Order("DH"+indexMaDH,cartItemArrayList.get(i).getSanPham().getMaSP(),cartItemArrayList.get(i).getSoLuongOrder());
                    myRef.child("Order").push().setValue(order);
                }

                Intent intent = new Intent(CartActivity.this,FinishBuyActivity.class);
                intent.putExtra("tai khoan", taiKhoan);
                startActivity(intent);
                finish();
//
//
//                //String gioLap = simpleDateFormatTime.format(calendarNow.getTime().toString());
//                String s = maDH + "," + idAcc + "," + tongTien + "," + ngayGioLap + *//*"," + gioLap +*//* "," + 0 + "\n";
//                *//*Iterator<CartItem> iterator = cartItemArrayList.iterator();
//                while (iterator.hasNext()) {
//                    CartItem cartItem = iterator.next();
//
//                }*//*
//                FileInternal fileInternal = new FileInternal(CartActivity.this);
//                fileInternal.writeData(s, "datadonhang.txt", true);
//                Intent intent = new Intent(CartActivity.this,FinishBuyActivity.class);
//                startActivity(intent);
//                finish();

            }
        });

    }

    private void init() {

        tvIdAccCart = findViewById(R.id.tvIdAccCart);
        tvTongTien = findViewById(R.id.tvTongTien);
        lvCart = findViewById(R.id.lvCart);
        btnHuy = findViewById(R.id.btnHuy);
        btnDatMua = findViewById(R.id.btnDatMua);

        tongTien = 0;
        calendarNow = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
        arraylistOrder = new ArrayList<>();
        arrListDonHang = new ArrayList<>();



    }

}
