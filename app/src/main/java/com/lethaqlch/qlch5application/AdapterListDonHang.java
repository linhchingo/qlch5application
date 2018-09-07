package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterListDonHang extends BaseAdapter {
    Context context;
    int layout;
    List<DonHangItem> donHangItems;

    public AdapterListDonHang(Context context, int layout, List<DonHangItem> donHangItems) {
        this.context = context;
        this.layout = layout;
        this.donHangItems = donHangItems;
    }

    @Override
    public int getCount() {
        return donHangItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);

        TextView tvMaDHDonHang = convertView.findViewById(R.id.tvMaDHDonHang);
        TextView tvIDKhachDonHang = convertView.findViewById(R.id.tvIDKhachDonHang);
        TextView tvNgayDatDonHang = convertView.findViewById(R.id.tvNgayDatDonHang);
        TextView tvGioDatDonHang = convertView.findViewById(R.id.tvGioDatDonHang);
        TextView tvTongTienDonHang = convertView.findViewById(R.id.tvTongTienDonHang);

        DonHangItem donHangItem = donHangItems.get(position);

        tvMaDHDonHang.setText(donHangItem.getMaDH());
        tvIDKhachDonHang.setText(donHangItem.getiDKhach());
        tvNgayDatDonHang.setText(donHangItem.getNgayDat());
        tvGioDatDonHang.setText(donHangItem.getGioDat());
        tvTongTienDonHang.setText(donHangItem.getTongTien()+"");


        return convertView;
    }
}
