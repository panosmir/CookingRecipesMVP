package com.mir.panosdev.cookingrecipesmvp.modules.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.searchAdapter.SearchAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.SearchPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchActivityMVP;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchFragment extends BaseFragment implements SearchActivityMVP.SearchView {

    @Inject
    protected SearchPresenter mSearchPresenter;

    @BindView(R.id.searchRecipeTextView)
    EditText searchText;

    @BindView(R.id.searchRecipeList)
    RecyclerView searchList;

    private SearchAdapter mSearchAdapter;

    @Override
    public void onStart() {
        super.onStart();
        mSearchPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearchPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    private void initializeList() {
        searchList.setHasFixedSize(true);
        searchList.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mSearchAdapter = new SearchAdapter(getActivity().getLayoutInflater());
        mSearchAdapter.setRecipeClickListener(mRecipeClickListener);
        searchList.setAdapter(mSearchAdapter);
    }

    private OnRecipeClickListener mRecipeClickListener = new OnRecipeClickListener() {
        @Override
        public void onClick(View v, Recipe recipe, int position) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE, recipe);
            startActivity(intent);
        }
    };

    @OnClick(R.id.searchButton)
    public void searchRecipeById(View view) {
        if (view.getId() == R.id.searchButton) {
            mSearchPresenter.getARecipe();
            initializeList();
        }
    }

    @Override
    public void onShowDialog(String searchString) {
        if (mSearchAdapter != null) {
            showDialog(searchString);
        }
    }

    @Override
    public String searchTitle() {
        if (searchText.getText().toString().isEmpty()) {
            return null;
        } else {
            return searchText.getText().toString();
        }
    }

    @Override
    public void onShowToast(String message) {
        if (mSearchAdapter != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClearItems() {
        if (mSearchAdapter != null) {
            mSearchAdapter.clearRecipes();
        }
    }

    @Override
    public void onRecipeLoaded(List<Recipe> recipes) {
        if (mSearchAdapter != null) {
            mSearchAdapter.addRecipes(recipes);
        }
    }

    @Override
    public void onHideDialog() {
        hideDialog();
   }
}
