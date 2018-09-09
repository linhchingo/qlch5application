package com.lethaqlch.qlch5application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterQuestListSP extends RecyclerView.Adapter<AdapterQuestListSP.ViewHolder> {
    public interface OnItemClickListen {
        void click(SanPham sanPham);
    }

    ArrayList<SanPham> arrayList;
    Context context;
    OnItemClickListen onItemClickListen;

    public AdapterQuestListSP(ArrayList<SanPham> arrayList, Context context, OnItemClickListen listen) {
        this.arrayList = arrayList;
        this.context = context;
        onItemClickListen = listen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_sp_quest, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imgItemSPQuest.setImageResource(arrayList.get(position).getHinhSp());
        holder.tvItemTenSPQuest.setText(arrayList.get(position).getTenSp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListen != null) {
                    onItemClickListen.click(arrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemSPQuest;
        TextView tvItemTenSPQuest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemSPQuest = itemView.findViewById(R.id.imgItemSPQuest);
            tvItemTenSPQuest = itemView.findViewById(R.id.tvItemTenSPQuest);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context,SanPhamChiTietQuestActivity.class);
                    intent.putExtra("",arrayList.get(getAdapterPosition()));
                    context.startActivity(intent);*/

                }
            });
        }
    }

}
