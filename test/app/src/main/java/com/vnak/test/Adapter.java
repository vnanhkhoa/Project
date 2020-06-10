package com.vnak.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holer> {
    Context context;
    ArrayList<Song> list;

    public Adapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);
        Holer holer = new Holer(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(@NonNull Holer holder, int position) {
        Song song = list.get(position);
        holder.imageView2.setBackgroundResource(song.getImg());
        holder.txtSong.setText(song.getSong());
        holder.txtSinger.setText(song.getSinger());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity2.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holer extends RecyclerView.ViewHolder {
        ImageView imageView2;
        TextView txtSong,txtSinger;
        LinearLayout item;
        public Holer(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            txtSinger = itemView.findViewById(R.id.txtSinger);
            txtSong = itemView.findViewById(R.id.txtSong);
            item = itemView.findViewById(R.id.item);
        }
    }
}
