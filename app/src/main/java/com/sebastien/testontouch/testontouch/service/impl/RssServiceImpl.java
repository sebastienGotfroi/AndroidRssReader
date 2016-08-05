package com.sebastien.testontouch.testontouch.service.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Pair;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.Constant;
import com.sebastien.testontouch.testontouch.adapter.MyAdapter;
import com.sebastien.testontouch.testontouch.XmlAsynchronousTask;
import com.sebastien.testontouch.testontouch.bean.Article;
import com.sebastien.testontouch.testontouch.bean.Flux;
import com.sebastien.testontouch.testontouch.service.RssService;
import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Sébastien on 03-04-16.
 */
public class RssServiceImpl implements RssService {

    private static RssService rssService;

    public static RssService getRssService(){
        if(rssService == null){
            rssService = new RssServiceImpl();
        }
        return rssService;
    }

    @Override
    public Pair<String, List<Article>> getAllArticle(String flux) throws MalformedURLException, IOException, ParserConfigurationException, SAXException{

        List<Article> listArticles = new ArrayList<>();

        InputStream inputStream = this.getHttpStream(flux);

        if(inputStream != null) {

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            NodeList nodeList = document.getElementsByTagName(Constant.DOM_ELEMENT_ITEM);

            String title;
            String url;
            String pubDate;

            for (int i = 0; i < nodeList.getLength(); i++) {

                Element element = (Element) nodeList.item(i);

                title = element.getElementsByTagName(Constant.DOM_ELEMENT_TITLE).item(0).getTextContent();
                url = element.getElementsByTagName(Constant.DOM_ELEMENT_URL).item(0).getTextContent();
                pubDate = element.getElementsByTagName(Constant.DOM_ELEMENT_PUBDATE).item(0).getTextContent();
                SimpleDateFormat sdt = new SimpleDateFormat(Constant.DOM_ELEMENT_PUBDATE_FORMAT, Locale.ENGLISH);
                Date date = null;

                try {
                    date = sdt.parse(pubDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                listArticles.add(new Article(title, url, date));
            }

            if (inputStream != null)
                inputStream.close();
        }

        return new Pair<String, List<Article>>(flux, listArticles);
    }

    public String getAllArticlesForAllThemes(Context context, MyAdapter adapter){

        Set<Flux> listFlux= this.getThemes(context);

        if (listFlux != null) {
            List<XmlAsynchronousTask> listTask = new ArrayList<>();

            for (Flux flux : listFlux) {
                XmlAsynchronousTask currentThread = new XmlAsynchronousTask(adapter);
                listTask.add(currentThread);
                AsyncTaskCompat.executeParallel(currentThread, flux.getUrl());
            }

            for (XmlAsynchronousTask xmlAsynchronousTask : listTask) {
                try {
                    if (xmlAsynchronousTask.get().getException() != null) {
                        return xmlAsynchronousTask.get().getException().getMessage();
                    }
                } catch (InterruptedException | ExecutionException e) {
                }
            }
            return null;
        }
        return context.getString(R.string.noFlux);
    }

    @Override
    public void addNewTheme(Flux newFlux, Context context) {

        Set<Flux> listFlux = this.getThemes(context);

        if(listFlux == null){
            listFlux = new HashSet<>();
        }

        listFlux.add(newFlux);

        this.saveThemes(context, listFlux);
    }

    @Override
    public void deleteThemes( Context context, Set<Flux> themesToDelete) {
        Set<Flux> listFlux = this.getThemes(context);

        listFlux.removeAll(themesToDelete);

        this.saveThemes(context, listFlux);
    }

    public Set<Flux> getThemes (Context context){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);
        String jsonFlux = userPreference.getString(Constant.KEY_THEME, null);

        Gson gson = new Gson();

        Flux[] tableFlux = gson.fromJson(jsonFlux, Flux[].class);

        return tableFlux == null ||tableFlux.length == 0 ? null : new HashSet<Flux>(Arrays.asList(tableFlux));
    }

    @Override
    public void addFavorite(Article newFavorite, Context context) {

        Set<Article> listFavorite = this.getFavorites(context);

        if(listFavorite == null){
            listFavorite = new HashSet<>();
        }

        listFavorite.add(newFavorite);

        this.saveFavorites(context, listFavorite);
    }

    @Override
    public void deleteFavorite( Context context, Article articleToRemove) {
        Set<Article> listFavorite = this.getFavorites(context);

        listFavorite.remove(articleToRemove);

        this.saveFavorites(context, listFavorite);
    }

    public Set<Article> getFavorites (Context context){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);
        String jsonFavorite = userPreference.getString(Constant.KEY_FAVORIS, null);

        Gson gson = new Gson();

        Article[] tableArticle = gson.fromJson(jsonFavorite, Article[].class);

        return tableArticle == null ||tableArticle.length == 0 ? null : new HashSet<Article>(Arrays.asList(tableArticle));
    }

    private void saveThemes (Context context, Set<Flux> fluxToSave){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);

        Gson gson = new Gson();
        String jsonFlux = gson.toJson(fluxToSave);
        userPreference.edit().putString(Constant.KEY_THEME, jsonFlux).commit();
    }

    private void saveFavorites (Context context, Set<Article> favoriteToSave){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);

        Gson gson = new Gson();
        String jsonFavorite = gson.toJson(favoriteToSave);
        userPreference.edit().putString(Constant.KEY_FAVORIS, jsonFavorite).commit();
    }

    private InputStream getHttpStream(String urlName) throws IOException{

            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return connection.getInputStream();
    }
}
