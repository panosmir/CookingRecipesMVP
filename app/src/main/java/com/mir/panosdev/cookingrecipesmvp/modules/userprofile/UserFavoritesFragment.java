package com.mir.panosdev.cookingrecipesmvp.modules.userprofile;

import android.app.ActivityOptions;
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


public class UserFavoritesFragment extends BaseFragment implements UserProfileMVP.UserProfileView {
    @Inject
    protected UserProfilePresenter mPresenter;

    @Inject
    protected SharedPreferences sharedPreferences;

    private UserProfileAdapter mProfileAdapter;

    @BindView(R.id.userProfileFavoritesRecipesView)
    RecyclerView mRecyclerView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserFavoritesRecipes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_user_favorites_recipes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeList();
        mPresenter.getUserFavoritesRecipes();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mProfileAdapter = new UserProfileAdapter(getActivity().getLayoutInflater());
        mProfileAdapter.setOnRecipeClickListener(mClickListener);
        mRecyclerView.setAdapter(mProfileAdapter);
    }

    private OnRecipeClickListener mClickListener = (v, recipe, position) -> {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.RECIPE, recipe);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "recipeAnimation");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    };

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
        if (mProfileAdapter != null) {
            mProfileAdapter.addRecipes(recipes);
        }
    }

    @Override
    public void onClearItems() {
        if (mProfileAdapter != null) {
            mProfileAdapter.clearRecipes();
        }
    }

    @Override
    public void onCompleteShowToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
