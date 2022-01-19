package com.andrew.codingtest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrew.liu on 2022/1/19.
 */
public class PassType {
    @SerializedName("passType")
    @Expose
    private int passType;

    @SerializedName("item")
    @Expose
    private List<Pass> list;

    public int getPassType() {
        return passType;
    }

    public void setPassType(int passType) {
        this.passType = passType;
    }

    public List<Pass> getList() {
        return list;
    }

    public void setList(List<Pass> list) {
        this.list = list;
    }
}
