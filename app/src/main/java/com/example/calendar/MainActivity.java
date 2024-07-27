package com.example.calendar;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private FileUtil fileUtil;
    private NavigationAction navigationAction;
    private ActivityResultLauncher<Intent> galleryLauncher;
    AppConstants appConstants = new AppConstants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileUtil = new FileUtil(this);
        navigationAction = new NavigationAction(this);

        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationView();
        setupGalleryLauncher();

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
        boolean internalFileExists = fileUtil.doesFileExistInInternalStorage(this, appConstants.INTERNAL_FILE_NAME);
        String nav_goal = "80";
        if (!internalFileExists) {
            fileUtil.saveDataToFile(appConstants.INTERNAL_FILE_NAME, appConstants.CUSTOM_DATA_NAVIGATION_GOAL, nav_goal);
        } else {
            nav_goal = fileUtil.getValueFromFile(appConstants.INTERNAL_FILE_NAME, appConstants.CUSTOM_DATA_NAVIGATION_GOAL);
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
                    navigationAction.showAlertDialogSetGoal(nav_goal);
                    return true;
                } else if (id == R.id.bar_random_list) {
                    Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.bar_change_promise) {
                    Toast.makeText(getApplicationContext(), "C", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.bar_change_theme) {
                    navigationAction.showAlertDialogSetThemeImage(galleryLauncher);
                    return true;
                }

                return false;
            });
        }
    }
    private void setupGalleryLauncher()
    {
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        try {
                            int reqWidth = 800;
                            int reqHeight = 800;

                            Bitmap bitmap = fileUtil.getScaledBitmap(selectedImage, reqWidth, reqHeight);
                            fileUtil.saveImageToFile(MainActivity.this, bitmap, appConstants.INTERNAL_THEME_UPLOAD_IMAGE_NAME);
                            navigationAction.setThemeImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        Toolbar toolbar = findViewById(R.id.includeToolbar);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
