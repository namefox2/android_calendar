package com.example.calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_bar);
        }

        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.nav_drawer);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                Log.d("TEST2", "IN");
                int id = item.getItemId();
                if (id == R.id.bar_success_percent) {
                    Toast.makeText(getApplicationContext(), "A", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.bar_random_list) {
                    Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.bar_change_promise) {
                    Toast.makeText(getApplicationContext(), "C", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        toolbar.getMenu().clear();

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            Log.d("TEST", "IN");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}