package com.lethaqlch.qlch5application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterListCast extends BaseAdapter {
    Context context;
    int layout;
    List<Cart> cartItems;

    public AdapterListCast(Context context, int layout, List<Cart> cartItems) {
        this.context = context;
        this.layout = layout;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
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

        TextView tvItemMaSPCart = convertView.findViewById(R.id.tvItemMaSPCart);
        TextView tvItemNameSPCart = convertView.findViewById(R.id.tvItemNameSPCart);
        TextView tvItemSoLuongSPCart = convertView.findViewById(R.id.tvItemSoLuongSPCart);
        TextView tvItemGiaSPCart = convertView.findViewById(R.id.tvItemGiaSPCart);


        Cart cartItem = cartItems.get(position);

        tvItemMaSPCart.setText(cartItem.getSanPham().getMaSP());
        tvItemNameSPCart.setText(cartItem.getSanPham().getTenSp());
        tvItemSoLuongSPCart.setText(cartItem.getSoLuongOrder()+"");
        tvItemGiaSPCart.setText(cartItem.getSanPham().getGiaSp()+"");




        return convertView;
    }
}
