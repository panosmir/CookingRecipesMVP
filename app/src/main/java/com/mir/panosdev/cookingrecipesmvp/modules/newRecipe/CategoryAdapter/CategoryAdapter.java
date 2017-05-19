package com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.CategoryAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Panos on 19-May-17.
 */

public class CategoryAdapter extends BaseAdapter{

    private List<Category> mCategoryList;
    @Inject Context mContext;
    private LayoutInflater mLayoutInflater;

    public CategoryAdapter(List<Category> mCategoryList, Context mContext) {
        this.mCategoryList = mCategoryList;
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null){
            view = mLayoutInflater.inflate(R.layout.spinner_list_layout, null);
            TextView tv = (TextView)view.findViewById(R.id.spinnerTextView);
            tv.setText(mCategoryList.get(position).getCategory());
        }
        return view;
    }

    public void clear() {
        mCategoryList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Category> categories) {
        mCategoryList.addAll(categories);
        notifyDataSetChanged();
    }
}
