package com.sebastien.testontouch.testontouch;

import android.os.AsyncTask;
import android.util.Pair;

import com.sebastien.testontouch.testontouch.adapter.MyAdapter;
import com.sebastien.testontouch.testontouch.bean.Article;
import com.sebastien.testontouch.testontouch.bean.AsyncTaskResult;
import com.sebastien.testontouch.testontouch.bean.Flux;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.List;

/**
 * Created by Sébastien on 11-04-16.
 */
public class XmlAsynchronousTask extends AsyncTask<Flux, Void, AsyncTaskResult<Pair<String, List<Article>>>> {

    private MyAdapter adapter;
    private String error;


    public XmlAsynchronousTask(MyAdapter adapter){
        this.adapter = adapter;
    }
    @Override
    protected AsyncTaskResult<Pair<String, List<Article>>> doInBackground(Flux ... params) {
        try {
            return new AsyncTaskResult(RssServiceImpl.getRssService().getAllArticle((Flux) params[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AsyncTaskResult(e);
        }
    }

    protected void onPostExecute(AsyncTaskResult<Pair<String, List<Article>>> articles) {

        if(articles != null && articles.getResult() != null){
            adapter.addListArticles(articles.getResult().second);
        }
    }
}
