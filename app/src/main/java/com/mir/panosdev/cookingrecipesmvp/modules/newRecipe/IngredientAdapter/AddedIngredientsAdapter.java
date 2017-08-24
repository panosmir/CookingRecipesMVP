package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddedIngredientsAdapter extends RecyclerView.Adapter<AddedIngredientsAdapter.Holder>{

    private LayoutInflater mLayoutInflater;
    private List<Ingredient> mAddedIngredients = new ArrayList<>();

    public AddedIngredientsAdapter(LayoutInflater inflater){
        mLayoutInflater = inflater;
    }

    @Override
    public AddedIngredientsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.added_ingredients_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(AddedIngredientsAdapter.Holder holder, int position) {
        holder.bind(mAddedIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddedIngredients.size();
    }

    public void addedIngredients(List<Ingredient> ingredients){
        mAddedIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.addedIngredientTextView)
        TextView mAddedIngredient;

        @BindView(R.id.quantityTextView)
        TextView mQuantity;

        private Ingredient mIngredient;
        private Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredient ingredient) {
            mIngredient = ingredient;
            mAddedIngredient.setText(ingredient.getIngredient());
            mQuantity.setText(ingredient.getQuantity());
        }
    }
}
