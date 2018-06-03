package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.view.View;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

public interface OnRecipeClickListener {
    void onClick(View v, Recipe recipe, int position);
}
