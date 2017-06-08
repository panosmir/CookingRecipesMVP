package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

import java.io.Serializable;

/**
 * Created by Panos on 18-May-17.
 */

public class Ingredient implements Serializable{

    private int id;
    private String ingredient;
    private Category category;

    public Ingredient(int id, String ingredient) {
        this.id = id;
        this.ingredient = ingredient;
    }

    public Ingredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
