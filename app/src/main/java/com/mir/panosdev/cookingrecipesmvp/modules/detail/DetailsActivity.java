package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

/**
 * Created by Panos on 3/18/2017.
 */

public class DetailsActivity extends BaseActivity implements DetailsView {

    public static final String RECIPE = "recipe";

    @BindView(R.id.recipeTitleDetail)
    TextView mRecipeTitle;

    @BindView(R.id.recipeDescriptionDetail)
    TextView mRecipeDescription;

    @Inject
    SharedPreferences prefs;

    @Inject
    protected DetailsPresenter mPresenter;

    private Recipe recipe;
    boolean onDeleteButtonClick = false;
    int userId;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showBackArrow();
        onDeleteButtonClick = false;
        prefs = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);

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
        userId = prefs.getInt("USER_ID", 0);
        if (recipe.getUser().getId() == userId){
            deleteButton.setVisible(true);
        }
        else{
            deleteButton.setVisible(false);
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
                onDeleteButtonClick = true;
                mPresenter.deleteRecipe();
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
    public boolean getDeleteSignal() {
        return onDeleteButtonClick;
    }

    @Override
    public void onDeleteShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}