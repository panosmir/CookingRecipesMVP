package com.mir.panosdev.cookingrecipesmvp.modules.userprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
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
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseFragment implements UserProfileMVP.UserProfileView {

    @BindView(R.id.usernameTextView)
    TextView mUsername;

    @BindView(R.id.userRecipesRecyclerView)
    RecyclerView mRecyclerView;

    @Inject protected UserProfilePresenter mUserProfilePresenter;

    @Inject protected SharedPreferences sharedPreferences;

    private UserProfileAdapter mProfileAdapter;

    @Override
    public void onStart() {
        super.onStart();
        mUserProfilePresenter.attachView(this);
        loadUserRecipes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUsernameTextBox();
        initializeList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserProfilePresenter.detachView();
    }

    private void loadUserRecipes() {
        mUserProfilePresenter.getUserRecipes();
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mProfileAdapter = new UserProfileAdapter(getActivity().getLayoutInflater());
        mProfileAdapter.setOnRecipeClickListener(mClickListener);
        mRecyclerView.setAdapter(mProfileAdapter);
    }

    private void initializeUsernameTextBox() {
        sharedPreferences = getActivity().getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
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
    public int getUserId() {
        sharedPreferences = getActivity().getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
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
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private OnRecipeClickListener mClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };
}
