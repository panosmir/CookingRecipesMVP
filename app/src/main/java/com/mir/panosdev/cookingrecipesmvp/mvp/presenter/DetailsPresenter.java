package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.CategoryMapper;
import com.mir.panosdev.cookingrecipesmvp.mapper.IngredientMapper;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsFragment;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DetailsPresenter implements DetailsActivityMVP.Presenter {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected IngredientMapper mIngredientMapper;

    @Inject
    protected CategoryMapper mCategoryMapper;

    private DetailsActivityMVP.DetailsView mView;
    private DetailsActivityMVP.DetailsViewActivity mDetailsViewActivity;
    private DetailsActivityMVP.DetailsViewFragment mDetailsViewFragment;
    private Completable recipeCompletable;

    protected CompositeDisposable compositeDisposable;

    @Inject
    public DetailsPresenter() {
    }

    @Inject
    public void deleteRecipe() {
        if (mDetailsViewActivity!=null && mDetailsViewActivity.getDeleteSignal()) {
            recipeCompletable= mRecipesApiService.deleteRecipe(mDetailsViewActivity.getRecipeDetails());
            Disposable disposable = recipeCompletable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            mDetailsViewActivity.onDeleteShowToast("Recipe deleted!");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void updateRecipe() {
        if (mView!=null && mView.getUpdateSignal()) {
            recipeCompletable = mRecipesApiService.updateRecipe(mView.getRecipeDetails());
            Disposable disposable = recipeCompletable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            mView.onUpdateShowToast("Recipe updated!");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void fetchCategories() {
        if(mView!=null) {
            Single<Response<Categories>> categoryObservable = mRecipesApiService.getAllCategories();
            Disposable disposable = categoryObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<Categories>>() {
                        @Override
                        public void onSuccess(Response<Categories> categoriesResponse) {
                            List<Category> categories = mCategoryMapper.mapCategories(categoriesResponse.body().getCategories());
                            if (categories != null) {
                                mView.onClearItems();
                                mView.onItemsLoaded(categories);
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.d("ERROR_LOG", "ERROR------->" + e.getMessage());
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void fetchIngredients(){
        if (mView!=null && mView.getCategoryId() != 0) {
            Single<Response<IngredientsResponse>> responseObservable = mRecipesApiService.getIngredientsById(mView.getCategoryId());
            Disposable disposable = responseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<Response<IngredientsResponse>>() {
                        @Override
                        public void onSuccess(Response<IngredientsResponse> ingredientsResponseResponse) {
                            List<Ingredient> ingredientList = mIngredientMapper.mapIngredients(ingredientsResponseResponse.body().getIngredients());
                            mView.onClearIngredients();
                            mView.onIngredientsLoaded(ingredientList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }
                    });
            if(compositeDisposable!=null)
                compositeDisposable.add(disposable);
        } else
            return;
    }

    @Inject
    public void addFavorite(){
        if (mDetailsViewFragment != null) {
            Completable completable = mRecipesApiService.addFavorite(mDetailsViewFragment.getRecipeId(), mDetailsViewFragment.getUsername());
            Disposable disposable = completable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            mDetailsViewFragment.onCompletedToast("Bookmarked!");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mDetailsViewFragment.onErrorToast(throwable.getMessage());
                        }
                    });
            if(compositeDisposable!=null)
                compositeDisposable.add(disposable);
        }
    }

    public void removeFavorite() {
        if (mDetailsViewFragment != null) {
            Completable completable = mRecipesApiService.removeFavorite(mDetailsViewFragment.getRecipeId(), mDetailsViewFragment.getUsername());
            Disposable disposable = completable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            mDetailsViewFragment.onCompletedToast("UnFavorited!");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mDetailsViewFragment.onErrorToast(throwable.getMessage());
                        }
                    });
            if(compositeDisposable!=null)
                compositeDisposable.add(disposable);
        }
    }

    @Override
    public void attachView(DetailsActivityMVP.DetailsView view) {
        mView = view;
    }

    @Override
    public void attachActivity(DetailsActivityMVP.DetailsViewActivity detailsViewActivity) {
        mDetailsViewActivity = detailsViewActivity;
    }

    @Override
    public void attachFragment(DetailsActivityMVP.DetailsViewFragment viewFragment) {
        mDetailsViewFragment = viewFragment;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
        mDetailsViewActivity = null;
    }
}
