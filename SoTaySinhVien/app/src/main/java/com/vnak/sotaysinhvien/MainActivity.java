package com.vnak.sotaysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.vnak.sotaysinhvien.fragment.HomeFragment;
import com.vnak.sotaysinhvien.fragment.PointFragment;
import com.vnak.sotaysinhvien.fragment.ThongTinFragment;
import com.vnak.sotaysinhvien.model.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    NavigationView view;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        if (compat.areNotificationsEnabled()) {
            compat.cancel(1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent.hasExtra("User")) {
            user = (User) intent.getSerializableExtra("User");
            headerNav(view,user);
            SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ID",user.getId());
            editor.putString("MSV",user.getMsv());
            editor.putString("Name",user.getName());
            editor.putString("Email",user.getEmail());
            editor.putString("Gender",user.getGender());
            editor.putString("Birth",user.getBirth());
            editor.putString("Class",user.getLop());
            editor.putString("Address",user.getAddress());
            editor.putString("SDT",user.getSdt());
            editor.commit();
            Toast.makeText(MainActivity.this,"Wellcome "+ user.getName(),Toast.LENGTH_LONG).show();
        } else {
            user = new User();
            SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
            if (preferences != null)
            {
                String name = preferences.getString("Name",null);
                String email = preferences.getString("Email",null);
                user.setEmail(email);
                user.setName(name);
                headerNav(view,user);
            }
            if (user.getName() ==  null) {
                Intent intent1 = new Intent(MainActivity.this,Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            }
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout,new HomeFragment());
        ft.commit();

        view.setCheckedItem(R.id.home);
    }

    private void headerNav(NavigationView nav, User user) {
            View view = nav.getHeaderView(0);
            TextView txtEmail = view.findViewById(R.id.txtUser);
            TextView txtFullName = view.findViewById(R.id.txtFullName);

            txtEmail.setText(user.getEmail());
            txtFullName.setText(user.getName());
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.dwlayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = findViewById(R.id.nvgView);
        view.setNavigationItemSelectedListener(MainActivity.this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawe = findViewById(R.id.dwlayout);
        if (drawe.isDrawerOpen(GravityCompat.START))
        {
            drawe.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.home:
                ft.replace(R.id.fragment_layout,new HomeFragment());
                ft.commit();
                break;
            case R.id.inforuser:
                ft.replace(R.id.fragment_layout,new ThongTinFragment());
                ft.commit();
                break;
            case R.id.point:
                ft.replace(R.id.fragment_layout,new PointFragment());
                ft.commit();
                break;
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn Có Muốn Xóa Lịch Cá Nhân Không");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getSharedPreferences("Work", Context.MODE_PRIVATE);
                        preferences.edit().clear().commit();
                        Intent logout = new Intent(getApplicationContext(),Login.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(logout);
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),Login.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(logout);
                        finish();
                    }
                });
                builder.create().show();
                break;
            case R.id.editPass:
                Intent intent = new Intent(MainActivity.this,EditPass.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        item.setChecked(true);
        setTitle(item.getTitle());
        return true;
    }


}