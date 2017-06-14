package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

/**
 * Created by Panos on 4/3/2017.
 */

public interface NewRecipeMVP {

    interface NewRecipeView extends BaseView{
        Recipe getRecipeDetails();

        void onCompletedToast(String message);

        void onClearItems();

        void onItemsLoaded(List<Category> categories);

        int getCategoryId();

        void onIngredientsLoaded(List<Ingredient> ingredientList);

        void onClearIngredients();
    }

    interface Presenter{
        void attachView(NewRecipeMVP.NewRecipeView view);
        void detachView();
    }

}
