package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.CategoriesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Panos on 19-May-17.
 */

public class CategoryMapper {

    @Inject
    public CategoryMapper(){}

    public List<Category> mapCategories(CategoriesResponse[] response){
        List<Category> categories = new ArrayList<>();
        Category c = new Category();
        c.setmCategory("Please select a category");
        categories.add(c);
        if(response != null){
            for (CategoriesResponse categoriesResponse:
                 response) {
                Category mCategory = new Category();
                mCategory.setmId(categoriesResponse.getId());
                mCategory.setmCategory(categoriesResponse.getCategory());
                categories.add(mCategory);
            }
        }
        return categories;
    }
}
