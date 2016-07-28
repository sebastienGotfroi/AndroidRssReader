package com.sebastien.testontouch.testontouch.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sebastien.testontouch.testontouch.MyAdapter;
import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.URLLoader;
import com.sebastien.testontouch.testontouch.XmlAsynchronousTask;
import com.sebastien.testontouch.testontouch.popup.PopupAdd;
import com.sebastien.testontouch.testontouch.popup.PopupDelete;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.List;

/**
 * Created by Sébastien on 09-07-16.
 */
public class MainFragment extends Fragment {

    private List<XmlAsynchronousTask> listTask = null;
    private MyAdapter adapter;
    private ProgressBar progress;
    private Context context;

    public static MainFragment create (){
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle saveInstanceState){
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.view);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MyAdapter((URLLoader)getActivity());
        rv.setAdapter(adapter);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        refreshListOfAricles();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh : progress.setVisibility(View.VISIBLE);
                refreshListOfAricles();
                break;
            case R.id.action_add : new PopupAdd(getActivity(), this).show();
                break;
            case R.id.action_delete : new PopupDelete(getActivity(), this).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();

        if(listTask != null && listTask.size()>0){
            for (XmlAsynchronousTask xmlAsynchronousTask : listTask) {
                xmlAsynchronousTask.cancel(true);
            }
        }
    }

    public void refreshListOfAricles () {

        adapter.clearList();

        AsyncTaskCompat.executeParallel(new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                return new RssServiceImpl().getAllArticlesForAllThemes(context, adapter);
            }

            @Override
            public void onPostExecute(Object result) {
                if(result != null) {
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
