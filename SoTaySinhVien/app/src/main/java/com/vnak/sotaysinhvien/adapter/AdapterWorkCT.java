package com.vnak.sotaysinhvien.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vnak.sotaysinhvien.EditWorkActivity;
import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.model.Work;

import java.util.ArrayList;

public class AdapterWorkCT extends RecyclerView.Adapter<AdapterWorkCT.Holder>{
    Activity activity;
    ArrayList<Work> list;

    public AdapterWorkCT(Activity activity, ArrayList<Work> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemword_ct,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final Work work = list.get(position);
        String date = (!work.getThoiGian().equals("CN")) ? "Thứ "+((String) work.getThoiGian()).substring(1) : "Chủ Nhật";
        holder.txtTime.setText("\t"+work.getGio());
        holder.txtThu.setText("\t"+date);
        holder.txtAddress.setText("\t"+work.getDiaDiem());

        if (!activity.getTitle().equals("Thêm Lịch")) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditWorkActivity.txtDiaChi.getEditText().setText(work.getDiaDiem());
                    int positionThu = (!work.getThoiGian().equals("CN")) ? Integer.parseInt(work.getThoiGian().substring(1))-2 : 6;
                    EditWorkActivity.thu.setSelection(positionThu);
                    String bd = work.getGio().substring(0,5);
                    String kt = work.getGio().substring(7);
                    EditWorkActivity.txtGioKT.setText(kt);
                    EditWorkActivity.txtGioBD.setText(bd);
                    EditWorkActivity.positionWork = position;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtThu,txtTime,txtAddress;
        FloatingActionButton btnDelete;
        CardView item;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtThu = itemView.findViewById(R.id.txtThu);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            item = itemView.findViewById(R.id.item);
        }
    }
}
