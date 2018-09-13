package com.lethaqlch.qlch5application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
    private String mkey,emailNV,emailKH,maNV;

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
                listNhanVien.add(nhanVien);
                listTenNV.add(nhanVien.getMaNV());
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
        myRef.child("TaiKhoan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                if (taiKhoan.getiD().equals(donHang.getiDKhach())){
                    emailKH = taiKhoan.getEmail();
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
                    maNV = spinnerNVTiepNhan.getSelectedItem().toString();
                    Iterator<NhanVien> nhanVienIterator = listNhanVien.iterator();
                    while(nhanVienIterator.hasNext()){
                        NhanVien nhanVien = nhanVienIterator.next();
                        if (nhanVien.getMaNV().equals(maNV)){
                            emailNV = nhanVien.getEmailNV();
                        }

                    }

                    myRef.child("NhanVien").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            NhanVien nhanVien = dataSnapshot.getValue(NhanVien.class);
                            if (nhanVien.getMaNV().equals(spinnerNVTiepNhan.getSelectedItem().toString())){
                                myRef.child("DonHang").child(mkey).child("maNV").setValue(nhanVien.getMaNV());
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

                    Log.i("SendMailActivity", "Send Button Clicked.");

                    String fromEmail = "lethaisan191193@gmail.com";
                    String fromPassword ="heoconngoc";
                    String toEmails = emailNV+","+emailKH;
                    List<String> toEmailList = Arrays.asList(toEmails
                            .split("\\s*,\\s*"));
                    Log.i("SendMailActivity", "To List: " + toEmailList);
                    String emailSubject = "Xac Nhan Don Hang"+donHang.getMaDH();
                    String emailBody = "<!DOCTYPE html>" +
                            "<html lang=\"en\">" +
                            "<head>" +
                            "    <meta charset=\"UTF-8\">" +
                            "    <title>Title</title>" +
                            "</head>" +
                            "" +
                            "<body>" +
                            "<table style=\"border-spacing: 0px;\">" +
                            "    <tbody>" +
                            "    <tr>" +
                            "        <td style=\"padding: 5px;\">Ma Don Hang:" +
                            "        </td>" +
                            "        <td style=\"padding: 5px;\">"+donHang.getMaDH()+
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td style=\"padding: 5px;\">Tai Khoan Mua Hang:" +
                            "        </td>" +
                            "        <td style=\"padding: 5px;\">"+donHang.getiDKhach()+
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td style=\"padding: 5px;\">Ngay Dat:" +
                            "        </td>" +
                            "        <td style=\"padding: 5px;\">"+donHang.getNgayDat()+
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td style=\"padding: 5px;\">Gio Dat:" +
                            "        </td>" +
                            "        <td style=\"padding: 5px;\">"+donHang.getGioDat()+
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td style=\"padding: 5px;\">Nhan Vien Tiep Nhan:" +
                            "        </td>" +
                            "        <td style=\"padding: 5px;\">"+maNV+
                            "        </td>" +
                            "    </tr>" +
                            "    </tbody>" +
                            "</table>" +






                            "<table style=\"border-spacing: 0px;\">" +
                            "    <tbody>" +
                            spEd(listSPed)+
                            "    <tr>" +
                            "        <td style=\" border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">" +
                            "            &nbsp;" +
                            "        </td>" +
                            "        <td style=\"border-bottom: 1px solid #000;margin: 0px; padding: 5px 10px;\">" +
                            "            &nbsp;" +
                            "        </td>" +
                            "        <td style=\"border-bottom: 1px solid #000; margin: 0px;padding: 5px 10px;\">" +
                            "            Tong tien: " +donHang.getTongTien()+
                            "        </td>" +
                            "        <td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">&nbsp;</td>" +
                            "    </tr>" +
                            "    </tbody>" +
                            "</table>" +
                            "</body>" +
                            "</html>"
                            /*"<p> Ma Don Hang: "+donHang.getMaDH()+"</p>"+
                            "<p>Tai Khoan Mua Hang: "+donHang.getiDKhach()+"</p>"+
                            "<p>Tong Tien: "+donHang.getTongTien()+"</p>"+
                            "<p>Ngay Dat: "+donHang.getNgayDat()+"</p>"+
                            "<p>Gio Dat: "+donHang.getGioDat()+"</p>"+
                            "<p>Nhan Vien Tiep Nhan: "+maNV+"</p>"+
                            "<table class=\"table-m\">"+spEd(listSPed)+"</table>"+
                            "<style>\n" +
                            "    .table-m tr td {\n" +
                            "        border-bottom: 1px solid #000;\n" +
                            "        margin: 0px;\n" +
                            "        padding: 5px 10px;\n" +
                            "    }\n" +
                            "    .table-m {\n" +
                            "        border-spacing: 0px;\n" +
                            "    }\n" +
                            "    .table-c tr td {\n" +
                            "        padding: 5px;\n" +
                            "    }\n" +
                            "</style>"*/

                            ;
                    new SendMailTask(CTDonHangActivity.this).execute(fromEmail,
                            fromPassword, toEmailList, emailSubject, emailBody);

                    btnXacNhanDonHang.setEnabled(false);
                    tvCTTinhTrang.setText("Da Xac Nhan");

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        emailNV = new String();
        emailKH = new String();
        maNV = new String();
    }

    public String spEd(ArrayList<Cart> listCart){
        StringBuilder stringBuilder = new StringBuilder();
               stringBuilder.append(
                       "<tr>" +
                               "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">Ma SP</td>" +
                               "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">Ten SP</td>" +
                               "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">Don Gia</td>" +
                               "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">So luong mua</td>" +
                               "</tr>");
        Iterator<Cart> cartIterator = listCart.iterator();
        while (cartIterator.hasNext()){
            Cart cart= cartIterator.next();
            String itemCart = " <tr>" +
                    "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">"+cart.getSanPham().getMaSP()+"</td>"+
                    "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">"+cart.getSanPham().getTenSp()+"</td>"+
                    "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">"+cart.getSanPham().getGiaSp()+"</td>"+
                    "<td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">"+cart.getSoLuongOrder()+"</td>" +
                    "</tr>";
            stringBuilder.append(itemCart);
        }
        return stringBuilder.toString();
    }
 /*"    <tr>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">" +
         "            Ma SP: SP7" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">" +
         "            Ten SP: Taplet mini" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px; padding: 5px 10px;\">" +
         "            Don Gia: 500" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">" +
         "            So luong mua: 1" +
         "        </td>" +
         "    </tr>" +
         "    <tr>" +
         "        <td style=\" border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">" +
         "            &nbsp;" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px; padding: 5px 10px;\">" +
         "            &nbsp;" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000; margin: 0px;padding: 5px 10px;\">" +
         "            Tong tien: 500" +
         "        </td>" +
         "        <td style=\"border-bottom: 1px solid #000;margin: 0px;padding: 5px 10px;\">&nbsp;</td>" +
         "    </tr>" +

*/
}
