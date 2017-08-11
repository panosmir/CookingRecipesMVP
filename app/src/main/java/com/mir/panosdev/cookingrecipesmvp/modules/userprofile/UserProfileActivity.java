package com.mir.panosdev.cookingrecipesmvp.modules.userprofile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.userRecipesAdapter.UserProfileAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.UserProfilePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.UserProfileMVP;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class UserProfileActivity extends BaseActivity implements UserProfileMVP.UserProfileView {

    @BindView(R.id.usernameTextView)
    TextView mUsername;

    @BindView(R.id.userRecipesRecyclerView)
    RecyclerView mRecyclerView;

    @Inject protected UserProfilePresenter mUserProfilePresenter;

    @Inject protected SharedPreferences sharedPreferences;

    private UserProfileAdapter mProfileAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserProfilePresenter.attachView(this);
        loadUserRecipes();
        showBackArrow();
        initializeUsernameTextBox();
        initializeList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserProfilePresenter.detachView();
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

    private void initializeUsernameTextBox() {
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        mUsername.setText(sharedPreferences.getString("USER_USERNAME", null));
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
        return R.layout.activity_user_profile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getUserId() {
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", 0);
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

    @Override
    public void onCompleteShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
