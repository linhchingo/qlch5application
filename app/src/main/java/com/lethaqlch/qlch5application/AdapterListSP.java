package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterListSP extends BaseAdapter {
    Context context;
    int layout;
    List<SanPham> sanPhamList;

    public AdapterListSP(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
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

        convertView = layoutInflater.inflate(layout, null);

        ImageView imageViewItemSP = convertView.findViewById(R.id.imgItemSP);
        TextView textViewItemTenSP = convertView.findViewById(R.id.tvItemTenSP);
        TextView textViewItemMaSP = convertView.findViewById(R.id.tvItemMaSP);
        TextView textViewItemGiaSP = convertView.findViewById(R.id.tvItemGiaSP);
        TextView textViewSoLuong = convertView.findViewById(R.id.tvSoLuong);

        SanPham sanPham = sanPhamList.get(position);

        imageViewItemSP.setImageResource(sanPham.getHinhSp());
        textViewItemTenSP.setText("Ten SP: "+sanPham.getTenSp());
        textViewItemMaSP.setText("Ma SP: "+sanPham.getMaSP());
        textViewItemGiaSP.setText("Gia SP: "+sanPham.getGiaSp());
        textViewSoLuong.setText("So Luong: "+sanPham.getSoLuongTonKho());


        return convertView;
    }
}
