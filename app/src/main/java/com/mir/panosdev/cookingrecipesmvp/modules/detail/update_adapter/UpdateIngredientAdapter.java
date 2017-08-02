package com.mir.panosdev.cookingrecipesmvp.modules.detail.update_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.listeners.OnIngredientClickListener;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Panos on 14-Jun-17.
 */

public class UpdateIngredientAdapter extends RecyclerView.Adapter<UpdateIngredientAdapter.Holder>{

    private LayoutInflater mLayoutInflater;
    private List<Ingredient> mIngredients = new ArrayList<>();

    public UpdateIngredientAdapter(LayoutInflater inflater){
        mLayoutInflater = inflater;
    }

    @Override
    public UpdateIngredientAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.added_ingredients_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(UpdateIngredientAdapter.Holder holder, int position) {
        holder.bind(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void addIngredients(List<Ingredient> ingredients){
        mIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    public void clearIngredients(){
        mIngredients.clear();
        notifyDataSetChanged();
    }

    public void removeIngredient(Ingredient ingredient){
        mIngredients.remove(ingredient);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.addedIngredientTextView)
        TextView mIngredientTextView;

        @BindView(R.id.quantityTextView)
        TextView mIngredientQuantity;

        private Context mContext;
        private Ingredient mIngredient;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredient ingredient) {
            mIngredient = ingredient;
            mIngredientTextView.setText(ingredient.getIngredient());
            mIngredientQuantity.setText(ingredient.getQuantity());
        }

        @Override
        public void onClick(View v) {
            if(mIngredientClickListener!=null){
                mIngredientClickListener.onClick(mIngredientTextView, mIngredient, getAdapterPosition());
            }
        }
    }

    private OnIngredientClickListener mIngredientClickListener;

    public void setIngredientClickListener(OnIngredientClickListener listener){
        mIngredientClickListener = listener;
    }
}
