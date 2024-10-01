package com.example.calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class listDbItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String category;

    public listDbItem(String name, String category) {
        this.name = name;
        this.category = category;
    }
}