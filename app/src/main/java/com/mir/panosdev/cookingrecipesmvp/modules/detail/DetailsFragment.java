package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.AddedIngredientsAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsFragment extends BaseFragment implements DetailsActivityMVP.DetailsView {
    public static final String RECIPE = "recipe";

    @BindView(R.id.recipeDescriptionDetail)
    TextView mRecipeDescription;

    @BindView(R.id.ingredientsRecyclerView)
    RecyclerView mRecyclerView;

    @Inject
    SharedPreferences mPrefs;

    @Inject
    protected DetailsPresenter mPresenter;

    private Recipe mRecipe;
    private AddedIngredientsAdapter mIngredientsAdapter;
    private int userId;
    private boolean isReadyForDelete = false, isReadyForUpdate = false;

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            mRecipe = (Recipe) getArguments().getSerializable("OLD_RECIPE");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPrefs = getActivity().getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
        mRecipe = (Recipe) getActivity().getIntent().getSerializableExtra(RECIPE);
        mRecipeDescription.setText(mRecipe.getDescription());
        initializeList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecipeDescription.setTransitionName("recipeAnimation");
        }

    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mIngredientsAdapter = new AddedIngredientsAdapter(getActivity().getLayoutInflater());
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mIngredientsAdapter.addedIngredients(mRecipe.getIngredients());
    }

    @Override
    public Recipe getRecipeDetails() {
        return null;
    }

    @Override
    public void onDeleteShowToast(String message) {
    }

    @Override
    public boolean getDeleteSignal() {
        return false;
    }

    @Override
    public boolean getUpdateSignal() {
        return false;
    }

    @Override
    public void onUpdateShowToast(String message) {

    }

    @Override
    public int getCategoryId() {
        return 0;
    }

    @Override
    public void onClearIngredients() {

    }

    @Override
    public void onIngredientsLoaded(List<Ingredient> ingredientList) {

    }

    @Override
    public void onClearItems() {

    }

    @Override
    public void onItemsLoaded(List<Category> categories) {

    }
}
