package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.mir.panosdev.cookingrecipesmvp.listeners.OnSwipeUpListener;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter.RecipeAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RecipesPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP;
import com.mir.panosdev.cookingrecipesmvp.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainActivityMVP.MainView {

    @Inject
    protected RecipesPresenter mRecipesPresenter;

    private RecipeAdapter mRecipeAdapter;

    @BindView(R.id.recipe_list)
    RecyclerView recipesRecyclerView;

    @BindView(R.id.mainSwipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private OnSwipeUpListener mOnSwipeUpListener = this::loadRecipes;

    @Override
    public void onResume() {
        super.onResume();
        loadRecipes();
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecipesPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecipesPresenter.detachView();
    }

    private void initializeList() {
        recipesRecyclerView.setHasFixedSize(true);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mRecipeAdapter = new RecipeAdapter(getActivity().getLayoutInflater());
        mRecipeAdapter.setRecipeClickListener(mRecipeClickListener);
        recipesRecyclerView.setAdapter(mRecipeAdapter);
    }

    private OnRecipeClickListener mRecipeClickListener = (v, recipe, position) -> {
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

    public void loadRecipes() {
        if (NetworkUtils.isNetworkAvailable(this.getActivity())){
            mRecipesPresenter.getRecipes();
        }else{
            mRecipesPresenter.getRecipesFromDatabase();

        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeList();
        mSwipeRefreshLayout.setOnRefreshListener(mOnSwipeUpListener);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeLoaded(List<Recipe> recipes) {
        mRecipeAdapter.addRecipes(recipes);
    }

    @Override
    public void onClearItems() {
        mRecipeAdapter.clearRecipes();
    }

    @Override
    public void onNetworkUnavailableToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
