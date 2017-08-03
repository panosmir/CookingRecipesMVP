package com.mir.panosdev.cookingrecipesmvp.mvp.model.users;

import java.io.Serializable;

import io.reactivex.annotations.Nullable;

/**
 * Created by Panos on 3/30/2017.
 */

public class User implements Serializable{

    private int user_id;
    private String username;
    @Nullable
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.user_id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
