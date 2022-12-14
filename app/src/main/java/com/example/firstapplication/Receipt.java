package com.example.firstapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Receipt {

    @PrimaryKey (autoGenerate = true)
    private long id;
    private String Name;
    private String Ingredients;
    private Integer Calorie;
    private Integer Time;
    private Integer Difficulty;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getCalorie() {
        return Calorie;
    }

    public Integer getDifficulty() {
        return Difficulty;
    }

    public Integer getTime() {
        return Time;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setCalorie(Integer calorie) {
        Calorie = calorie;
    }

    public void setDifficulty(Integer difficulty) {
        Difficulty = difficulty;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public void setTime(Integer time) {
        Time = time;
    }
}
