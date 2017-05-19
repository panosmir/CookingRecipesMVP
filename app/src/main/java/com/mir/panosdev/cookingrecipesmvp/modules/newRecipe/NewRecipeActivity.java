package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerNewRecipeComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.NewRecipeModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.CategoryAdapter.CategoryAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.NewRecipePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Panos on 4/3/2017.
 */

public class NewRecipeActivity extends BaseActivity implements NewRecipeView {

    @BindView(R.id.addRecipeTitleEditText)
    EditText addRecipeTitle;

    @BindView(R.id.addRecipeDescriptionEditText)
    EditText addRecipeDescription;

    @BindView(R.id.categorySpinner)
    Spinner spinner;

    @Inject
    protected NewRecipePresenter mNewRecipePresenter;

    User user = new User();
    CategoryAdapter mCategoryAdapter;
    List<Category> mCategories = new ArrayList<>();

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        mCategoryAdapter = new CategoryAdapter(mCategories, this);
        spinner.setAdapter( mCategoryAdapter);
        mNewRecipePresenter.fetchCategories();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerNewRecipeComponent.builder().applicationComponent(
                getApplicationComponent()
        ).newRecipeModule(new NewRecipeModule(this))
        .build().inject(this);
    }

    @OnClick(R.id.addRecipeButton)
    public void addRecipeButtonClick(View view){
        if (view.getId() == R.id.addRecipeButton){
            mNewRecipePresenter.addNewRecipe();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_recipe;
    }

    @Override
    public Recipe getRecipeDetails() {
        SharedPreferences mSharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        Recipe recipe = new Recipe();
        user.setId(mSharedPreferences.getInt("USER_ID", 0));
        recipe.setUser(user);
        if(!addRecipeTitle.getText().toString().isEmpty() || !addRecipeDescription.getText().toString().isEmpty()) {
            recipe.setTitle(addRecipeTitle.getText().toString());
            recipe.setDescription(addRecipeDescription.getText().toString());
            return recipe;
        }
        return null;
    }

    @Override
    public void onCompletedToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewRecipeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClearItems() {
        if(mCategoryAdapter != null) {
            mCategoryAdapter.clear();
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemsLoaded(List<Category> categories) {
        mCategoryAdapter.addAll(categories);
        mCategoryAdapter.notifyDataSetChanged();
        mCategories = categories;
    }

}
