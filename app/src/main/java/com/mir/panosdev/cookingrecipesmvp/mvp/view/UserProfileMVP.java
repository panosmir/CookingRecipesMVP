package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

public interface UserProfileMVP {

    interface UserProfileView extends BaseView{
        int getUserId();
        void onRecipesLoaded(List<Recipe> recipes);
        void onClearItems();
        void onCompleteShowToast(String message);
    }

    interface UserFragment{}

    interface Presenter{
        void attachView(UserProfileMVP.UserProfileView view);
        void attachFragment(UserProfileMVP.UserFragment fragment);
        void detachView();
    }
}
