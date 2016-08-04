package com.sebastien.testontouch.testontouch.bean;

import java.util.Date;

/**
 * Created by Sébastien on 03-04-16.
 */
public class Article implements Comparable{

    private String title;
    private String articleLink;
    private Date pubDate;
    private Boolean isFavorite;

    public Article(String title, String articleLink, Date pubDate) {
        this.title = title;
        this.articleLink = articleLink;
        this.pubDate = pubDate;
        isFavorite = false;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        return((Article)obj).getTitle().equals(title);
    }

    @Override
    public int hashCode(){
        return articleLink.hashCode();
    }

    public  int compareTo(Object a) {

        return ((Article)a).getPubDate().compareTo(getPubDate());
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
}
