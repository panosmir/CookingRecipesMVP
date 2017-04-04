package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerNewRecipeComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.NewRecipeModule;
import com.mir.panosdev.cookingrecipesmvp.modules.Login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.NewRecipePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeView;

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

    @Inject
    protected NewRecipePresenter mNewRecipePresenter;

    User user = new User();

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
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

}
