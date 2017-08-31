package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import javax.inject.Inject;

public class DetailsActivity extends BaseActivity implements DetailsActivityMVP.DetailsViewActivity {

    public static final String RECIPE = "recipe";

    @Inject
    protected SharedPreferences prefs;

    @Inject
    protected DetailsPresenter mPresenter;

    private Recipe recipe;
    private boolean isReadyForDelete = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachActivity(this);
        FragmentManager manager = getSupportFragmentManager();
        DetailsFragment detailsFragment = new DetailsFragment();
        manager.beginTransaction().replace(R.id.details_fragment_container, detailsFragment).commit();
        showBackArrow();
        prefs = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        recipe = (Recipe) getIntent().getSerializableExtra(RECIPE);
        getSupportActionBar().setTitle(recipe.getTitle());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem deleteButton = menu.findItem(R.id.deleteRecipeButton);
        MenuItem updateButton = menu.findItem(R.id.updateRecipeButton);
        int userId = prefs.getInt("USER_ID", 0);
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
                editUserDetails();
        }
        return super.onOptionsItemSelected(item);
    }

    private void editUserDetails() {
        FragmentManager manager = getSupportFragmentManager();
        UpdateFragment updateFragment = new UpdateFragment();
        manager.beginTransaction().replace(R.id.details_fragment_container, updateFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        return true;
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

}