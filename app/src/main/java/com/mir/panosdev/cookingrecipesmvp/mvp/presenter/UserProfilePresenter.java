package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.UserProfileMVP;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Panos on 4/5/2017.
 */

public class UserProfilePresenter implements UserProfileMVP.Presenter {

    private UserProfileMVP.UserProfileView mView;
    protected CompositeDisposable compositeDisposable;

    @Inject
    protected RecipesApiService mApiService;
    @Inject
    protected RecipeMapper mRecipeMapper;

    @Inject
    public UserProfilePresenter() {
    }

    @Inject
    public void getUserRecipes() {
        if (mView != null) {
            Observable<Response<RecipesResponse>> recipeObservable = mApiService.getUserRecipes(mView.getUserId());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                        @Override
                        public void onNext(Response<RecipesResponse> recipesResponseResponse) {
                            if (recipesResponseResponse.isSuccessful()) {
                                List<Recipe> recipes = mRecipeMapper.mapResults(recipesResponseResponse.body().getRecipes());
                                mView.onClearItems();
                                mView.onRecipesLoaded(recipes);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Override
    public void attachView(UserProfileMVP.UserProfileView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
