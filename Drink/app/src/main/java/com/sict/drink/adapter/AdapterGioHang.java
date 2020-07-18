package com.sict.drink.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.drink.GioHangActivity;
import com.sict.drink.MainActivity;
import com.sict.drink.R;
import com.sict.drink.ThanhToanActivity;
import com.sict.drink.model.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.Holder> {
    Activity context;
    ArrayList<GioHang> list;

    public AdapterGioHang(Activity context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        final GioHang gioHang = list.get(position);
        Picasso.get().load(gioHang.getDoUong().getAnh()).into(holder.imgspgiohang);
        int sl = gioHang.getSoLuong();
        if (sl >= 10) {
            holder.btncong.setVisibility(View.INVISIBLE);
        }
        if (gioHang.getDoUong().getTen().length() > 24){
            holder.txttenspgiohang.setTextSize(14);
            holder.txttenspgiohang.setPadding(0,5,0,10);
        }
        if (gioHang.getSoLuong() == 0) {
            MainActivity.gioHangs.remove(position);
        }
        holder.txtgiatri.setText(sl+"");
        holder.txtgiaspgiohang.setText(Integer.parseInt(gioHang.getDoUong().getGia())*sl+".000đ");
        holder.txttenspgiohang.setText(gioHang.getDoUong().getTen());
        holder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = gioHang.getSoLuong();
                MainActivity.gioHangs.get(position).setSoLuong(sl-1);
                holder.txtgiatri.setText((sl-1)+"");
                holder.txtgiaspgiohang.setText(Integer.parseInt(gioHang.getDoUong().getGia())*(sl-1)+".000đ");
                if (sl <= 10 && holder.btncong.getVisibility() == View.INVISIBLE){
                    holder.btncong.setVisibility(View.VISIBLE);
                }
                if ((sl-1) == 0) {
                    MainActivity.gioHangs.remove(position);
                    notifyDataSetChanged();
                }
                CharSequence title = context.getTitle();
                if ("Giỏ Hàng".equals(title)) {
                    GioHangActivity.sum();
                } else if ("Thanh Toán".equals(title)) {
                    ThanhToanActivity.sum();
                }
            }
        });
        holder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = gioHang.getSoLuong();
                MainActivity.gioHangs.get(position).setSoLuong(sl+1);
                holder.txtgiatri.setText((sl+1)+"");
                holder.txtgiaspgiohang.setText(Integer.parseInt(gioHang.getDoUong().getGia())*(sl+1)+".000đ");
                if ((sl+1) == 10) {
                    holder.btncong.setVisibility(View.INVISIBLE);
                    Toast.makeText(context,"Số lượng tối đa là 10",Toast.LENGTH_SHORT).show();
                }
                CharSequence title = context.getTitle();
                if ("Giỏ Hàng".equals(title)) {
                    GioHangActivity.sum();
                } else if ("Thanh Toán".equals(title)) {
                    ThanhToanActivity.sum();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView txttenspgiohang,txtgiaspgiohang;
        public ImageView imgspgiohang;
        public Button btntru;
        public Button btncong;
        public TextView txtgiatri;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txttenspgiohang = itemView.findViewById(R.id.txttenmonhang);
            txtgiaspgiohang = itemView.findViewById(R.id.txtgiamonhang);
            imgspgiohang = itemView.findViewById(R.id.imggiohang);
            btncong = itemView.findViewById(R.id.btncong);
            txtgiatri = itemView.findViewById(R.id.txtgiatri);
            btntru = itemView.findViewById(R.id.btntru);
        }
    }
}
