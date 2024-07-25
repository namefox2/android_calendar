package com.example.calendar;

import androidx.activity.OnBackPressedCallback;
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
    private static final String INTERNAL_FILE_NAME = "customData.txt";
    private static final String CUSTOM_DATA_NAVIGATION_GOAL = "goalNumber";
    private static final String CUSTOM_DATA_APPLICATION_THEME = "theme";
    private DrawerLayout mDrawerLayout;
    private FileUtil fileUtil;
    private NavigationAction navigationAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileUtil = new FileUtil(this);
        navigationAction = new NavigationAction(this);

        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationView();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });
    }
    private String getNavigationGoal()
    {
        boolean internalFileExists = fileUtil.doesFileExistInInternalStorage(this, INTERNAL_FILE_NAME);
        String nav_goal = "80";
        if (!internalFileExists) {
            fileUtil.saveDataToFile(INTERNAL_FILE_NAME, CUSTOM_DATA_NAVIGATION_GOAL, nav_goal);
        } else {
            nav_goal = fileUtil.getValueFromFile(INTERNAL_FILE_NAME, CUSTOM_DATA_NAVIGATION_GOAL);
        }
        return nav_goal;
    }
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.includeToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_bar);
        }
    }
    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.nav_drawer);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                mDrawerLayout.closeDrawers();
                int id = item.getItemId();
                if (id == R.id.bar_set_goal) {
                    String nav_goal = getNavigationGoal();
                    Log.d("TEST", "nav_goa1:l"+nav_goal);
                    navigationAction.showAlertDialogSetGoal(INTERNAL_FILE_NAME, CUSTOM_DATA_NAVIGATION_GOAL, nav_goal);
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
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
