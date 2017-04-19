package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

public class RecipesResponseRecipes {
    private String description;
    private int id;
    private String title;
    private int userId;
    private User user;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
