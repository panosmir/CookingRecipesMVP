package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Panos on 3/21/2017.
 */

public class SearchPresenter extends BasePresenter<SearchView> implements Observer<RecipesResponse> {

    @Inject protected RecipesApiService mRecipesApiService;

    @Inject protected RecipeMapper mRecipeMapper;

    @Inject public SearchPresenter(){}

    @Inject
    public void getARecipe(){
        getView().onShowDialog("Searching for this recipe.....");
        Observable<RecipesResponse> recipesResponseObservable = mRecipesApiService.getARecipe(getView().searchTitle());
        subscribe(recipesResponseObservable, this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(RecipesResponse recipesResponse) {
        List<Recipe> recipes = mRecipeMapper.mapRecipes(recipesResponse);
        getView().onClearItems();
        getView().onRecipeLoaded(recipes);
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading recipes " + e.getMessage());
    }

    @Override
    public void onComplete() {
        getView().onHideDialog();
        getView().onShowToast("Recipe found!!!");
    }
}
