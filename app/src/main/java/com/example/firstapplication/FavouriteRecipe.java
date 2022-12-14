package com.example.firstapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"recipe_id"}, unique = true)},
        foreignKeys = {@ForeignKey(entity = Receipt.class,
            parentColumns = "id",
            childColumns = "recipe_id",
            onDelete = ForeignKey.CASCADE)})
public class FavouriteRecipe {
    @PrimaryKey (autoGenerate = true)
    private long entity_id;
    private long user_id;
    private long recipe_id;

    public long getEntity_id() {
        return entity_id;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setEntity_id(long entity_id) {
        this.entity_id = entity_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
