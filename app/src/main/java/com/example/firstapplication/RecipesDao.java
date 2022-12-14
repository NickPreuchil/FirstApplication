package com.example.firstapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipesDao {
    @Query("SELECT * FROM receipt")
    List<Receipt> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Receipt receipt);
}
