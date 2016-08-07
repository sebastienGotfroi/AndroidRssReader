package com.sebastien.testontouch.testontouch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.bean.Category;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by cecem on 06/08/2016.
 */
public class SpinnerCategoryAdapter extends ArrayAdapter {

    private List<Category> listCategory;
    private Context context;

    public SpinnerCategoryAdapter(List<Category> listCategory, Context context) {
        super(context, R.layout.spinner_category);
        this.listCategory = listCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Category getItem(int position) {
        return listCategory.get(position);
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
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        return textView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(listCategory.get(position).getStartColor());

        return textView;
    }
}
