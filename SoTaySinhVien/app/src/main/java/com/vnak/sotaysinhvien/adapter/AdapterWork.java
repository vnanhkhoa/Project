package com.vnak.sotaysinhvien.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vnak.sotaysinhvien.EditWorkActivity;
import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.ThemLichCNActivity;
import com.vnak.sotaysinhvien.model.CalendarSubject;

import java.util.ArrayList;

public class AdapterWork extends RecyclerView.Adapter<AdapterWork.Holder>{
    Activity activity;
    ArrayList<CalendarSubject> list;

    public AdapterWork(Activity activity, ArrayList<CalendarSubject> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_word,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        CalendarSubject cv = list.get(position);
        final String x = cv.getMonhoc();
        holder.txtChuDe.setText(cv.getMonhoc());
        AdapterWorkCT workCT = new AdapterWorkCT(activity,cv.getDsWork());
        holder.rcvChiTiet.setAdapter(workCT);
        holder.rcvChiTiet.setLayoutManager(new LinearLayoutManager(activity));
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rcvChiTiet.setVisibility(View.VISIBLE);
                holder.btnShow.setVisibility(View.GONE);
                holder.btnClose.setVisibility(View.VISIBLE);
            }
        });
        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rcvChiTiet.setVisibility(View.GONE);
                holder.btnShow.setVisibility(View.VISIBLE);
                holder.btnClose.setVisibility(View.GONE);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                ThemLichCNActivity.listWork.remove(x);
                notifyDataSetChanged();
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditWorkActivity.class);
                intent.putExtra("Position",position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtChuDe;
        ImageView btnShow,btnClose;
        RecyclerView rcvChiTiet;
        FloatingActionButton btnDelete;
        CardView item;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtChuDe = itemView.findViewById(R.id.txtTitle);
            btnClose = itemView.findViewById(R.id.btnClose);
            btnShow = itemView.findViewById(R.id.btnShow);
            rcvChiTiet = itemView.findViewById(R.id.rcvChiTiet);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            item = itemView.findViewById(R.id.item);
        }
    }
}
