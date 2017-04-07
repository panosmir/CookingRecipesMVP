package com.mir.panosdev.cookingrecipesmvp.modules.listeners;

import android.view.View;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

/**
 * Created by Panos on 4/7/2017.
 */

public interface OnRecipeClickListener {
    void onClick(View v, Recipe recipe, int position);
}
