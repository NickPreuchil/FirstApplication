package com.example.firstapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Receipt.class, User.class, FavouriteRecipe.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipesDao recipesDao();
    public abstract UserDao userDao();
    public abstract FavouriteRecipeDao favouriteRecipeDao();
}
