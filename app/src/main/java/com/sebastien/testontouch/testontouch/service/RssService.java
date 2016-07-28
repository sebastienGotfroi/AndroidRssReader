package com.sebastien.testontouch.testontouch.service;

import android.content.Context;
import android.util.Pair;

import com.sebastien.testontouch.testontouch.bean.Article;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Sébastien on 03-04-16.
 */
public interface RssService {

    public Pair<String, List<Article>> getAllArticle (String flux) throws MalformedURLException, IOException, ParserConfigurationException, SAXException;
    public void addNewTheme (String url, Context context);
    public void deleteThemes ( Context context, Set<String> themesToDelete);
    public Set<String> getThemes (Context context);
}
