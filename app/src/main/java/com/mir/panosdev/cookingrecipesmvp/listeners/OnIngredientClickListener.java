package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.view.View;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;

/**
 * Created by Panos on 22-May-17.
 */

public interface OnIngredientClickListener {
    void onClick(View v, Ingredient ingredient, int position, boolean isClicked);
}
