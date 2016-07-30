package com.sebastien.testontouch.testontouch.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sebastien.testontouch.testontouch.Constant;
import com.sebastien.testontouch.R;

/**
 * Created by Sébastien on 09-07-16.
 */
public class ArticleFragment extends Fragment {

    private Menu menu;
    private WebView webView;
    private Context context;

    public static ArticleFragment create (String link, String title) {

        Bundle bundle = new Bundle();
        bundle.putString(Constant.PARAM_LINK, link);
        bundle.putString(Constant.PARAM_TITLE, title);

        ArticleFragment articleFragment = new ArticleFragment();        
        articleFragment.setArguments(bundle);

        return articleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        setHasOptionsMenu(true);
        setRetainInstance(true);
        context = this.getActivity();
        return inflater.inflate(R.layout.article_fragment, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle bundle){

        ((Activity)context).setTitle(getArguments().getString(Constant.PARAM_TITLE));

        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        final ProgressBar progress = (ProgressBar) view.findViewById(R.id.progress_article);

        if(bundle == null) {

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String request) {
                    view.loadUrl(request);
                    return false;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    progress.setVisibility(View.GONE);
                }
            });
            webView.loadUrl(getArguments().getString(Constant.PARAM_LINK));
        }
        else {
            webView.restoreState(bundle);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.menu = menu;
        menuInflater.inflate(R.menu.menu_article, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getArguments().getString("link"));

            startActivity(Intent.createChooser(intent, getResources().getString(R.string.intent_share_title)));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
    }
}
