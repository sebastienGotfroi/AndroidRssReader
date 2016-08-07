package com.sebastien.testontouch.testontouch.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.adapter.MyAdapter;
import com.sebastien.testontouch.testontouch.URLLoader;

/**
 * Created by cecem on 03/08/2016.
 */
public abstract class ListArticleAbstractFragment extends Fragment {

    protected MyAdapter adapter;
    protected ProgressBar progress;
    protected Context context;

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
