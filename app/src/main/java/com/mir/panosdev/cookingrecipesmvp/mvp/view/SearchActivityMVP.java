package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

/**
 * Created by Panos on 3/21/2017.
 */

public interface SearchActivityMVP extends BaseView {

    interface SearchView extends BaseView{
        void onShowDialog(String searchString);

        String searchTitle();

        void onShowToast(String message);

        void onClearItems();

        void onRecipeLoaded(List<Recipe> recipes);

        void onHideDialog();
    }

    interface Presenter{
        void attachView(SearchActivityMVP.SearchView view);
        void detachView();
    }

}
