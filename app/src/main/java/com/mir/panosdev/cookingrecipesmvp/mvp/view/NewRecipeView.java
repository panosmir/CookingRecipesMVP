package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

/**
 * Created by Panos on 4/3/2017.
 */

public interface NewRecipeView extends BaseView {
    Recipe getRecipeDetails();

    void onCompletedToast(String message);

}
