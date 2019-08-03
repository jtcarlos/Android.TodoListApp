package com.example.project_todolistapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // change status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(0xFF1A1A1A);
        }

        // set application toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set navigation icon
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // set onclick listener for navigation drawer
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_today:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_today()).commit();
                        break;
                    case R.id.menu_upcoming:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_upcoming()).commit();
                        break;
                    case R.id.menu_overdue:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_overdue()).commit();
                        break;
                    case R.id.menu_create:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_create()).commit();
                        break;
                    case R.id.menu_header:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_header()).commit();
                        break;
                    case R.id.menu_about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_about()).commit();
                        break;
                }

                mDrawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        // reset back icon colors to default and remove actionbar label
        navigationView.setItemIconTintList(null);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // initialize first fragment
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new fragment_today()).commit();
            navigationView.setCheckedItem(R.id.menu_today);
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
