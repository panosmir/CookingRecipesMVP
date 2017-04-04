package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

public class RecipesResponse {
    private RecipesResponseRecipes[] recipes;

    public RecipesResponseRecipes[] getRecipes() {
        return this.recipes;
    }

    public void setRecipes(RecipesResponseRecipes[] recipes) {
        this.recipes = recipes;
    }
}
