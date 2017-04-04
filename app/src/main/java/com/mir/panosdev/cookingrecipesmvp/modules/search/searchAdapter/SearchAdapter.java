package com.mir.panosdev.cookingrecipesmvp.modules.search.searchAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Panos on 3/23/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder>{

    private LayoutInflater mLayoutInflater;
    private List<Recipe> mRecipeList = new ArrayList<>();

    public SearchAdapter(LayoutInflater inflater){
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.Holder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void addRecipes(List<Recipe> recipes){
        mRecipeList.addAll(recipes);
        notifyDataSetChanged();
    }

    public void clearRecipes(){
        mRecipeList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipeTitle)
        protected TextView recipeTitle;

        @BindView(R.id.recipeDescription)
        protected TextView recipeDescription;

        private Context mContext;
        private Recipe mRecipe;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            mRecipe = recipe;
            recipeTitle.setText(recipe.getTitle());
            recipeDescription.setText(recipe.getDescription());
        }

        @Override
        public void onClick(View v) {
            if (mRecipeClickListener != null){
                mRecipeClickListener.onClick(recipeTitle, mRecipe, getAdapterPosition());
            }
        }
    }


    public void setRecipeClickListener(SearchAdapter.OnRecipeClickListener listener){
        mRecipeClickListener = listener;
    }

    private SearchAdapter.OnRecipeClickListener mRecipeClickListener;

    public interface OnRecipeClickListener{
        void onClick(View v, Recipe recipe, int position);
    }
}
