package com.sebastien.testontouch.testontouch.bean;

import android.support.v4.content.ContextCompat;

import java.util.Date;

/**
 * Created by Sébastien on 03-04-16.
 */
public class Article implements Comparable {

    private String title;
    private String articleLink;
    private Date pubDate;
    private Boolean isFavorite;
    private Flux flux;

    public Article(String title, String articleLink, Date pubDate, Flux flux) {
        this.title = title;
        this.articleLink = articleLink;
        this.pubDate = pubDate;
        isFavorite = false;
        this.flux = flux;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return ((Article) obj).getTitle().equals(title);
    }

    @Override
    public int hashCode() {
        return articleLink.hashCode();
    }

    public int compareTo(Object a) {

        return ((Article) a).getPubDate().compareTo(getPubDate());
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Flux getFlux() {
        return flux;
    }

    public void setFlux(Flux flux) {
        this.flux = flux;
    }
}
