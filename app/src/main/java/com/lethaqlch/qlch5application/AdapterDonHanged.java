package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterDonHanged extends BaseAdapter {
    Context context;
    int layout;
    List<DonHangItem> listDonHanged;

    public AdapterDonHanged(Context context, int layout, List<DonHangItem> listDonHanged) {
        this.context = context;
        this.layout = layout;
        this.listDonHanged = listDonHanged;
    }

    @Override
    public int getCount() {
        return listDonHanged.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        TextView tvMaDHed = convertView.findViewById(R.id.tvMaDHed);
        TextView tvNVDHed = convertView.findViewById(R.id.tvNVDHed);
        TextView tvKHDHed = convertView.findViewById(R.id.tvKHDHed);
        TextView tvTongTienDHed = convertView.findViewById(R.id.tvTongTienDHed);


        DonHangItem donHanged = listDonHanged.get(position);

        tvMaDHed.setText(donHanged.getMaDH());
        tvNVDHed.setText(donHanged.getMaNV());
        tvKHDHed.setText(donHanged.getiDKhach());
        tvTongTienDHed.setText(donHanged.getTongTien()+"");



        return convertView;
    }
}
