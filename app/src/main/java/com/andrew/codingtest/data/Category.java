package com.andrew.codingtest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrew.liu on 2022/1/19.
 */
public class Category {
    @SerializedName("category")
    @Expose
    private List<PassType> list;

    public List<PassType> getCategory() {
        return list;
    }

    public void setCategory(List<PassType> list) {
        this.list = list;
    }
}

