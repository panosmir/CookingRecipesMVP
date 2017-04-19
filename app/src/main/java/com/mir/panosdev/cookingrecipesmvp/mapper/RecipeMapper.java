package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponseRecipes;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Panos on 3/18/2017.
 */

public class RecipeMapper {

    @Inject
    public RecipeMapper(){}

    public List<Recipe> mapRecipes(Storage storage, RecipesResponseRecipes[] response){
        List<Recipe> recipeList = new ArrayList<>();

        if (response != null){
                for (RecipesResponseRecipes recipe: response) {
                    Recipe mRecipe = new Recipe();
                    mRecipe.setUser(recipe.getUser());
                    mRecipe.setId(recipe.getId());
                    mRecipe.setTitle(recipe.getTitle());
                    mRecipe.setDescription(recipe.getDescription());
                    storage.addRecipe(mRecipe);
                    recipeList.add(mRecipe);
                }
//            }
        }
        return recipeList;
    }

    public List<Recipe> mapResults(RecipesResponseRecipes[] response){
        List<Recipe> recipeList = new ArrayList<>();
        if(response != null){
            for (RecipesResponseRecipes recipe :
                    response) {
                Recipe mRecipe = new Recipe();
                mRecipe.setUser(recipe.getUser());
                mRecipe.setId(recipe.getId());
                mRecipe.setTitle(recipe.getTitle());
                mRecipe.setDescription(recipe.getDescription());
                recipeList.add(mRecipe);
            }
        }
        return recipeList;
    }
}
