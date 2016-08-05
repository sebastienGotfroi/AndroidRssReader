package com.sebastien.testontouch.testontouch.bean;

import android.graphics.Color;

/**
 * Created by cecem on 04/08/2016.
 */
public class Category {
    private String name;
    private String startColor;
    private String endColor;

    public Category(){
        startColor = "#75009A24";
        endColor = "#009A24";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartColor() {
        return startColor;
    }

    public void setStartColor(String startColor) {
        this.startColor = startColor;
    }

    public String getEndColor() {
        return endColor;
    }

    public void setEndColorId(String endColor) {
        this.endColor = endColor;
    }
}
