package com.mir.panosdev.cookingrecipesmvp.mvp.model.category;

public class Categories implements java.io.Serializable {
    private static final long serialVersionUID = 6849705858325444072L;
    private CategoriesResponse[] categories;

    public CategoriesResponse[] getCategories() {
        return this.categories;
    }

    public void setCategories(CategoriesResponse[] categories) {
        this.categories = categories;
    }
}
