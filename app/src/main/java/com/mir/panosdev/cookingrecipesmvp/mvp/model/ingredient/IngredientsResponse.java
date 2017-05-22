package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient;

public class IngredientsResponse implements java.io.Serializable {
    private IngredientsResponseIngredients[] ingredients;

    public IngredientsResponseIngredients[] getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(IngredientsResponseIngredients[] ingredients) {
        this.ingredients = ingredients;
    }
}
