package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

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

    @Inject
    SharedPreferences mPrefs;

    @Inject
    protected DetailsPresenter mDetailsPresenter;

    private Recipe mRecipe;
    private boolean isReadyForDelete = false, isReadyForUpdate = false;

    @Override
    public void onStart() {
        super.onStart();
        mDetailsPresenter.attachView(this);
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
    }

    @OnClick(R.id.saveRecipeButton)
    public void saveRecipeButtonClick(){
        isReadyForUpdate = true;
        mRecipe.setTitle(mRecipeTitleEditText.getText().toString());
        mRecipe.setDescription(mRecipeDescriptionEditText.getText().toString());
        mDetailsPresenter.updateRecipe();
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick(){
        isReadyForDelete = true;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DetailsFragment detailsFragment = new DetailsFragment();
        manager.beginTransaction().replace(R.id.details_fragment_container, detailsFragment).commit();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    @Override
    public Recipe getRecipeDetails() {
        return mRecipe;
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
}
