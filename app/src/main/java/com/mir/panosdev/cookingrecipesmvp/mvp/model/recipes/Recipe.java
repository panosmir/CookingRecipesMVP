package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.io.Serializable;

/**
 * Created by Panos on 3/18/2017.
 */

public class Recipe implements Serializable{
    private int userId;
    private int id;
    private String title;
    private String description;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
