package com.sebastien.testontouch.testontouch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.popup.PopupAdd;
import com.sebastien.testontouch.testontouch.popup.PopupDelete;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.List;

/**
 * Created by cecem on 03/08/2016.
 */
public class FavoriteFragment extends ListArticleAbstractFragment {

    public static FavoriteFragment create (){
        return new FavoriteFragment();
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        adapter.setListArticles(RssServiceImpl.getRssService().getFavorites(context));
        progress.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh : progress.setVisibility(View.VISIBLE);
                adapter.setListArticles(RssServiceImpl.getRssService().getFavorites(context));
                progress.setVisibility(View.GONE);
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
    }
}
