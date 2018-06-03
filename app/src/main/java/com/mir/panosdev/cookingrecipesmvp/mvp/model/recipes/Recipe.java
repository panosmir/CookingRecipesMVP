package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Recipe implements Serializable{
    private int id;
    private String title;
    private String description;

    private User user;
    private List<User> favorites = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<User> favorites) {
        this.favorites = favorites;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
