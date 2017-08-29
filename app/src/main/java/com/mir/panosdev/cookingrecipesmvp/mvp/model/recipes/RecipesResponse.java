package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

public class RecipesResponse {
    private Recipe[] recipes;

    public Recipe[] getRecipes() {
        return this.recipes;
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }
}
