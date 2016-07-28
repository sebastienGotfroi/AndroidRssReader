package com.sebastien.testontouch.testontouch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sebastien.testontouch.testontouch.Constant;
import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.fragment.ArticleFragment;

/**
 * Created by Sébastien on 05-04-16.
 */
public class ArticleActivity extends AppCompatActivity {

    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String link;
        String title;

        if(getIntent().getStringExtra(Constant.PARAM_TITLE) != null) {
            title = getIntent().getStringExtra(Constant.PARAM_TITLE);
            link = getIntent().getStringExtra(Constant.PARAM_LINK);
        }
        else {
            title = getResources().getString(R.string.app_name);
            link = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        }

        setTitle(title);
        setContentView(R.layout.activity_article);

        if(savedInstanceState != null) {
            articleFragment = (ArticleFragment) getFragmentManager().getFragment(savedInstanceState, "articleFragment");
        }
        else{
            articleFragment = ArticleFragment.create(link, title);
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_article, articleFragment)
                    .commit();
        }
    }

    public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"articleFragment",articleFragment);
        super.onSaveInstanceState(outState);
    }
}
