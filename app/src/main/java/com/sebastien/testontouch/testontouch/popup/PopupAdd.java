package com.sebastien.testontouch.testontouch.popup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.bean.Flux;
import com.sebastien.testontouch.testontouch.fragment.ListArticleAbstractFragment;
import com.sebastien.testontouch.testontouch.fragment.MainFragment;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

public class PopupAdd extends AlertDialog {

    private EditText editTextUrl;
    private EditText editTextAlias;
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

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flux newFLux = new Flux(editTextUrl.getText().toString(), editTextAlias.getText().toString());
                RssServiceImpl.getRssService().addNewTheme(newFLux, getContext());

                if(listArticleAbstractFragment.getClass().equals(MainFragment.class)) {
                    ((MainFragment)listArticleAbstractFragment).refreshListOfArticles();
                }
                dismiss();
            }
        });
    }
}
