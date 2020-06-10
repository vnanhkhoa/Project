package com.vnakhoa.floatingmenu.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.floatingmenu.MainActivity;
import com.vnakhoa.floatingmenu.R;
import com.vnakhoa.floatingmenu.model.SinhVien;

import java.util.ArrayList;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.Holder> {

    Context context;
    ArrayList<SinhVien> list;

    public SinhVienAdapter(Context context, ArrayList<SinhVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
            final SinhVien sv = list.get(position);
            holder.image.setImageResource(sv.getImg());
            holder.txtTT.setText(sv.toString());
            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId())
                            {
                                case R.id.xem:
                                    Toast.makeText(context,sv.getTen(),Toast.LENGTH_LONG).show();
                                    break;
                                case R.id.xoa:
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView txtTT;
        CardView item;
        public Holder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            txtTT = itemView.findViewById(R.id.txtTT);
            item = itemView.findViewById(R.id.item);

        }
    }

}
