package com.example.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class NavigationAction {
    private final Context context;

    public NavigationAction(Context context)
    {
        this.context = context;
    }

    public void showAlertDialogSetGoal(String fileName, String key, String nav_goal)
    {
        Button button_ok;
        Button button_cancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.nav_goal_layout, null);
        builder.setView(customLayout);
        EditText editText = customLayout.findViewById(R.id.nav_goal_percent);

        editText.setText(nav_goal);
        button_ok = customLayout.findViewById(R.id.nav_goal_ok);
        button_cancel = customLayout.findViewById(R.id.nav_goal_cancel);

        AlertDialog dialog = builder.create();

        button_ok.setOnClickListener(v -> handleGoalButtonClick(v, dialog, fileName, key));
        button_cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void handleGoalButtonClick(View view, AlertDialog dialog, String fileName, String key)
    {
        FileUtil fileUtil = new FileUtil(context);
        if (view.getId() == R.id.nav_goal_ok) {
            TextView str_num = dialog.findViewById(R.id.nav_goal_percent);
            assert str_num != null;
            String nav_goal = str_num.getText().toString();
            if (nav_goal.isEmpty()) {
                nav_goal = "80";
            }
            dialog.dismiss();
            fileUtil.saveDataToFile(fileName, key, nav_goal);
            fileUtil.showFileContents(fileName, key);
        }
        else if(view.getId() == R.id.nav_goal_cancel)
        {
            dialog.dismiss();
        }
    }
}
