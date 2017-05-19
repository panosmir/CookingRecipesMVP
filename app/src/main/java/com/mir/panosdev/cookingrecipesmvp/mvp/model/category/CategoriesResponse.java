package com.mir.panosdev.cookingrecipesmvp.mvp.model.category;

public class CategoriesResponse implements java.io.Serializable {
    private static final long serialVersionUID = 1129127297578075169L;
    private int id;
    private String category;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
