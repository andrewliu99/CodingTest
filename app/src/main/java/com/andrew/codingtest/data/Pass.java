package com.andrew.codingtest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrew.liu on 2022/1/19.
 */
public class Pass {
    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("range")
    @Expose
    private int range;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
