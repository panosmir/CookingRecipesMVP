package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

/**
 * Created by Panos on 4/5/2017.
 */

public interface UserProfileView extends BaseView {
    int getUserId();

    void onRecipesLoaded(List<Recipe> recipes);

    void onClearItems();
}
