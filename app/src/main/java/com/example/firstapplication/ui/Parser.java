package com.example.firstapplication.ui;

import com.example.firstapplication.Receipt;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parser {
    private String Name;
    private String Ingredients;
    private Integer Calorie;
    private Integer Time;
    private Integer Difficulty;

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
