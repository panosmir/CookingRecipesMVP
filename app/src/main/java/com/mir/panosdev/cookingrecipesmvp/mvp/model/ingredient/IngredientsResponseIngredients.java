package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

public class IngredientsResponseIngredients implements java.io.Serializable {
    private static final long serialVersionUID = 2479905863658673040L;
    private String ingredient;
    private int id;
    private Category category;

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
