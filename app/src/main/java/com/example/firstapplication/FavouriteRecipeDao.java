package com.example.firstapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteRecipeDao {
    @Query("SELECT recipe_id FROM favouriterecipe WHERE user_id = :id")
    long[] getReceiptsIdByUser(long id);

    @Query("SELECT * FROM receipt WHERE id IN (SELECT recipe_id FROM favouriterecipe WHERE user_id = :id)")
    List<Receipt> getReceiptsByUser(long id);

    @Query("DELETE FROM favouriterecipe WHERE user_id = :id")
    void flushUserFavourites(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavouriteRecipe favouriteRecipe);
}
