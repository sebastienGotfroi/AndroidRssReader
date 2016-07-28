package com.sebastien.testontouch.testontouch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sebastien.testontouch.testontouch.Constant;
import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.URLLoader;
import com.sebastien.testontouch.testontouch.fragment.ArticleFragment;
import com.sebastien.testontouch.testontouch.fragment.MainFragment;


public class MainActivity extends AppCompatActivity implements URLLoader {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainFragment = MainFragment.create();

        setTitle(getResources().getString(R.string.app_name));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, mainFragment)
                .commit();
    }

    @Override
    public void load(String title, String link) {

        if(findViewById(R.id.article) != null){
            ArticleFragment articleFragment = ArticleFragment.create(link, title);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.article, articleFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(Constant.PARAM_LINK, link);
            intent.putExtra(Constant.PARAM_TITLE, title);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0)  {
            if(getFragmentManager().getBackStackEntryCount()==1){
                setTitle(getResources().getString(R.string.app_name));
            }
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"mainFragment",mainFragment);
        super.onSaveInstanceState(outState);
    }
}
