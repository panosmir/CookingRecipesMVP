package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

/**
 * Created by Panos on 4/17/2017.
 */

public interface DetailsView extends BaseView {

    Recipe getRecipeDetails();

    boolean getDeleteSignal();

    void onDeleteShowToast(String message);
}
