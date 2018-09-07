package com.lethaqlch.qlch5application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lethaqlch.qlch5application.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private TextView textViewIdAcc;
    private ImageButton imgbToCart;
    private TaiKhoan taiKhoan;
    private RecyclerView recyclerViewSPQuest;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<String> listKey;
    private AdapterQuestListSP adapterQuestListSP;
    private ArrayList<Cart> cartList;
    private Cart cart;
    private final int REQUEST_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("resume", "onCreate: ");

        taiKhoan = new TaiKhoan();
        if (getIntent() != null) {
            taiKhoan = getIntent().getParcelableExtra("AccLog");
        }

        init();


        textViewIdAcc.setText("Tai khoan: " + taiKhoan.getiD());

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("SanPham").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                listSanPham.add(sanPham);
                listKey.add(dataSnapshot.getKey());
                adapterQuestListSP.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                String keyChange = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < listKey.size(); i++) {
                    if (keyChange.equals(listKey.get(i))) {
                        index = i;
                    }
                }
                listSanPham.set(index, sanPham);
                adapterQuestListSP.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String keyDel = dataSnapshot.getKey();
                int index = 0;
                for (int i = 0; i < listKey.size(); i++) {
                    if (keyDel.equals(listKey.get(i))) {
                        index = i;
                    }
                }
                listSanPham.remove(index);
                adapterQuestListSP.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerViewSPQuest = findViewById(R.id.recyclerViewListSP);
        recyclerViewSPQuest.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);

        recyclerViewSPQuest.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
//        recyclerViewSPQuest.addItemDecoration(dividerItemDecoration);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewSPQuest.getContext(), DividerItemDecoration.VERTICAL);
//        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerViewSPQuest.getContext(), DividerItemDecoration.HORIZONTAL);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_divider);
//        dividerItemDecoration.setDrawable(drawable);
//        dividerItemDecoration2.setDrawable(drawable);

//        recyclerViewSPQuest.addItemDecoration(dividerItemDecoration);
//        recyclerViewSPQuest.addItemDecoration(dividerItemDecoration2);
        adapterQuestListSP = new AdapterQuestListSP(listSanPham, this, new AdapterQuestListSP.OnItemClickListen() {
            @Override
            public void click(SanPham sanPham) {
                Intent intent = new Intent(getApplicationContext(), SanPhamChiTietQuestActivity.class);
                intent.putExtra("SanPham", sanPham);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        recyclerViewSPQuest.setAdapter(adapterQuestListSP);


        imgbToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putParcelableArrayListExtra("cardList", cartList);
                intent.putExtra("tai khoan", taiKhoan);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            cart = data.getParcelableExtra("cart");
            SanPham sanPhamTemp = data.getParcelableExtra("sanPhamUpdate");

            for (int i = 0; i < listSanPham.size(); i++) {
                if (sanPhamTemp.getMaSP().equals(listSanPham.get(i).getMaSP())) {
                    listSanPham.get(i).setSoLuongTonKho(sanPhamTemp.getSoLuongTonKho());
                }
            }

            if (cartList.size() == 0) {
                cartList.add(cart);
            } else {
                if (!checkMaSp(cart)) {
                    for (int i = 0; i < cartList.size(); i++) {
                        if (cart.getSanPham().getMaSP().equals(cartList.get(i).getSanPham().getMaSP())) {
                            cart.setSoLuongOrder(cart.getSoLuongOrder() + cartList.get(i).getSoLuongOrder());
                            cartList.set(i, cart);
                        }
                    }
                } else {
                    cartList.add(cart);
                }
            }
        }


    }


    private void init() {
        textViewIdAcc = findViewById(R.id.textViewIdAcc);
        imgbToCart = findViewById(R.id.imgbToCart);
        listSanPham = new ArrayList<>();
        listKey = new ArrayList<>();
        cartList = new ArrayList<>();


    }


    public Boolean checkMaSp(Cart cart) {
        Boolean flag = true;
        for (int i = 0; i < cartList.size(); i++) {
            if (cart.getSanPham().getMaSP().equals(cartList.get(i).getSanPham().getMaSP())) {
                flag = false;
            }
        }
        return flag;
    }


}