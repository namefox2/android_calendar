package com.example.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

public class NavigationAction {
    private final Context context;
    AppConstants appConstants = new AppConstants();
    ImageView themeImageView;
    FileUtil fileUtil;
    public NavigationAction(Context context)
    {
        this.context = context;
        this.fileUtil = new FileUtil(context);
    }

    public void showAlertDialogSetGoal(String nav_goal)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.nav_goal_layout, null);
        builder.setView(customLayout);

        EditText editText = customLayout.findViewById(R.id.nav_goal_percent);
        editText.setText(nav_goal);

        Button button_ok = customLayout.findViewById(R.id.nav_goal_ok);
        Button button_cancel = customLayout.findViewById(R.id.nav_goal_cancel);

        AlertDialog dialog = builder.create();

        button_ok.setOnClickListener(v -> handleGoalButtonClick(v, dialog));
        button_cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    public void showAlertDialogSetThemeImage(ActivityResultLauncher<Intent> galleryLauncher)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.nav_change_theme, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();

        Button button_ok = customLayout.findViewById(R.id.nav_theme_ok);
        Button button_cancel = customLayout.findViewById(R.id.nav_theme_cancel);
        Button image_upload_button = customLayout.findViewById(R.id.nav_theme_upload);

        themeImageView = customLayout.findViewById(R.id.nav_theme_view);
        new Thread(() -> {
            Bitmap bitmap = fileUtil.loadImageFromFile(context, appConstants.INTERNAL_THEME_CURRENT_IMAGE_NAME);
            ((Activity) context).runOnUiThread(()-> {
                if (bitmap == null) {
                    Drawable drawable = ResourcesCompat.getDrawable(customLayout.getResources(), R.drawable.default_theme, null);
                    themeImageView.setImageDrawable(drawable);
                } else themeImageView.setImageBitmap(bitmap);
            });
        }).start();

        new Thread(() -> image_upload_button.setOnClickListener(v -> openGallery(galleryLauncher))).start();
        button_ok.setOnClickListener(v -> handleThemeButtonClick(v, dialog));
        button_cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    private void handleGoalButtonClick(View view, AlertDialog dialog)
    {
        if (view.getId() == R.id.nav_goal_ok) {
            TextView str_num = dialog.findViewById(R.id.nav_goal_percent);
            if(str_num != null) {
                String nav_goal = str_num.getText().toString();

                int navGoalInt = Integer.parseInt(nav_goal);
                if (navGoalInt < 0 || navGoalInt > 100) {
                    Toast.makeText(context.getApplicationContext(), "0~100 사이 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nav_goal.isEmpty()) {
                    nav_goal = "80";
                }
                fileUtil.saveDataToFile(appConstants.INTERNAL_FILE_NAME, appConstants.CUSTOM_DATA_NAVIGATION_GOAL, nav_goal);
            }
            dialog.dismiss();
        }
        else if(view.getId() == R.id.nav_goal_cancel)
        {
            dialog.dismiss();
        }
    }
    private void handleThemeButtonClick(View view, AlertDialog dialog)
    {
        if (view.getId() == R.id.nav_theme_ok) {
            new Thread(()-> {
                int reqWidth = 800;
                int reqHeight = 800;
                Bitmap bitmap = fileUtil.loadImageFromFile(context, appConstants.INTERNAL_THEME_UPLOAD_IMAGE_NAME);
                ((Activity) context).runOnUiThread(()->{
                    if (bitmap != null) {
                        Bitmap resizeBitmap = fileUtil.getResizedBitmap(bitmap, reqWidth, reqHeight);
                        fileUtil.saveImageToFile(context, resizeBitmap, appConstants.INTERNAL_THEME_CURRENT_IMAGE_NAME);
                    }
                    dialog.dismiss();
                });
            }).start();
        }
        else if(view.getId() == R.id.nav_theme_cancel)
        {
            dialog.dismiss();
        }
    }
    public void openGallery(ActivityResultLauncher<Intent> galleryLauncher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }
    public void setThemeImageView(Bitmap bitmap)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.nav_change_theme, null);
        builder.setView(customLayout);

        if(bitmap != null)
            themeImageView.setImageBitmap(bitmap);
    }
}
