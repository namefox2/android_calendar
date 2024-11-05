package com.example.calendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;
public class randomListPage extends AppCompatActivity {

    private AppDatabase db;
    List<listDbItem> itemList;
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.randomlistpage);

        Toolbar toolbar = findViewById(R.id.listToolbar);
        setSupportActionBar(toolbar);

        db = Room.databaseBuilder(this, AppDatabase.class,
                "app_database").allowMainThreadQueries().build();
        itemList = db.itemDao().getAllItems();
        listItemAdapter itemAdapter = new listItemAdapter(itemList);

        ImageButton imageButton = findViewById(R.id.list_plus);

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.addlist, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(randomListPage.this);
                builder.setView(dialogView);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText category = dialogView.findViewById(R.id.item_category);
                        EditText name = dialogView.findViewById(R.id.item_name);
                        EditText link = dialogView.findViewById(R.id.item_link);
                        String category_v = category.getText().toString();
                        String name_v = name.getText().toString();
                        String link_v = link.getText().toString();

                        db.itemDao().insert(new listDbItem(category_v, name_v, link_v));
                        itemList = db.itemDao().getAllItems();
                        itemAdapter.setItems(itemList);
                        itemAdapter.notifyItemInserted(itemList.size());

//                        Log.d("TEST", category_v);
                        //remove
//                        itemList.remove(position);
//                        adapter.notifyItemRemoved(position);
                        //edit
//                        itemList.set(position, newItem);
//                        adapter.notifyItemChanged(position);
                        //move
//                        Collections.swap(itemList, fromPosition, toPosition);
//                        adapter.notifyItemMoved(fromPosition, toPosition);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int color = ContextCompat.getColor(this, R.color.grayMuteColor);
        int height = 2;
        recyclerView.addItemDecoration(new CustomDividerItemDecoration(color, height));
        recyclerView.setAdapter(itemAdapter);
    }

//    @Entity(tableName = "items")
//    public static class listDatabase {
//
//        @PrimaryKey(autoGenerate = true)
//        public int id;
//
//        public String name;
//
//        public listDatabase(String name) {
//            this.name = name;
//        }
//    }
}
