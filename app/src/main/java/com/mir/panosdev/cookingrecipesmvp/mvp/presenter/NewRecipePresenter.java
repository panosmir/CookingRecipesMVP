package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Panos on 4/3/2017.
 */

public class NewRecipePresenter extends BasePresenter<NewRecipeView> implements Observer<Recipe> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject public NewRecipePresenter(){}

    @Inject
    public void addNewRecipe(){
        Observable<Recipe> recipeObservable = mRecipesApiService.addRecipe(getView().getRecipeDetails());
        subscribe(recipeObservable, this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Recipe recipe) {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        getView().onCompletedToast("Your recipe created successfully!!");
    }
}
