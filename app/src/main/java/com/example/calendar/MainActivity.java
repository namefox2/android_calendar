package com.example.calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        navigationView = findViewById(R.id.navigationView);


//        TextView toolbarTitle = findViewById(R.id.toolbar_title);
//        String toolbar_text = "TODO";
//        toolbarTitle.setText(toolbar_text);

//        if(getSupportActionBar() != null)
//        {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null)
//        {
//            actionBar.setTitle("YOU CAN DO IT");
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
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
        if(id == R.id.bar_success_percent)
        {

            return true;
        }
        else if(id == R.id.bar_random_list)
        {
            return true;
        }
        else if(id == R.id.bar_change_promise)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}