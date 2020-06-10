package com.vnakhoa.tygiahoaidoi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vnakhoa.tygiahoaidoi.R;
import com.vnakhoa.tygiahoaidoi.model.TyGia;

import java.util.List;

public class AdapterTyGia extends ArrayAdapter<TyGia> {

    Activity context;
    int resource;
    List<TyGia> objects;

    public AdapterTyGia(@NonNull Activity context, int resource, @NonNull List<TyGia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View item = layoutInflater.inflate(this.resource,null);

        TyGia tyGia = this.objects.get(position);

        ImageView imgQG = item.findViewById(R.id.imgQG);
        TextView txtMa = item.findViewById(R.id.txtMa);
        TextView txtMuatm = item.findViewById(R.id.txtmuatm);
        TextView txtMuack = item.findViewById(R.id.txtmuack);
        TextView txtBantm = item.findViewById(R.id.txtbantm);
        TextView txtBanck = item.findViewById(R.id.txtbanck);

        imgQG.setImageBitmap(tyGia.getBitmap());
        txtMa.setText(tyGia.getType());
        txtBanck.setText(tyGia.getBanck());
        txtMuatm.setText(tyGia.getMuatienamt());
        txtMuack.setText(tyGia.getMuack());
        txtBantm.setText(tyGia.getBantienmat());

        return item;
    }
}
