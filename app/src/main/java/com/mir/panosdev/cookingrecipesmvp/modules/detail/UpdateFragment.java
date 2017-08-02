package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnIngredientClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.update_adapter.UpdateIngredientAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.CategoryAdapter.CategoryAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.IngredientAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Panos on 10-Jun-17.
 */

public class UpdateFragment extends BaseFragment implements DetailsActivityMVP.DetailsView {
    public static final String RECIPE = "recipe";

    @BindView(R.id.recipeTitleDetailEditText)
    EditText mRecipeTitleEditText;

    @BindView(R.id.recipeDescriptionDetailEditText)
    EditText mRecipeDescriptionEditText;

    @BindView(R.id.updateIngredientRecyclerView)
    RecyclerView mIngredientRecyclerView;

    @BindView(R.id.updateCategorySpinner)
    Spinner mCategorySpinner;

    @Inject
    SharedPreferences mPrefs;

    @Inject
    protected DetailsPresenter mDetailsPresenter;

    private Recipe mRecipe, updateRecipe;
    private boolean isReadyForDelete = false, isReadyForUpdate = false, isCancelled = false;
    private UpdateIngredientAdapter mUpdateIngredientAdapter;
    private IngredientAdapter mIngredientAdapter;
    private int categoryId;
    private List<Category> mCategories = new ArrayList<>();
    private List<Ingredient> mIngredients = new ArrayList<>();
    private List<Ingredient> updatedIngredients = new ArrayList<>();
    private Category mCategory = new Category();
    private CategoryAdapter mCategoryAdapter;
    private List<Ingredient> tempList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        mDetailsPresenter.attachView(this);
        mDetailsPresenter.fetchCategories();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_update_recipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPrefs = getActivity().getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
        mRecipe = (Recipe) getActivity().getIntent().getSerializableExtra(RECIPE);
        mRecipeTitleEditText.setText(mRecipe.getTitle());
        mRecipeDescriptionEditText.setText(mRecipe.getDescription());
        updateRecipe = mRecipe;
        updatedIngredients = mRecipe.getIngredients();
        initializeList();
        fillRecipeIngredients();
        setAdapter();
        spinnerItemSelectedListener();
    }

    private void spinnerItemSelectedListener() {
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = mCategories.get(position);
                categoryId = position;
                mDetailsPresenter.fetchIngredients();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAdapter() {
        mCategoryAdapter = new CategoryAdapter(mCategories, this.getActivity());
        mCategorySpinner.setAdapter(mCategoryAdapter);
    }

    private void fillRecipeIngredients() {
        mUpdateIngredientAdapter.addIngredients(updatedIngredients);
    }

    @OnClick(R.id.saveRecipeButton)
    public void saveRecipeButtonClick() {
        isReadyForUpdate = true;
        mRecipe.setTitle(mRecipeTitleEditText.getText().toString());
        mRecipe.setDescription(mRecipeDescriptionEditText.getText().toString());
        mDetailsPresenter.updateRecipe();
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick() {
        isCancelled = true;
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("OLD_RECIPE", mRecipe);
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        DetailsFragment detailsFragment = new DetailsFragment();
//        detailsFragment.setArguments(bundle);
//        manager.beginTransaction().replace(R.id.details_fragment_container, detailsFragment).commit();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    private void initializeList() {
        mIngredientRecyclerView.setHasFixedSize(true);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mUpdateIngredientAdapter = new UpdateIngredientAdapter(getActivity().getLayoutInflater());
        mIngredientAdapter = new IngredientAdapter(getActivity().getLayoutInflater());
        mUpdateIngredientAdapter.setIngredientClickListener(mIngredientClickListener);
        mIngredientAdapter.setIngredientClickListener(mUpdateIngredientClickListener);
        mIngredientRecyclerView.setAdapter(mUpdateIngredientAdapter);
    }

    private List<Ingredient> moddedIngredients(){
        tempList.clear();
        tempList.addAll(mIngredients);
        for (Ingredient i :
                mIngredients) {
            for (Ingredient ingredient :
                    updateRecipe.getIngredients()) {
                if(ingredient.getIngredient().equals(i.getIngredient())){
                    tempList.remove(i);
                }
            }
        }
        mIngredientAdapter.clearIngredients();
        mIngredientAdapter.addIngredients(tempList);
        return tempList;
    }

    @OnClick(R.id.ingredientSearchButton)
    public void searchIngredientButtonClick() {
        new MaterialDialog.Builder(this.getActivity())
                .title(mCategory.getCategory())
                .items(moddedIngredients())
                .adapter(mIngredientAdapter, new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false))
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
                        mUpdateIngredientAdapter.clearIngredients();
                        mUpdateIngredientAdapter.addIngredients(updateRecipe.getIngredients());
                    }
                })
                .show();
    }

    private OnIngredientClickListener mIngredientClickListener = new OnIngredientClickListener() {
        @Override
        public void onClick(View v, Ingredient ingredient, int position) {
            mUpdateIngredientAdapter.removeIngredient(ingredient);
            updateRecipe.getIngredients().remove(ingredient);
        }
    };

    private OnIngredientClickListener mUpdateIngredientClickListener = new OnIngredientClickListener() {
        @Override
        public void onClick(View v, Ingredient ingredient, int position) {
            if (updateRecipe.getIngredients().contains(ingredient)) {
                updateRecipe.getIngredients().remove(ingredient);
                mUpdateIngredientAdapter.removeIngredient(ingredient);
            } else
                updateRecipe.getIngredients().add(ingredient);
        }
    };

    @Override
    public Recipe getRecipeDetails() {
        if (isCancelled)
            return mRecipe;
        else
            return updateRecipe;
    }

    @Override
    public void onDeleteShowToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean getDeleteSignal() {
        return isReadyForDelete;
    }

    @Override
    public boolean getUpdateSignal() {
        return isReadyForUpdate;
    }

    @Override
    public void onUpdateShowToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public void onClearIngredients() {
        mIngredientAdapter.clearIngredients();
    }

    @Override
    public void onIngredientsLoaded(List<Ingredient> ingredientList) {
        mIngredientAdapter.addIngredients(ingredientList);
        mIngredients = ingredientList;
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
    }
}
