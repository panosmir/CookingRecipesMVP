package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerNewRecipeComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.NewRecipeModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnIngredientClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.CategoryAdapter.CategoryAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.AddedIngredientsAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.IngredientAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.NewRecipePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class NewRecipeActivity extends BaseActivity implements NewRecipeView {

    @BindView(R.id.addRecipeTitleEditText)
    EditText addRecipeTitle;

    @BindView(R.id.addRecipeDescriptionEditText)
    EditText addRecipeDescription;

    @BindView(R.id.categorySpinner)
    Spinner spinner;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    protected NewRecipePresenter mNewRecipePresenter;

    User user = new User();
    CategoryAdapter mCategoryAdapter;
    List<Category> mCategories = new ArrayList<>();
    List<Ingredient> mIngredients = new ArrayList<>();
    List<Ingredient> addedIngredients = new ArrayList<>();
    Category category = new Category();
    private int categoryId;
    private IngredientAdapter mIngredientAdapter;
    private AddedIngredientsAdapter mAddedIngredientsAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        mNewRecipePresenter.fetchCategories();
        setAdapters();
        initializeList();
        spinnerItemSelectedListener();
    }

    private void setAdapters() {
        mCategoryAdapter = new CategoryAdapter(mCategories, this);
        spinner.setAdapter(mCategoryAdapter);
    }

    private void spinnerItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = mCategories.get(position);
                categoryId = position;
                mNewRecipePresenter.fetchIngredients();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mIngredientAdapter = new IngredientAdapter(getLayoutInflater());
        mIngredientAdapter.setIngredientClickListener(mOnIngredientClickListener);

        mAddedIngredientsAdapter = new AddedIngredientsAdapter(getLayoutInflater());

        mRecyclerView.setAdapter(mAddedIngredientsAdapter);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerNewRecipeComponent.builder().applicationComponent(
                getApplicationComponent()
        ).newRecipeModule(new NewRecipeModule(this))
                .build().inject(this);
    }

    @OnClick(R.id.searchIngredientsButton)
    public void searchIngredientButtonClick(){
        new MaterialDialog.Builder(this)
                .title(category.getCategory())
                .items(mIngredients)
                .adapter(mIngredientAdapter, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, Integer[] integers, CharSequence[] charSequences) {
                        return true;
                    }
                })
                .widgetColor(Color.BLUE)
                .positiveText("Choose")
                .positiveColor(Color.BLUE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mAddedIngredientsAdapter.clearAddedIngredients();
                        mAddedIngredientsAdapter.addedIngredients(addedIngredients);
                    }
                })
                .show();
    }

    @OnClick(R.id.addRecipeButton)
    public void addRecipeButtonClick(View view) {
        if (view.getId() == R.id.addRecipeButton) {
            mNewRecipePresenter.addNewRecipe();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_recipe;
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
        if (mCategoryAdapter != null) {
            mCategoryAdapter.clear();
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemsLoaded(List<Category> categories) {
        mCategoryAdapter.addAll(categories);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public void onIngredientsLoaded(List<Ingredient> ingredientList) {
        mIngredientAdapter.addIngredients(ingredientList);
        mIngredientAdapter.notifyDataSetChanged();
        mIngredients = ingredientList;
    }

    @Override
    public void onClearIngredients() {
        mIngredientAdapter.clearIngredients();
    }

    private OnIngredientClickListener mOnIngredientClickListener = new OnIngredientClickListener() {
        @Override
        public void onClick(View v, Ingredient ingredient, int position) {
            if (addedIngredients.contains(ingredient))
                addedIngredients.remove(ingredient);
            else {
                addedIngredients.add(ingredient);
            }
        }
    };

    @Override
    public Recipe getRecipeDetails() {
        SharedPreferences mSharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        Recipe recipe = new Recipe();
        user.setId(mSharedPreferences.getInt("USER_ID", 0));
        user.setUsername(mSharedPreferences.getString("USER_USERNAME", ""));
        user.setPassword(mSharedPreferences.getString("USER_PASSWORD", ""));
        recipe.setUser(user);
        if (!addRecipeTitle.getText().toString().isEmpty() || !addRecipeDescription.getText().toString().isEmpty()) {
            recipe.setTitle(addRecipeTitle.getText().toString());
            recipe.setDescription(addRecipeDescription.getText().toString());
            if (!addedIngredients.isEmpty()) {
                recipe.getIngredients().addAll(addedIngredients);
            }
            return recipe;
        } else
            return null;
    }

}
