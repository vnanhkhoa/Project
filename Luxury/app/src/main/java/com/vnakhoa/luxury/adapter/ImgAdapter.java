package com.vnakhoa.luxury.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.luxury.R;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgHoder> {

    ArrayList<Integer> arrImg;
    Context context;

    public ImgAdapter(ArrayList<Integer> arrImg, Context context) {
        this.arrImg = arrImg;
        this.context = context;
    }

    @NonNull
    @Override
    public ImgHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img,parent,false);
        return new ImgHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHoder holder, int position) {
        holder.imgView.setImageResource(arrImg.get(position));
    }

    @Override
    public int getItemCount() {
        return arrImg.size();
    }

    public class ImgHoder extends RecyclerView.ViewHolder{
        ImageView imgView;
        public ImgHoder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgHotel);
        }
    }

}
