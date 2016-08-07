package com.sebastien.testontouch.testontouch.bean;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.sebastien.testontouch.R;

import java.io.Serializable;

/**
 * Created by cecem on 30/07/2016.
 */
public class Flux implements Serializable{

    private String url;
    private String alias;
    private Category category;

    public Flux (String url, String alias, Category category){
        this.url = url;
        this.alias = alias;
        this.category = category;
    }

    public String displayFlux (){
        return alias == null || alias.isEmpty() ? url : alias;
    }

    @Override
    public boolean equals(Object o) {
        return ((Flux)o).getUrl().equals(getUrl()) ? true : false;
    }

    @Override
    public int hashCode() {
        return getUrl().hashCode()+getAlias().hashCode();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
