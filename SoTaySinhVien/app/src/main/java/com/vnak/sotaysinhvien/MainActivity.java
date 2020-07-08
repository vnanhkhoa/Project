package com.vnak.sotaysinhvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.vnak.sotaysinhvien.fragment.HomeFragment;
import com.vnak.sotaysinhvien.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    NavigationView view;
    Menu nav_Menu ;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivityForResult(intent,121);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 121)
        {
            if (resultCode == RESULT_OK)
            {
                user = (User) data.getSerializableExtra("User");
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
                editor.commit();
                Toast.makeText(MainActivity.this,"Wellcome "+ user.getName(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void headerNav(NavigationView nav, User user) {
            View view = nav.getHeaderView(0);
            TextView txtEmail = view.findViewById(R.id.txtUser);
            TextView txtFullName = view.findViewById(R.id.txtFullName);

            txtEmail.setText(user.getEmail());
            txtFullName.setText(user.getName());

//            nav_Menu = nav.getMenu();
//            nav_Menu.findItem(R.id.signin).setVisible(false);
//            nav_Menu.findItem(R.id.signup).setVisible(false);
//            nav_Menu.findItem(R.id.logout).setVisible(true);
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.dwlayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = findViewById(R.id.nvgView);
        view.setNavigationItemSelectedListener(MainActivity.this);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout,new HomeFragment());
        ft.commit();

        view.setCheckedItem(R.id.home);
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
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent logout = new Intent(getApplicationContext(),Login.class);
                startActivityForResult(logout,121);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        item.setChecked(true);
        setTitle(item.getTitle());
        return true;
    }


}