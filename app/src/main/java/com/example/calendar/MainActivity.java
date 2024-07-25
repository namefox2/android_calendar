package com.example.calendar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    private static final String INTERNAL_FILE_NAME = "customData.txt";
    private static final String CUSTOM_DATA_NAVIGATION_GOAL = "goalNumber";
    private static final String CUSTOM_DATA_APPLICATION_THEME = "theme";
    private DrawerLayout mDrawerLayout;
    private FileUtil fileUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileUtil = new FileUtil(this);
        String nav_goal = initializeNavigationGoal();
        Log.d("TEST", "nav_goal:"+nav_goal);

        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationView(nav_goal);

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
    private String initializeNavigationGoal()
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
    private void setupNavigationView(String nav_goal) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.nav_drawer);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                mDrawerLayout.closeDrawers();
                int id = item.getItemId();
                if (id == R.id.bar_set_goal) {
                    showAlertDialogSetGoalClicked(nav_goal);
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
    public void showAlertDialogSetGoalClicked(String nav_goal)
    {
        Button button_ok;
        Button button_cancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.nav_goal_layout, null);
        builder.setView(customLayout);
        EditText editText = customLayout.findViewById(R.id.nav_goal_percent);

        editText.setText(nav_goal);
        button_ok = customLayout.findViewById(R.id.nav_goal_ok);
        button_cancel = customLayout.findViewById(R.id.nav_goal_cancel);

        AlertDialog dialog = builder.create();

        button_ok.setOnClickListener(v -> handleButtonClick(v, dialog));
        button_cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void handleButtonClick(View view, AlertDialog dialog)
    {
        if (view.getId() == R.id.nav_goal_ok) {
            TextView str_num = dialog.findViewById(R.id.nav_goal_percent);
            assert str_num != null;
            String nav_goal = str_num.getText().toString();
            if (nav_goal.isEmpty()) {
                nav_goal = "80";
            }
            dialog.dismiss();
            fileUtil.saveDataToFile(INTERNAL_FILE_NAME, CUSTOM_DATA_NAVIGATION_GOAL, nav_goal);
            fileUtil.showFileContents(INTERNAL_FILE_NAME, CUSTOM_DATA_NAVIGATION_GOAL);
        }
        else if(view.getId() == R.id.nav_goal_cancel)
        {
            dialog.dismiss();
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
