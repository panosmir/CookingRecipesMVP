package com.mir.panosdev.cookingrecipesmvp.modules.userprofile.userRecipesAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnRecipeClickListener;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Recipe> mRecipeList = new ArrayList<>();

    public UserProfileAdapter(LayoutInflater inflater){
        mLayoutInflater = inflater;
    }

    @Override
    public UserProfileAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(UserProfileAdapter.Holder holder, int position) {
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
        TextView recipeTitle;

        @BindView(R.id.recipeDescription)
        TextView recipeDescription;

        private Recipe mRecipe;
        private Context mContext;

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
            if(mOnRecipeClickListener!=null){
                mOnRecipeClickListener.onClick(v, mRecipe, getAdapterPosition());
            }
        }
    }

    public void setOnRecipeClickListener(OnRecipeClickListener listener){
        mOnRecipeClickListener = listener;
    }

    private OnRecipeClickListener mOnRecipeClickListener;
}
