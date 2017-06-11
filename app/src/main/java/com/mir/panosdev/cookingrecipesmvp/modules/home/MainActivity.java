package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnBottomNavigationClickListener;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnSwipeUpListener;
import com.mir.panosdev.cookingrecipesmvp.modules.login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RecipesPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP;
import com.mir.panosdev.cookingrecipesmvp.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

//// TODO: 4/4/2017 Code cleanup, comments needed.

public class MainActivity extends BaseActivity implements MainActivityMVP.MainView {
    @Inject
    protected RecipesPresenter mRecipesPresenter;

    private RecipeAdapter mRecipeAdapter;

    @BindView(R.id.recipe_list)
    RecyclerView recipesRecyclerView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.mainSwipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        mRecipesPresenter.attachView(this);
        loadRecipes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecipesPresenter.detachView();
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnBottomNavigationListener);
        mSwipeRefreshLayout.setOnRefreshListener(mOnSwipeUpListener);
    }

    @OnClick(R.id.floatingActionButton)
    public void addNewRecipeButtonClick(View view) {
        if (view.getId() == R.id.floatingActionButton) {
            Intent intent = new Intent(MainActivity.this, NewRecipeActivity.class);
            startActivity(intent);
        }
    }

    private OnSwipeUpListener mOnSwipeUpListener = new OnSwipeUpListener() {
        @Override
        public void onRefresh() {
            loadRecipes();
        }
    };

    private OnBottomNavigationClickListener mOnBottomNavigationListener = new OnBottomNavigationClickListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menu) {
            switch (menu.getItemId()){
                case R.id.action_recipes:
                    loadRecipes();
                    return true;
                case R.id.action_search:
                    seachRecipe();
                    return true;
                case R.id.action_profile:
                    userProfile();
                    return true;
            }
            return true;
        }
    };


    private void userProfile() {
        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void loadRecipes() {
        if (NetworkUtils.isNetworkAvailable(this)){
            mRecipesPresenter.getRecipes();
        }else{
            mRecipesPresenter.getRecipesFromDatabase();

        }
        mSwipeRefreshLayout.setRefreshing(false);
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
                .recipesModule(new RecipesModule())
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

    @Override
    public void onNetworkUnavailableToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void seachRecipe() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private OnRecipeClickListener mRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, "recipeAnimation");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenuButton:
                sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
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
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }
}
