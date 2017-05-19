package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerDetailComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.DetailsModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Panos on 3/18/2017.
 */
public class DetailsActivity extends BaseActivity implements DetailsView {

    public static final String RECIPE = "recipe";

    @BindView(R.id.recipeTitleDetail)
    TextView mRecipeTitle;

    @BindView(R.id.recipeDescriptionDetail)
    TextView mRecipeDescription;

    @BindView(R.id.recipeTitleDetailEditText)
    EditText mRecipeTitleEdit;

    @BindView(R.id.recipeDescriptionDetailEditText)
    EditText mRecipeDescriptionEdit;

    @BindView(R.id.saveRecipeButton)
    Button mSaveRecipeButton;

    @BindView(R.id.cancelButton)
    Button mCancelButton;

    @Inject
    SharedPreferences prefs;

    @Inject
    protected DetailsPresenter mPresenter;

    private Recipe recipe;
    boolean isReadyForDelete = false, isReadyForUpdate = false;
    int userId;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showBackArrow();
        prefs = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        mRecipeTitleEdit.setVisibility(View.INVISIBLE);
        mRecipeDescriptionEdit.setVisibility(View.INVISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecipeTitle.setTransitionName("recipeAnimation");
        }

        recipe = (Recipe) intent.getSerializableExtra(RECIPE);
        mRecipeTitle.setText(recipe.getTitle());
        mRecipeDescription.setText(recipe.getDescription());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem deleteButton = menu.findItem(R.id.deleteRecipeButton);
        MenuItem updateButton = menu.findItem(R.id.updateRecipeButton);
        userId = prefs.getInt("USER_ID", 0);
        if (recipe.getUser().getId() == userId){
            deleteButton.setVisible(true);
            updateButton.setVisible(true);
        }
        else{
            deleteButton.setVisible(false);
            updateButton.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.deleteRecipeButton:
                isReadyForDelete = true;
                mPresenter.deleteRecipe();
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.updateRecipeButton:
                isReadyForUpdate = true;
                editUserDetails();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.saveRecipeButton)
    public void saveRecipeButtonClick(){
        recipe.setTitle(mRecipeTitleEdit.getText().toString());
        recipe.setDescription(mRecipeDescriptionEdit.getText().toString());
        mPresenter.updateRecipe();
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick(){
        mRecipeTitle.setVisibility(View.VISIBLE);
        mRecipeTitleEdit.setVisibility(View.INVISIBLE);
        mRecipeDescription.setVisibility(View.VISIBLE);
        mRecipeDescriptionEdit.setVisibility(View.INVISIBLE);
        mSaveRecipeButton.setVisibility(View.INVISIBLE);
        mCancelButton.setVisibility(View.INVISIBLE);
    }

    private void editUserDetails() {
        mRecipeTitle.setVisibility(View.INVISIBLE);
        mRecipeTitleEdit.setVisibility(View.VISIBLE);
        mRecipeDescription.setVisibility(View.INVISIBLE);
        mRecipeDescriptionEdit.setVisibility(View.VISIBLE);
        mSaveRecipeButton.setVisibility(View.VISIBLE);
        mCancelButton.setVisibility(View.VISIBLE);

        mRecipeTitleEdit.setText(recipe.getTitle());
        mRecipeDescriptionEdit.setText(recipe.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        return true;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .detailsModule(new DetailsModule(this))
                .build().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public Recipe getRecipeDetails() {
        return recipe;
    }

    @Override
    public void onDeleteShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}