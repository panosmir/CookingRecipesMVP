package com.mir.panosdev.cookingrecipesmvp.modules.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerSearchComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.SearchModule;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.search.searchAdapter.SearchAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.SearchPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity implements SearchView {

    @Inject
    protected SearchPresenter mSearchPresenter;

    @BindView(R.id.searchRecipeTextView)
    EditText searchText;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.searchRecipeList)
    RecyclerView searchList;

    private SearchAdapter mSearchAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeNavBar();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerSearchComponent.builder()
                .applicationComponent(getApplicationComponent())
                .searchModule(new SearchModule(this))
                .build().inject(this);
    }

    private void initializeList() {
        searchList.setHasFixedSize(true);
        searchList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSearchAdapter = new SearchAdapter(getLayoutInflater());
        mSearchAdapter.setRecipeClickListener(mOnRecipeClickListener);
        searchList.setAdapter(mSearchAdapter);
    }

    private OnRecipeClickListener mOnRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };

    private void initializeNavBar() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recipes:
                        loadRecipes();
                        return true;
                    case R.id.action_search:
                        return true;
                    case R.id.action_profile:
                        userProfile();
                        return true;
                }
                return true;
            }

        });
    }

    private void userProfile() {
        Intent intent = new Intent(SearchActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.searchButton)
    public void searchRecipeById(View view) {
        if (view.getId() == R.id.searchButton) {
            mSearchPresenter.getARecipe();
            initializeList();
        }
    }


    private void loadRecipes() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onShowDialog(String searchString) {
        if (mSearchAdapter != null) {
            showDialog(searchString);
        }
    }


    @Override
    public String searchTitle() {
        if (searchText.getText().toString().isEmpty()) {
            return null;
        } else {
            return searchText.getText().toString();
        }
    }

    @Override
    public void onShowToast(String message) {
        if (mSearchAdapter != null) {
            Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClearItems() {
        if (mSearchAdapter != null) {
            mSearchAdapter.clearRecipes();
        }
    }

    @Override
    public void onRecipeLoaded(List<Recipe> recipes) {
        if (mSearchAdapter != null) {
            mSearchAdapter.addRecipes(recipes);
        }
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }
}