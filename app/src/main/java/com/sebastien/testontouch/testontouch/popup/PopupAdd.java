package com.sebastien.testontouch.testontouch.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.adapter.SpinnerCategoryAdapter;
import com.sebastien.testontouch.testontouch.bean.Category;
import com.sebastien.testontouch.testontouch.bean.Flux;
import com.sebastien.testontouch.testontouch.fragment.ListArticleAbstractFragment;
import com.sebastien.testontouch.testontouch.fragment.MainFragment;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PopupAdd extends AlertDialog {

    private EditText editTextUrl;
    private EditText editTextAlias;
    private Spinner spinnerCategory;
    private ListArticleAbstractFragment listArticleAbstractFragment;

    public PopupAdd (Context context, ListArticleAbstractFragment listArticleAbstractFragment){
        super(context);
        this.listArticleAbstractFragment = listArticleAbstractFragment;
    }

    @Override
    public void onCreate(Bundle saveInstaceState){
        setTitle(R.string.title_addFlux);
        setContentView(R.layout.popup_add_flux);

        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);

        editTextUrl = (EditText) findViewById(R.id.editText_url);
        editTextAlias = (EditText) findViewById(R.id.editText_alias);
        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);

        List<Category> listCategory = new ArrayList<>();
        String[] listStringCategory  = spinnerCategory.getResources().getStringArray(R.array.category);

        listCategory.add(new Category(listStringCategory[0],
                ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentYellow),
                ContextCompat.getColor(spinnerCategory.getContext(), R.color.yellow)));
        listCategory.add(new Category(listStringCategory[1],
                ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentRed),
                ContextCompat.getColor(spinnerCategory.getContext(), R.color.red)));
        listCategory.add(new Category(listStringCategory[2],
                ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentGreen),
                ContextCompat.getColor(spinnerCategory.getContext(), R.color.green)));
        listCategory.add(new Category(listStringCategory[3],
                ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentPurple),
                ContextCompat.getColor(spinnerCategory.getContext(), R.color.purple)));
        listCategory.add(new Category(listStringCategory[4],
                ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentDarkBlue),
                ContextCompat.getColor(spinnerCategory.getContext(), R.color.darkBlue)));

        spinnerCategory.setAdapter(new SpinnerCategoryAdapter(listCategory, spinnerCategory.getContext()));

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flux newFLux = new Flux(editTextUrl.getText().toString(), editTextAlias.getText().toString(), new Category("Techonologie",
                        ContextCompat.getColor(spinnerCategory.getContext(),R.color.transparentYellow),
                        ContextCompat.getColor(spinnerCategory.getContext(), R.color.yellow)));
                RssServiceImpl.getRssService().addNewTheme(newFLux, getContext());

                if(listArticleAbstractFragment.getClass().equals(MainFragment.class)) {
                    ((MainFragment)listArticleAbstractFragment).refreshListOfArticles();
                }
                dismiss();
            }
        });
    }
}
