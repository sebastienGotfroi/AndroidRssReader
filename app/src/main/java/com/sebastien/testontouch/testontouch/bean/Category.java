package com.sebastien.testontouch.testontouch.bean;

import android.graphics.Color;

/**
 * Created by cecem on 04/08/2016.
 */
public class Category {
    private String name;
    private Integer startColor;
    private Integer endColor;

    public Category(String name){
        this.name = name;
    }

    public Category(String name, Integer startColor, Integer endColor){
        this(name);
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartColor() {
        return startColor;
    }

    public void setStartColor(Integer startColor) {
        this.startColor = startColor;
    }

    public Integer getEndColor() {
        return endColor;
    }

    public void setEndColorId(Integer endColor) {
        this.endColor = endColor;
    }
}
