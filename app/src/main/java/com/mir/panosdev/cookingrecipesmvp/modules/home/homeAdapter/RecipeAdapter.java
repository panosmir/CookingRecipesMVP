package com.mir.panosdev.cookingrecipesmvp.modules.home.homeAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panos on 3/18/2017.
 */

public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.Holder>{

    private LayoutInflater mLayoutInflater;
    private List<Recipe> mRecipeList = new ArrayList<>();

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.)
        return view;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
