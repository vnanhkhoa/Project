package com.sict.drink.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.drink.MainActivity;
import com.sict.drink.R;
import com.sict.drink.model.DoUong;
import com.sict.drink.model.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDoUong extends RecyclerView.Adapter<AdapterDoUong.Holder>{
    Context context;
    ArrayList<DoUong> list;

    public AdapterDoUong(Context context, ArrayList<DoUong> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_do_uong,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final DoUong doUong = list.get(position);
        holder.txtTen.setText(doUong.getTen());
        holder.txtGia.setText(doUong.getGia()+".000đ");
        Picasso.get().load(doUong.getAnh()).into(holder.anh);
        if (MainActivity.gioHangs.size() > 0) {
            for (GioHang gioHang : MainActivity.gioHangs) {
                if (gioHang.getDoUong().getTen().equals(doUong.getTen()) &&
                        gioHang.getDoUong().getAnh().equals(doUong.getAnh()) &&
                        gioHang.getDoUong().getGia().equals(doUong.getGia())) {
                    if (gioHang.getSoLuong() >= 10) {
                        holder.btnadd.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangs.size() == 0) {
                    GioHang gioHang = new GioHang();
                    gioHang.setSoLuong(1);
                    gioHang.setDoUong(doUong);
                    MainActivity.gioHangs.add(gioHang);
                } else {
                    Boolean check = true;
                    for (GioHang gioHang : MainActivity.gioHangs) {
                        if (gioHang.getDoUong().getTen().equals(doUong.getTen()) &&
                                gioHang.getDoUong().getAnh().equals(doUong.getAnh()) &&
                                gioHang.getDoUong().getGia().equals(doUong.getGia())) {
                            if (gioHang.getSoLuong() < 10) {
                                gioHang.setSoLuong(gioHang.getSoLuong()+1);
                            } else {
                                holder.btnadd.setVisibility(View.INVISIBLE);
                               Toast.makeText(context,"Số lượng tối đa là 10",Toast.LENGTH_SHORT).show();
                            }
                            check = false;
                            break;
                        }
                    }
                    if (check.equals(true)) {
                        GioHang gioHang = new GioHang();
                        gioHang.setSoLuong(1);
                        gioHang.setDoUong(doUong);
                        MainActivity.gioHangs.add(gioHang);
                    }
                }
                Toast.makeText(context,"Đã thêm "+doUong.getTen(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView txtTen,txtGia;
        ImageButton btnadd;
        public Holder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.anh);
            txtTen = itemView.findViewById(R.id.txtten);
            txtGia = itemView.findViewById(R.id.txtgia);
            btnadd = itemView.findViewById(R.id.btnadd);
        }
    }
}
