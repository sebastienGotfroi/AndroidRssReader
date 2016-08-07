package com.sebastien.testontouch.testontouch.fragment;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.view.MenuItem;
import android.view.View;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.XmlAsynchronousTask;
import com.sebastien.testontouch.testontouch.popup.PopupAdd;
import com.sebastien.testontouch.testontouch.popup.PopupDelete;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.List;

/**
 * Created by Sébastien on 09-07-16.
 */
public class MainFragment extends ListArticleAbstractFragment {

    private List<XmlAsynchronousTask> listTask = null;

    public static MainFragment create() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        refreshListOfArticles();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_favorite:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main, FavoriteFragment.create())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_refresh:
                progress.setVisibility(View.VISIBLE);
                refreshListOfArticles();
                break;
            case R.id.action_add:
                new PopupAdd(getActivity(), this).show();
                break;
            case R.id.action_delete:
                new PopupDelete(getActivity(), this).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        if (listTask != null && listTask.size() > 0) {
            for (XmlAsynchronousTask xmlAsynchronousTask : listTask) {
                xmlAsynchronousTask.cancel(true);
            }
        }
    }

    public void refreshListOfArticles() {

        adapter.clearList();

        AsyncTaskCompat.executeParallel(new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                return RssServiceImpl.getRssService().getAllArticlesForAllThemes(context, adapter);
            }

            @Override
            public void onPostExecute(Object result) {
                if (result != null) {
                    progress.setVisibility(View.GONE);

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error !")
                            .setMessage((String) result)
                            .setCancelable(true)
                            .setNeutralButton("Ok", null)
                            .show();
                }
            }
        });
    }
}
