package com.sebastien.testontouch.testontouch.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Pair;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.Constant;
import com.sebastien.testontouch.testontouch.MyAdapter;
import com.sebastien.testontouch.testontouch.XmlAsynchronousTask;
import com.sebastien.testontouch.testontouch.bean.Article;
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

    @Override
    public void addNewTheme(String url, Context context) {

        Set<String> listTheme = this.getThemes(context);

        if(listTheme == null){
            listTheme = new HashSet<>();
        }

        listTheme.add(url);

        this.saveThemes(context, listTheme);
    }

    @Override
    public void deleteThemes( Context context, Set<String> themesToDelete) {
        Set<String> listTheme = this.getThemes(context);

        listTheme.removeAll(themesToDelete);

        this.saveThemes(context, listTheme);
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

    public Set<String> getThemes (Context context){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);
        Set<String> setTheme = userPreference.getStringSet(Constant.KEY_THEME, null);
        return setTheme == null ||setTheme.size() == 0 ? null : new HashSet<>(setTheme);
    }

    public String getAllArticlesForAllThemes(Context context, MyAdapter adapter){

        Set<String> listThemes = this.getThemes(context);

        if (listThemes != null) {
            List<XmlAsynchronousTask> listTask = new ArrayList<>();


            for (String theme : listThemes) {
                XmlAsynchronousTask currentThread = new XmlAsynchronousTask(adapter);
                listTask.add(currentThread);
                AsyncTaskCompat.executeParallel(currentThread, theme);
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

    private void saveThemes (Context context, Set<String> themesToSave){
        SharedPreferences userPreference = context.getSharedPreferences(Constant.PREFERENCE_FILE_KEY, 0);
        userPreference.edit().putStringSet(Constant.KEY_THEME, themesToSave).commit();
    }

    private InputStream getHttpStream(String urlName) throws IOException{

            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return connection.getInputStream();
    }
}
