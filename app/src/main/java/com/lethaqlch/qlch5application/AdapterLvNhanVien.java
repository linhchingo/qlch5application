package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lethaqlch.qlch5application.base.BaseActivity;

import java.util.List;

public class AdapterLvNhanVien extends BaseAdapter{
    private Context context;
    private int layout;
    private List<NhanVien> listNhanVien;

    public AdapterLvNhanVien(Context context, int layout, List<NhanVien> listNhanVien) {
        this.context = context;
        this.layout = layout;
        this.listNhanVien = listNhanVien;
    }

    @Override
    public int getCount() {
        return listNhanVien.size();
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

       TextView tvItemMaNV = convertView.findViewById(R.id.tvItemMaNV);
       TextView tvItemTenNV = convertView.findViewById(R.id.tvItemTenNV);

       NhanVien nhanVien = listNhanVien.get(position);

        tvItemMaNV.setText("Ma NV: "+nhanVien.getMaNV());
        tvItemTenNV.setText("Ten NV: "+nhanVien.getTenNV());



        return convertView;
    }
}
