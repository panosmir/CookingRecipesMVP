package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnBottomNavigationClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchFragment;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @Inject
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecipes();
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnBottomNavigationListener);
    }

    @OnClick(R.id.floatingActionButton)
    public void addNewRecipeButtonClick(View view) {
        if (view.getId() == R.id.floatingActionButton) {
            Intent intent = new Intent(MainActivity.this, NewRecipeActivity.class);
            startActivity(intent);
        }
    }

    private OnBottomNavigationClickListener mOnBottomNavigationListener = menu -> {
        switch (menu.getItemId()) {
            case R.id.action_recipes:
                getRecipes();
                return true;
            case R.id.action_search:
                searchRecipe();
                return true;
            case R.id.action_profile:
                userProfile();
                return true;
        }
        return true;
    };

    private void getRecipes() {
        FragmentManager manager = getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment();
        manager.beginTransaction().replace(R.id.mainFragmentContainer, mainFragment).commit();
    }


    private void searchRecipe() {
        FragmentManager manager = getSupportFragmentManager();
        SearchFragment searchFragment = new SearchFragment();
        manager.beginTransaction().replace(R.id.mainFragmentContainer, searchFragment).commit();
    }

    private void userProfile() {
        FragmentManager manager = getSupportFragmentManager();
        UserProfileFragment fragment = new UserProfileFragment();
        manager.beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenuButton:
                sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
