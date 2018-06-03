package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

public interface DetailsActivityMVP {

    interface DetailsView extends BaseView {
        Recipe getRecipeDetails();
        boolean getUpdateSignal();
        void onUpdateShowToast(String message);
        int getCategoryId();
        void onClearIngredients();
        void onIngredientsLoaded(List<Ingredient> ingredientList);
        void onClearItems();
        void onItemsLoaded(List<Category> categories);
    }

    interface DetailsViewActivity extends BaseView{
        Recipe getRecipeDetails();
        boolean getDeleteSignal();
        void onDeleteShowToast(String message);
    }

    interface DetailsViewFragment extends BaseView{
        int getRecipeId();
        String getUsername();
        void onCompletedToast(String s);
        void onErrorToast(String message);
    }

    interface Presenter {
        void attachView(DetailsView view);
        void attachActivity(DetailsViewActivity detailsViewActivity);
        void attachFragment(DetailsViewFragment viewFragment);
        void detachView();
    }
}
