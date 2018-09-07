package com.lethaqlch.qlch5application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lethaqlch.qlch5application.base.BaseActivity;
import com.lethaqlch.qlch5application.mClass.Firebase;

import java.util.ArrayList;

public class QLTaiKhoanActivity extends BaseActivity {
    private ListView lvTK;
    private ArrayList<TaiKhoan> taiKhoanArrayList;
    private ArrayList<String>   key;
    private AdapterListAccount adapterListAccount;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltai_khoan);

        init();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference child = database.child("TaiKhoan");
        database.child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                taiKhoanArrayList.add(taiKhoan);
                key.add(dataSnapshot.getKey());
                adapterListAccount.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                String keyChange = dataSnapshot.getKey();
                int index = 0;
                for (int i=0;i<key.size();i++){
                    if (keyChange.equals(key.get(i))){
                        index = i;
                    }
                }
                taiKhoanArrayList.set(index,taiKhoan);
                adapterListAccount.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String keyDel = dataSnapshot.getKey();
                int index = 0;
                for (int i=0;i<key.size();i++){
                    if (keyDel.equals(key.get(i))){
                        index = i;
                    }
                }
                taiKhoanArrayList.remove(index);
                adapterListAccount.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvTK.setAdapter(adapterListAccount);
        lvTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QLTaiKhoanActivity.this,SettingAccountActivity.class);
                intent.putExtra("taikhoan",taiKhoanArrayList.get(position));
                intent.putExtra("key",key.get(position));
                startActivity(intent);
            }
        });
        lvTK.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogDel(key.get(position),taiKhoanArrayList.get(position));
                return false;
            }
        });
    }

    private void init() {
        lvTK = findViewById(R.id.lvTK);
        taiKhoanArrayList = new ArrayList<>();
        key = new ArrayList<>();
        adapterListAccount = new AdapterListAccount(QLTaiKhoanActivity.this,R.layout.item_taikhoan,taiKhoanArrayList);
    }

    private void showDialogDel(final String mkey, final TaiKhoan taiKhoan){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete").setIcon(android.R.drawable.ic_menu_delete).setMessage("Ban co chac muon xoa tai khoan '"+taiKhoan.getiD()+"' nay khong?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("TaiKhoan").child(mkey).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(getApplicationContext(), "Xoa tai khoan "+taiKhoan.getiD()+" thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Xoa tai khoan "+taiKhoan.getiD()+" that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

}
