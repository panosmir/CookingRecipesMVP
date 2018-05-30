package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecipeMapper {

    @Inject
    public RecipeMapper(){}

    public List<Recipe> mapRecipesAndStorage(Storage storage, Recipe[] response){
        List<Recipe> recipeList = new ArrayList<>();

        if (response != null){
                for (Recipe recipe: response) {
                    Recipe mRecipe = new Recipe();
                    mRecipe.setUser(recipe.getUser());
                    mRecipe.setId(recipe.getId());
                    mRecipe.setTitle(recipe.getTitle());
                    mRecipe.setDescription(recipe.getDescription());
                    mRecipe.setIngredients(recipe.getIngredients());
                    mRecipe.setFavorites(recipe.getFavorites());
                    storage.addRecipe(mRecipe);
                    recipeList.add(mRecipe);
                }
        }
        return recipeList;
    }

    public List<Recipe> mapRecipes(Recipe[] response){
        List<Recipe> recipeList = new ArrayList<>();
        if(response != null){
            for (Recipe recipe :
                    response) {
                Recipe mRecipe = new Recipe();
                mRecipe.setUser(recipe.getUser());
                mRecipe.setId(recipe.getId());
                mRecipe.setTitle(recipe.getTitle());
                mRecipe.setDescription(recipe.getDescription());
                mRecipe.setIngredients(recipe.getIngredients());
                mRecipe.setFavorites(recipe.getFavorites());
                recipeList.add(mRecipe);
            }
        }
        return recipeList;
    }
}
