package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class  AdapterListAccount extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TaiKhoan> taiKhoanList;

    public AdapterListAccount(Context context, int layout, List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.layout = layout;
        this.taiKhoanList = taiKhoanList;
    }

    @Override
    public int getCount() {
        return taiKhoanList.size();
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
        convertView = inflater.inflate(layout, null);

        TextView tvItemIDACC = convertView.findViewById(R.id.tvItemIDACC);
        TextView tvItemPrivilege = convertView.findViewById(R.id.tvItemPrivilege);

        TaiKhoan taiKhoan = taiKhoanList.get(position);
        String quyen = null;
        switch (taiKhoan.getQuyen()) {
            case 1:
                quyen = "Khach hang";
                break;
            case 2:
                quyen = "Admin";
                break;

        }
        tvItemIDACC.setText("ID: "+taiKhoan.getiD());
        tvItemPrivilege.setText("Privilege: "+quyen);

        return convertView;
    }
}
