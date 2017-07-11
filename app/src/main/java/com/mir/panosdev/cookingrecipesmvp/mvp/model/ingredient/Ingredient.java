package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

import com.google.gson.annotations.SerializedName;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

import java.io.Serializable;

/**
 * Created by Panos on 18-May-17.
 */

public class Ingredient implements Serializable{

    private int id;
    private String ingredient;
    private Category category;
    @SerializedName(value = "quantity")
    private String quantity;

    public Ingredient(int id, String ingredient, String quantity) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
