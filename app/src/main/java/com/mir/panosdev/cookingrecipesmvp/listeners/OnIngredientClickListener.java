package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.view.View;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;

public interface OnIngredientClickListener {
    void onClick(View v, Ingredient ingredient, int position, boolean isClicked);
}
