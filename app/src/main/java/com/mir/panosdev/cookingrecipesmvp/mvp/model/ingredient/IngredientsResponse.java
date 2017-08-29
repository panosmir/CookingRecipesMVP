package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

public class IngredientsResponse implements java.io.Serializable {
    private Ingredient[] ingredients;

    public Ingredient[] getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }
}
