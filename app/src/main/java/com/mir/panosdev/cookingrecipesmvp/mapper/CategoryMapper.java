package com.mir.panosdev.cookingrecipesmvp.mapper;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CategoryMapper {

    @Inject
    public CategoryMapper(){}

    public List<Category> mapCategories(Category[] response){
        List<Category> categories = new ArrayList<>();
        Category c = new Category();
        c.setmCategory("Please select a category");
        categories.add(c);
        if(response != null){
            for (Category categoriesResponse:
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
