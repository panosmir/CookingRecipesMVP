package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.IngredientAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnIngredientClickListener;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Ingredient> mIngredients = new ArrayList<>();

    public IngredientAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.ingredient_list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.Holder holder, int position) {
        holder.bind(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        mIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    public void clearIngredients() {
        mIngredients.clear();
        notifyDataSetChanged();
    }

    public void add(Ingredient ingredient) {
        mIngredients.add(ingredient);
        notifyDataSetChanged();
    }

    public void remove(Ingredient ingredient) {
        mIngredients.remove(ingredient);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.grEditText)
        EditText mGrEdiText;

        @BindView(R.id.ingredientTextView)
        protected TextView ingredientTitle;

        private Ingredient mIngredient;
        private Context mContext;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredient ingredient) {
            ingredientTitle.setText(ingredient.getIngredient());
            ingredient.setQuantity(mGrEdiText.getText().toString());
            mIngredient = ingredient;
        }

        @Override
        public void onClick(View v) {
            if (mIngredientClickListener != null) {
                mIngredientClickListener.onClick(ingredientTitle, mIngredient, getAdapterPosition());

                mIngredient.setQuantity(mGrEdiText.getText().toString());

                ingredientTitle.setPaintFlags(ingredientTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }
        }

    }

    private OnIngredientClickListener mIngredientClickListener = null;

    public void setIngredientClickListener(OnIngredientClickListener listener) {
        mIngredientClickListener = listener;
    }
}
