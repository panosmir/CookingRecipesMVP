package com.mir.panosdev.cookingrecipesmvp.modules.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseFragment;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter.AddedIngredientsAdapter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.DetailsPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class DetailsFragment extends BaseFragment implements DetailsActivityMVP.DetailsViewFragment {

    @BindView(R.id.recipeDescriptionDetail)
    TextView mRecipeDescription;

    @BindView(R.id.ingredientsRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.favoriteFloatingActionButton)
    FloatingActionButton favoriteButton;

    @Inject
    protected SharedPreferences mPrefs;

    private Recipe mRecipe;
    private String username;
    private boolean isFavorited = false;

    @Inject
    protected DetailsPresenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            mRecipe = (Recipe) getArguments().getSerializable("OLD_RECIPE");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPrefs = getActivity().getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
        mRecipe = (Recipe) getActivity().getIntent().getSerializableExtra(DetailsActivity.RECIPE);
        mRecipeDescription.setText(mRecipe.getDescription());
        initializeList();
        username = mPrefs.getString("USER_USERNAME", null);
        for (User u :
                mRecipe.getFavorites()) {
            if (u.getUsername().equals(username)) {
                favoriteButton.setImageResource(R.drawable.ic_favorite);
                isFavorited = true;
            }
        }


        setFavoriteIcon();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecipeDescription.setTransitionName("recipeAnimation");
        }

    }

    public void setFavoriteIcon() {
        Observable<Object> clicks = RxView.clicks(favoriteButton);
        clicks.subscribe(o -> {
            if (!isFavorited) {
                favoriteButton.setImageResource(R.drawable.ic_favorite);
                presenter.addFavorite();
                isFavorited = true;
            } else {
                favoriteButton.setImageResource(R.drawable.ic_remove_favorite);
                presenter.removeFavorite();
                isFavorited = false;
            }
        });

    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    private void initializeList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AddedIngredientsAdapter mIngredientsAdapter = new AddedIngredientsAdapter(getActivity().getLayoutInflater());
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mIngredientsAdapter.addedIngredients(mRecipe.getIngredients());
    }

    @Override
    public int getRecipeId() {
        return mRecipe.getId();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void onCompletedToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
