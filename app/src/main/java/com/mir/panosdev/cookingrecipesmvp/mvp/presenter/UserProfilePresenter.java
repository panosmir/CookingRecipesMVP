package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.UserProfileView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Panos on 4/5/2017.
 */

public class UserProfilePresenter extends BasePresenter<UserProfileView> implements Observer<RecipesResponse> {

    @Inject protected RecipesApiService mApiService;
    @Inject protected RecipeMapper mRecipeMapper;
    @Inject public UserProfilePresenter(){}

    @Inject
    public void getUserRecipes(){
        Observable<RecipesResponse> recipeObservable = mApiService.getUserRecipes(getView().getUserId());
        subscribe(recipeObservable, this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(RecipesResponse recipesResponse) {
        List<Recipe> recipes = mRecipeMapper.mapRecipes(recipesResponse);
        getView().onClearItems();
        getView().onRecipesLoaded(recipes);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
