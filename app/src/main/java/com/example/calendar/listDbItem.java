package com.example.calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "items")
public class listDbItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String category;
    public String link;
//    public List<String> tagList;

    public listDbItem(String name, String category, String link) {
        this.name = name;
        this.category = category;
        this.link = link;
    }
}