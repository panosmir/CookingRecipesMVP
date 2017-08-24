package com.mir.panosdev.cookingrecipesmvp.modules.userprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class UserProfileFragment extends BaseFragment implements UserProfileMVP.UserFragment{

    @BindView(R.id.usernameTextView)
    TextView mUsername;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    protected SharedPreferences sharedPreferences;

    @Inject
    protected Context mContext;

    @Inject
    protected UserProfilePresenter mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachFragment(this);
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeUsernameTextBox();
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(viewPager);
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

    private class CustomAdapter extends FragmentPagerAdapter {

        private String[] fragments = {"User Recipes", "Favorites"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context context) {
            super(supportFragmentManager);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new UserRecipesFragment();
                case 1:
                    return new UserFavoritesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
