package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchActivityMVP;
import java.net.HttpURLConnection;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SearchPresenter implements SearchActivityMVP.Presenter {

    private SearchActivityMVP.SearchView mView;
    private CompositeDisposable compositeDisposable = null;

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected RecipeMapper mRecipeMapper;

    @Inject
    public SearchPresenter() {
    }

    @Inject
    public void getARecipe() {
        if (mView != null) {
            Observable<Response<RecipesResponse>> recipesResponseObservable = mRecipesApiService.getARecipe(mView.searchTitle());
            Disposable disposable = recipesResponseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                        @Override
                        public void onNext(Response<RecipesResponse> recipesResponseResponse) {
                            if(recipesResponseResponse.isSuccessful()) {
                                List<Recipe> recipes = mRecipeMapper.mapRecipes(recipesResponseResponse.body().getRecipes());
                                mView.onClearItems();
                                mView.onRecipeLoaded(recipes);
                                mView.onHideDialog();
                                mView.onShowToast("Recipe found!!!");
                            }
                            else if(recipesResponseResponse.code() == HttpURLConnection.HTTP_NOT_FOUND){
                                mView.onHideDialog();
                                mView.onShowToast("Recipe not found.");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.onHideDialog();
                            mView.onShowToast("Error loading recipes " + e.getMessage());
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
    public void attachView(SearchActivityMVP.SearchView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
