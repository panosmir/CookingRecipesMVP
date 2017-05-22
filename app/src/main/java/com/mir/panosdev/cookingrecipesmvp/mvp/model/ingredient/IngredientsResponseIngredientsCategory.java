package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

public class IngredientsResponseIngredientsCategory extends Category implements java.io.Serializable {
    private static final long serialVersionUID = 3482750730568224331L;
    private int id;
    private String category;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
