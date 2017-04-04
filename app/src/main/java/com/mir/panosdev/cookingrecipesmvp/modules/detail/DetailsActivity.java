package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import butterknife.BindView;

/**
 * Created by Panos on 3/18/2017.
 */

public class DetailsActivity extends BaseActivity {

    public static final String RECIPE ="recipe";

    @BindView(R.id.recipeTitleDetail)
    TextView mRecipeTitle;

    @BindView(R.id.recipeDescriptionDetail)
    TextView mRecipeDescription;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        showBackArrow();

        Recipe recipe = (Recipe) intent.getSerializableExtra(RECIPE);
        mRecipeTitle.setText(recipe.getTitle());
        mRecipeDescription.setText(recipe.getDescription());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }
}