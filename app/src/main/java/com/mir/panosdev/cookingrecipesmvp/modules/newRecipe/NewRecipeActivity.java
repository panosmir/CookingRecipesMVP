package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnIngredientClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.update_adapter.UpdatableIngredientAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.CategoryAdapter.CategoryAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.MainIngredientAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.NewRecipePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeMVP;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class NewRecipeActivity extends BaseActivity implements NewRecipeMVP.NewRecipeView {

    @BindView(R.id.addRecipeTitleEditText)
    EditText addRecipeTitle;

    @BindView(R.id.addRecipeDescriptionEditText)
    EditText addRecipeDescription;

    @BindView(R.id.categorySpinner)
    Spinner spinner;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.newRecipeFloatingButton)
    FloatingActionButton newRecipeFloatingButton;

    @Inject
    protected NewRecipePresenter mNewRecipePresenter;

    private User user = new User();
    private Recipe recipe = new Recipe();
    private CategoryAdapter mCategoryAdapter;
    private List<Category> mCategories = new ArrayList<>();
    private List<Ingredient> mIngredients;
    private List<Ingredient> addedIngredients;
    private Category category = new Category();
    private int categoryId;
    private MainIngredientAdapter mIngredientAdapter;
    private UpdatableIngredientAdapter mUpdatableIngredientAdapter;
    private List<Ingredient> tempList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewRecipePresenter.attachView(this);
        mNewRecipePresenter.fetchCategories();
        setAdapters();
        initializeList();
        spinnerItemSelectedListener();
        mIngredients = new ArrayList<>();
        addedIngredients = new ArrayList<>();
        tempList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewRecipePresenter.detachView();
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

        mIngredientAdapter = new MainIngredientAdapter(getLayoutInflater());
        mIngredientAdapter.setIngredientClickListener(mMainIngredientClickListener);

        mUpdatableIngredientAdapter = new UpdatableIngredientAdapter(getLayoutInflater());
        mUpdatableIngredientAdapter.setIngredientClickListener(mAddedIngredientClickListener);
        mRecyclerView.setAdapter(mUpdatableIngredientAdapter);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    private List<Ingredient> moddedIngredients(){
        tempList.clear();
        tempList.addAll(mIngredients);
        for (Ingredient i :
                mIngredients) {
            for (Ingredient ingredient :
                    addedIngredients) {
                if(ingredient.getIngredient().equals(i.getIngredient())){
                    tempList.remove(i);
                }
            }
        }
        mIngredientAdapter.clearIngredients();
        mIngredientAdapter.addIngredients(tempList);
        return tempList;
    }

    @OnClick(R.id.searchIngredientsButton)
    public void searchIngredientButtonClick() {
        new MaterialDialog.Builder(this)
                .title(category.getCategory())
                .items(moddedIngredients())
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
                        mUpdatableIngredientAdapter.clearIngredients();
                        mUpdatableIngredientAdapter.addIngredients(addedIngredients);
                    }
                }).show();
    }

    private OnIngredientClickListener mAddedIngredientClickListener = new OnIngredientClickListener() {
        @Override
        public void onClick(View v, Ingredient ingredient, int position, boolean isClicked) {
            mUpdatableIngredientAdapter.removeIngredient(ingredient);
            addedIngredients.remove(ingredient);
            Toast.makeText(NewRecipeActivity.this, ingredient.getIngredient() + " removed!", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.newRecipeFloatingButton)
    public void addRecipeButtonClick(View view) {
        if (view.getId() == R.id.newRecipeFloatingButton) {
            if (!addRecipeTitle.getText().toString().isEmpty() && !addRecipeDescription.getText().toString().isEmpty() && !addedIngredients.isEmpty()) {
                newRecipeFloatingButton.setEnabled(true);
                mNewRecipePresenter.addNewRecipe();
            }
            else
                newRecipeFloatingButton.setEnabled(false);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_recipe;
    }

    @Override
    public void onCompletedToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewRecipeActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.RECIPE, recipe);
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
        mIngredients = ingredientList;
    }

    @Override
    public void onClearIngredients() {
        mIngredientAdapter.clearIngredients();
    }

    private OnIngredientClickListener mMainIngredientClickListener = new OnIngredientClickListener() {
        @Override
        public void onClick(View v, Ingredient ingredient, int position, boolean isClicked) {
            if (!addedIngredients.contains(ingredient) && !isClicked) {
                addedIngredients.add(ingredient);
                Toast.makeText(NewRecipeActivity.this,ingredient.getIngredient() + " added!", Toast.LENGTH_SHORT).show();
            }
            else if(isClicked){
                addedIngredients.remove(ingredient);
                Toast.makeText(NewRecipeActivity.this, ingredient.getIngredient() + " removed!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public Recipe getRecipeDetails() {
        SharedPreferences mSharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        user.setId(mSharedPreferences.getInt("USER_ID", 0));
        user.setUsername(mSharedPreferences.getString("USER_USERNAME", ""));
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
