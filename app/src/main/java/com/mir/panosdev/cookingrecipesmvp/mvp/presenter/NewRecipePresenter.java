package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.util.Log;
import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.CategoryMapper;
import com.mir.panosdev.cookingrecipesmvp.mapper.IngredientMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeMVP;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewRecipePresenter implements NewRecipeMVP.Presenter {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected CategoryMapper mCategoryMapper;

    @Inject
    protected IngredientMapper mIngredientMapper;

    private NewRecipeMVP.NewRecipeView mView;
    protected CompositeDisposable compositeDisposable;

    @Inject
    public NewRecipePresenter() {
    }


    @Inject
    public void addNewRecipe() {
        if (mView != null) {
            Completable recipeObservable = mRecipesApiService.addRecipe(mView.getRecipeDetails());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableCompletableObserver() {
                        @Override
                        public void onError(Throwable e) {
                            Log.e("ERROR_LOG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            mView.onCompletedToast("Your recipe created successfully!!");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void fetchCategories() {
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

    @Inject
    public void fetchIngredients() {
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
                        }
                    });
            if(compositeDisposable!=null)
                compositeDisposable.add(disposable);
        } else
            return;
    }

    @Override
    public void attachView(NewRecipeMVP.NewRecipeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable!=null)
            compositeDisposable.dispose();
        mView = null;
    }
}
