package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RecipesPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView{

    @Inject protected RecipesPresenter mRecipesPresenter;

    private RecipeAdapter mRecipeAdapter;

    @BindView(R.id.recipe_list)
    RecyclerView recipesRecyclerView;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadRecipes();
    }

    private void loadRecipes() {
        mRecipesPresenter.getRecipes();
    }

    private void initializeList() {
        recipesRecyclerView.setHasFixedSize(true);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecipeAdapter = new RecipeAdapter(getLayoutInflater());
        mRecipeAdapter.setRecipeClickListener(mRecipeClickListener);
        recipesRecyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
        .applicationComponent(getApplicationComponent())
        .recipesModule(new RecipesModule(this))
        .build().inject(this);
    }



    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeLoaded(List<Recipe> recipes) {
        mRecipeAdapter.addRecipes(recipes);
    }

    @Override
    public void onClearItems() {
        mRecipeAdapter.clearRecipes();
    }

    private RecipeAdapter.OnRecipeClickListener mRecipeClickListener = new RecipeAdapter.OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };
}
