package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

import com.google.gson.annotations.SerializedName;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipesResponseRecipes implements Serializable{
    private String description;
    private int id;
    private String title;

    private User user;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<User> favorites = new ArrayList<>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<User> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<User> favorites) {
        this.favorites = favorites;
    }
}
