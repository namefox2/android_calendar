package com.example.calendar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity
{
    private static final String INTERNAL_FILE_NAME = "customData.txt";
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    String percent_num = "80";
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean internalFileExists = FileUtil.doesFileExistInInternalStorage(this, INTERNAL_FILE_NAME);
        if(!internalFileExists)
        {
            saveDataToFile(INTERNAL_FILE_NAME, "percent_num", percent_num);
        }
        percent_num = getValueFromFile(INTERNAL_FILE_NAME, "percent_num");
        Log.d("TEST", percent_num);

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
                mDrawerLayout.closeDrawers();
                int id = item.getItemId();
                if (id == R.id.bar_success_percent) {
                    showAlertDialogPercentClicked(item);
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

    public void showAlertDialogPercentClicked(MenuItem item)
    {
        Button button_ok;
        Button button_cancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.success_percent, null);
        builder.setView(customLayout);
        EditText editText = customLayout.findViewById(R.id.success_number);

        editText.setText(percent_num);
        button_ok = customLayout.findViewById(R.id.success_ok);
        button_cancel = customLayout.findViewById(R.id.success_cancel);

        View.OnClickListener buttonClickListener = this::handleButtonClick;

        button_ok.setOnClickListener(buttonClickListener);
        button_cancel.setOnClickListener(buttonClickListener);

        dialog = builder.create();
        dialog.show();
    }

    private void handleButtonClick(View view)
    {
        if (view.getId() == R.id.success_ok) {
            TextView str_num = dialog.findViewById(R.id.success_number);
            assert str_num != null;
            percent_num = str_num.getText().toString();
            if (percent_num.isEmpty()) {
                percent_num = "80";
            }
            dialog.dismiss();
            saveDataToFile(INTERNAL_FILE_NAME, "percent_num", percent_num);
            showFileContents();
        }
        else if(view.getId() == R.id.success_cancel)
        {
            dialog.dismiss();
        }
    }
    public void saveDataToFile(String fileName, String key, String value)
    {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            osw.write(key+"="+value+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(osw != null)
            {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getValueFromFile(String fileName, String key)
    {
        String value = null;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput(fileName);
            isr = new InputStreamReader(fis);
            reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("=");
                if(parts.length == 2 && parts[0].equals(key))
                {
                    value = parts[1];
                    break;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
    public void showFileContents() {
        String fileContents = getValueFromFile(INTERNAL_FILE_NAME, "percent_num");
        if (fileContents != null) {
            Toast.makeText(this, "File Contents: " + fileContents, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No data found in file.", Toast.LENGTH_LONG).show();
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
class FileUtil {
    public static boolean doesFileExistInInternalStorage(Context context, String fileName) {
        String[] fileList = context.fileList();
        for (String file : fileList) {
            if (file.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
    public static boolean doesFileExistInExternalStorage(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}