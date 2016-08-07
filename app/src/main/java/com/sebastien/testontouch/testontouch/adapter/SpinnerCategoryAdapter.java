package com.sebastien.testontouch.testontouch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sebastien.testontouch.testontouch.bean.Category;

import java.util.List;

/**
 * Created by cecem on 06/08/2016.
 */
public class SpinnerCategoryAdapter extends BaseAdapter {

    private List<Category> listCategory;
    private Context context;

    public SpinnerCategoryAdapter(List<Category> listCategory, Context context){
        this.listCategory = listCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public String getItem(int position) {
        return listCategory.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);

        textView.setText(listCategory.get(position).getName());
        textView.setTextColor(listCategory.get(position).getStartColor());

        return textView;
    }
}
