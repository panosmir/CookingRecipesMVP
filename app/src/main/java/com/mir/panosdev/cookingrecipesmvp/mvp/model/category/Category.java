package com.mir.panosdev.cookingrecipesmvp.mvp.model.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Category implements Serializable{

    @SerializedName("id")
    private int mId;
    @SerializedName("category")
    private String mCategory;

    public int getId() {
        return mId;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public Category() {
    }
}
