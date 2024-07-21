package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        String toolbar_text = "TODO";
        toolbarTitle.setText(toolbar_text);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        return super.onOptionsItemSelected(item);
    }
}