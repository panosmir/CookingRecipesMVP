package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.util.List;

/**
 * Created by Panos on 3/18/2017.
 */

public interface MainView extends BaseView {
    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onRecipeLoaded(List<Recipe> recipes);

    void onClearItems();
}
