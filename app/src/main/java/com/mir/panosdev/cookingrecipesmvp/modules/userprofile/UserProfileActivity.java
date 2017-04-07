package com.mir.panosdev.cookingrecipesmvp.modules.userprofile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerUserProfileComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.UserProfileModule;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.modules.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.userRecipesAdapter.UserProfileAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.UserProfilePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.UserProfileView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Panos on 4/5/2017.
 */

public class UserProfileActivity extends BaseActivity implements UserProfileView{

    @BindView(R.id.usernameTextView)
    TextView mUsername;

    @BindView(R.id.userRecipesRecyclerView)
    RecyclerView mRecyclerView;

    @Inject protected UserProfilePresenter mUserProfilePresenter;

    private UserProfileAdapter mProfileAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeUsernameTextbox();
        initializeList();
        loadUserRecipes();
    }

    private void loadUserRecipes() {
        mUserProfilePresenter.getUserRecipes();
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mProfileAdapter = new UserProfileAdapter(getLayoutInflater());
        mProfileAdapter.setOnRecipeClickListener(mClickListener);
        mRecyclerView.setAdapter(mProfileAdapter);
    }

    private void initializeUsernameTextbox() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        mUsername.setText(sharedPreferences.getString("USER_USERNAME", null));
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerUserProfileComponent.builder()
                .applicationComponent(getApplicationComponent())
                .userProfileModule(new UserProfileModule(this))
                .build().inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_profile;
    }

    @Override
    public int getUserId() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        int id = sharedPreferences.getInt("USER_ID", 0);
        return id;
    }

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        if(mProfileAdapter!=null){
            mProfileAdapter.addRecipes(recipes);
        }
    }

    @Override
    public void onClearItems() {
        if(mProfileAdapter != null){
            mProfileAdapter.clearRecipes();
        }
    }

    private OnRecipeClickListener mClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(UserProfileActivity.this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };
}