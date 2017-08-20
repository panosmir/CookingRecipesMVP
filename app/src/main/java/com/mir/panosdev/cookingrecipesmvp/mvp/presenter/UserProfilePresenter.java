package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.UserProfileMVP;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserProfilePresenter implements UserProfileMVP.Presenter {

    private UserProfileMVP.UserProfileView mView = null;
    private UserProfileMVP.UserFragment mFragment = null;

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
        if (mView != null ) {
            Observable<Response<RecipesResponse>> recipeObservable = mApiService.getUserRecipes(mView.getUserId());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                        @Override
                        public void onNext(Response<RecipesResponse> recipesResponseResponse) {
                            if (recipesResponseResponse.isSuccessful()) {
                                List<Recipe> recipes = mRecipeMapper.mapRecipes(recipesResponseResponse.body().getRecipes());
                                mView.onClearItems();
                                mView.onRecipesLoaded(recipes);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
//                            mView.onCompleteShowToast("YOURS!");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void getUserFavoritesRecipes() {
        if (mView != null) {
            Observable<Response<RecipesResponse>> recipeObservable = mApiService.getUserFavorites(mView.getUserId());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                        @Override
                        public void onNext(Response<RecipesResponse> recipesResponseResponse) {
                            if (recipesResponseResponse.isSuccessful()) {
                                List<Recipe> recipes = mRecipeMapper.mapRecipes(recipesResponseResponse.body().getRecipes());
                                mView.onClearItems();
                                mView.onRecipesLoaded(recipes);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
//                            mView.onCompleteShowToast("FAVORITES!");
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
    public void attachFragment(UserProfileMVP.UserFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
