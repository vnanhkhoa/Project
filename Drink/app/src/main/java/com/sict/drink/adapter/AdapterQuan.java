package com.sict.drink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.drink.DoUongActivity;
import com.sict.drink.R;
import com.sict.drink.model.Quan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterQuan extends RecyclerView.Adapter<AdapterQuan.Holder> {
    Context context;
    ArrayList<Quan> list;

    public AdapterQuan(Context context, ArrayList<Quan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quan,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Quan quan = list.get(position);
        holder.txtGiatb.setText(quan.getGiatb());
        holder.txtDiaChi.setText(quan.getDiaChi());
        holder.txtTen.setText(quan.getTen());
        holder.txtHmoCua.setText(quan.getGiomocua());
        Picasso.get().load(quan.getAnh()).into(holder.anh);

        holder.itemQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoUongActivity.class);
                intent.putExtra("ID",quan.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView anh;
        CardView itemQuan;
        TextView txtTen,txtGiatb,txtDiaChi,txtHmoCua;
        public Holder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.anh);
            itemQuan = itemView.findViewById(R.id.itemQuan);
            txtTen = itemView.findViewById(R.id.txtten);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtGiatb = itemView.findViewById(R.id.txtGiatb);
            txtHmoCua = itemView.findViewById(R.id.txtHmoCua);
        }
    }
}
