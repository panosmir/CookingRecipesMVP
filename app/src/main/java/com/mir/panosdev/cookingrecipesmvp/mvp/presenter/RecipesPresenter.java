package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.os.Handler;
import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by Panos on 3/18/2017.
 */

public class RecipesPresenter extends BasePresenter<MainView> implements Observer<Response<RecipesResponse>> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected RecipeMapper mRecipeMapper;
    @Inject
    protected Storage mStorage;


    @Inject
    public RecipesPresenter() {
    }

    @Inject
    public void getRecipes() {
//        getView().onShowDialog("Loading recipes....");
        Observable<Response<RecipesResponse>> recipesResponseObservable = mRecipesApiService.getRecipes();
        subscribe(recipesResponseObservable, this);
        mStorage.dropDatabase();
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Response<RecipesResponse> recipesResponse) {
        List<Recipe> recipes = mRecipeMapper.mapRecipes(mStorage, recipesResponse.body().getRecipes());
        getView().onClearItems();
        getView().onRecipeLoaded(recipes);
    }

    public void getRecipesFromDatabase() {
        List<Recipe> recipes = mStorage.getSavedRecipes();
        getView().onClearItems();
        getView().onRecipeLoaded(recipes);
        getView().onNetworkUnavailableToast("Updating items from database...");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading recipes " + e.getMessage());
    }

    @Override
    public void onComplete() {
        getView().onHideDialog();
        getView().onShowToast("Sync completed!");

    }
}
