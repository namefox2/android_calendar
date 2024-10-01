package com.example.calendar;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBdao {

    @Insert
    void insert(listDbItem item);

    @Query("SELECT * FROM items")
    List<listDbItem> getAllItems();
}