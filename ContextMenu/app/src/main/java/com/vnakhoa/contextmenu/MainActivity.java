package com.vnakhoa.contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvDB;
    ArrayList<DanhBa> dsDB;
    ArrayAdapter<DanhBa> adapterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDB = findViewById(R.id.lvDB);
        dsDB = new ArrayList<>();
        dsDB.add(new DanhBa("Công An","113"));
        dsDB.add(new DanhBa("Cứu Hỏa","114"));
        dsDB.add(new DanhBa("Cứu Thương","115"));
        dsDB.add(new DanhBa("Tìm Kiếm Cứu Nạn","112"));
        adapterDB = new ArrayAdapter<DanhBa>(
                MainActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                dsDB);
        lvDB.setAdapter(adapterDB);
        registerForContextMenu(lvDB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to exit");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                break;
            case R.id.setting:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case R.id.share:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case R.id.exit:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case R.id.setting:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            case R.id.share:
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
