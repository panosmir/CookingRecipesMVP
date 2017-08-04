package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponseRecipes;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RecipeMapper {

    @Inject
    public RecipeMapper(){}

    public List<Recipe> mapRecipesAndStorage(Storage storage, RecipesResponseRecipes[] response){
        List<Recipe> recipeList = new ArrayList<>();

        if (response != null){
                for (RecipesResponseRecipes recipe: response) {
                    Recipe mRecipe = new Recipe();
                    mRecipe.setUser(recipe.getUser());
                    mRecipe.setId(recipe.getId());
                    mRecipe.setTitle(recipe.getTitle());
                    mRecipe.setDescription(recipe.getDescription());
                    mRecipe.setIngredients(recipe.getIngredients());
                    storage.addRecipe(mRecipe);
                    recipeList.add(mRecipe);
                }
        }
        return recipeList;
    }

    public List<Recipe> mapRecipes(RecipesResponseRecipes[] response){
        List<Recipe> recipeList = new ArrayList<>();
        if(response != null){
            for (RecipesResponseRecipes recipe :
                    response) {
                Recipe mRecipe = new Recipe();
                mRecipe.setUser(recipe.getUser());
                mRecipe.setId(recipe.getId());
                mRecipe.setTitle(recipe.getTitle());
                mRecipe.setDescription(recipe.getDescription());
                mRecipe.setIngredients(recipe.getIngredients());
                recipeList.add(mRecipe);
            }
        }
        return recipeList;
    }
}
