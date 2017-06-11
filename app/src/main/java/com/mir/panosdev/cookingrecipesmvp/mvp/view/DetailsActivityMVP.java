package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

/**
 * Created by Panos on 4/17/2017.
 */

public interface DetailsActivityMVP extends BaseView {

    interface DetailsView extends BaseView {
        Recipe getRecipeDetails();

        void onDeleteShowToast(String message);

        boolean getDeleteSignal();

        boolean getUpdateSignal();

        void onUpdateShowToast(String message);
    }

    interface Presenter {
        void attachView(DetailsView view);
        void detachView();
    }
}
