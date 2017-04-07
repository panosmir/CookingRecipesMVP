package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RecipesPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

//// TODO: 4/4/2017 Code cleanup, comments needed.
public class MainActivity extends BaseActivity implements MainView{

    @Inject protected RecipesPresenter mRecipesPresenter;

    private RecipeAdapter mRecipeAdapter;

    @BindView(R.id.recipe_list)
    RecyclerView recipesRecyclerView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadRecipes();
        initializeNavBar();
    }

    @OnClick(R.id.floatingActionButton)
    public void addNewRecipeButtonClick(View view){
        if (view.getId() == R.id.floatingActionButton){
            Intent intent = new Intent(MainActivity.this, NewRecipeActivity.class);
            startActivity(intent);
        }
    }

    private void initializeNavBar() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_recipes:
                        loadRecipes();
                        return true;
                    case R.id.action_search:
                        seachRecipe();
                        return true;
                    case R.id.action_profile:
                        userLogin();
                }
                return true;
            }
        });
    }

    private void userLogin() {
        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }


    public void loadRecipes() {
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

    private void seachRecipe(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private OnRecipeClickListener mRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenuButton:
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
