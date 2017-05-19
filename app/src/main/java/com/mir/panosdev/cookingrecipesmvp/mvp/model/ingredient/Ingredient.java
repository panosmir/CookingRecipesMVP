package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

/**
 * Created by Panos on 18-May-17.
 */

public class Ingredient {

    private int ingredient_id;
    private String ingredient;
    private Category category;

    public Ingredient(int ingredient_id, String ingredient) {
        this.ingredient_id = ingredient_id;
        this.ingredient = ingredient;
    }

    public Ingredient() {
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
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
