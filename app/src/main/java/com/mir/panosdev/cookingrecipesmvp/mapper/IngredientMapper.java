package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponseIngredients;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;



public class IngredientMapper {

    @Inject
    public IngredientMapper(){}

    public List<Ingredient> mapIngredients(IngredientsResponseIngredients[] responseIngredients){
        List<Ingredient> ingredientList = new ArrayList<>();
        if(responseIngredients != null){
            for (IngredientsResponseIngredients ingredients :
                    responseIngredients) {
                Ingredient mIngredient = new Ingredient();
                mIngredient.setId(ingredients.getId());
                mIngredient.setIngredient(ingredients.getIngredient());
                mIngredient.setCategory(ingredients.getCategory());
                ingredientList.add(mIngredient);
            }
        }
        return ingredientList;
    }
}
