package com.sebastien.testontouch.testontouch.bean;

import java.io.Serializable;

/**
 * Created by cecem on 30/07/2016.
 */
public class Flux implements Serializable{

    private String url;
    private String alias;

    public Flux (String url, String alias){
        this.url = url;
        this.alias = alias;
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
}
